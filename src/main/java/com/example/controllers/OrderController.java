package com.example.controllers;

import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.Props;
import com.example.repo.PropsRepository;
import com.example.repo.StatusCallbackRepository;
import com.example.repo.UserRepository;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import com.example.utils.remonlineAPI.RemOrders;
import com.example.utils.remonlineAPI.RemToken;
import com.example.utils.remonlineAPI.Status;
import com.example.utils.remonlineAPI.Statuses;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final String BASE_URL = "https://api.remonline.ru/";

    private StatusCallbackRepository statusCallbackRepository;
    private Props props;
    private Utils utils;

    @Autowired
    public OrderController(UserRepository userRepository, PropsRepository propsRepository,
                           StatusCallbackRepository statusCallbackRepository) {
        this.utils = new Utils(new UserServiceImpl(userRepository));
        this.statusCallbackRepository = statusCallbackRepository;
        props = propsRepository.getPropsById(1);
    }

    private String getToken() throws IOException {
        HttpEntity post = Request.Post("https://api.remonline.ru/token/new").connectTimeout(2000).useExpectContinue()
                .bodyForm(Form.form().add("api_key", "c78e141c0c4247eab658f2a6e39ba920").build())
                .execute()
                .returnResponse().getEntity();
        Gson g = new Gson();
        RemToken token = g.fromJson(EntityUtils.toString(post), RemToken.class);
        return token.token;
    }

    private RemOrders getOrders(String phone, Integer page, Integer id, Long statusId) {
        try {
            String token = getToken();
            String url = BASE_URL + "order/?token=" + token;
            url += (phone != null) ? ("&client_phones[]=" + phone.replaceAll(" ", "")) : "";
            url += (page != null) ? ("&page=" + page) : "";
            url += (id != null) ? ("&label_id=" + id) : "";
            url += (statusId != null) ? ("&statuses[]=" + statusId) : "";
            HttpEntity get = Request.Get(url)
                    .connectTimeout(2000)
                    .socketTimeout(2000)
                    .execute()
                    .returnResponse().getEntity();

            String preproc = EntityUtils.toString(get);
            int begin = preproc.lastIndexOf("\"count\":");
            int end = preproc.indexOf(",", begin + 9);
            String count = preproc.substring(begin + 9, end);
            int lastPage = (int) Math.ceil(Integer.valueOf(count) / 50.0);
            if (page == null && lastPage != 1) {
                get = Request.Get(url + "&page=" + lastPage)
                        .connectTimeout(2000)
                        .socketTimeout(2000)
                        .execute()
                        .returnResponse().getEntity();
            }

            log.info("Orders count: " + count + ", token: " + token);

            Gson g = new Gson();
            return g.fromJson(EntityUtils.toString(get), RemOrders.class);
        } catch (JsonSyntaxException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Status> getStatuses() {
        try {
            String token = getToken();
            String url = BASE_URL + "statuses/?token=" + token;
            HttpEntity get = Request.Get(url)
                    .connectTimeout(2000)
                    .socketTimeout(2000)
                    .execute()
                    .returnResponse().getEntity();
            return new Gson().fromJson(EntityUtils.toString(get), Statuses.class).data;
        } catch (JsonSyntaxException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/status_json")
    @ResponseBody
    public String customStatus(@RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "status", required = false) Long statusId) {
        RemOrders remOrders = getOrders(phone, page, id, statusId);
        if (remOrders == null) return "NULL";
        Gson g = new Gson();
        return g.toJson(remOrders);
    }

    private String digits(String phone) {
        StringBuilder solve = new StringBuilder();
        for (int i = 0; i < phone.length(); i++) {
            if (Character.isDigit(phone.charAt(i))) {
                solve.append(phone.charAt(i));
            }
        }
        return solve.toString();
    }

    private boolean isZeros(String phone) {
        int zeros = 0;
        for (int i = 0; i < phone.length(); i++) {
            if (phone.charAt(i) == '0')
                zeros++;
        }
        return zeros == phone.length();
    }

    private RemOrders getOrders(String phone, Integer page,
                                Integer id, Long statusId, boolean isAdmin) {
        RemOrders remOrders = null;
        if (phone != null)
            phone = digits(phone);
        if ((phone != null && phone.length() >= props.getCountDigitsForWidget() && !isZeros(phone)) || isAdmin) {
            if (!isAdmin) {
                page = null;
                id = null;
            }
            remOrders = getOrders(phone, page, id, statusId);
            if (remOrders != null && remOrders.data != null) {
                try {
                    remOrders.data.sort((o1, o2) -> Long.compare(o2.created_at, o1.created_at));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return remOrders;
    }

    @RequestMapping(value = "/widget", method = RequestMethod.GET)
    public String widgetStatus(ModelMap modelMap,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "status", required = false) Long statusId,
                               Principal principal) {
        boolean isAdmin = utils.isAdmin(principal);
        RemOrders remOrders = getOrders(phone, page, id, statusId, isAdmin);
        modelMap.addAttribute("orders", remOrders);
        modelMap.addAttribute("phone", phone != null ? phone : "");
        modelMap.addAttribute("statuses", isAdmin ? getStatuses() : null);
        if (remOrders != null && remOrders.data != null && remOrders.data.size() > 0 && isAdmin && statusId != null)
            modelMap.addAttribute("status", remOrders.data.get(0).status);
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "order/widget";
    }

    @RequestMapping(value = "/widget_html", method = RequestMethod.POST)
    public String widgetToModal(ModelMap modelMap,
                                @RequestParam(value = "phone", required = false) String phone,
                                @RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "id", required = false) Integer id,
                                @RequestParam(value = "status", required = false) Long statusId,
                                Principal principal){
        boolean isAdmin = utils.isAdmin(principal);
        RemOrders remOrders = getOrders(phone, page, id, statusId, isAdmin);
        modelMap.addAttribute("orders", remOrders);
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "fragments/results_widget";
    }

}
