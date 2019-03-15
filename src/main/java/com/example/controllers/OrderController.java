package com.example.controllers;

import com.example.jpa_services_impl.OrderServiceImpl;
import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.Props;
import com.example.models.User;
import com.example.repo.CartLineInfoRepository;
import com.example.repo.OrderRepository;
import com.example.repo.PropsRepository;
import com.example.repo.UserRepository;
import com.example.services.UserService;
import com.example.utils.Consts;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import com.example.utils.remonlineAPI.RemOrders;
import com.example.utils.remonlineAPI.RemToken;
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

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final String BASE_URL = "https://api.remonline.ru/order/?token=";

    private OrderServiceImpl orderService;
    private UserService userService;
    private Props props;
    private Utils utils;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, CartLineInfoRepository cartLineInfoRepository,
                           PropsRepository propsRepository) {
        this.orderService = new OrderServiceImpl(orderRepository, cartLineInfoRepository);
        this.userService = new UserServiceImpl(userRepository);
        this.utils = new Utils(userService);
        props = propsRepository.getPropsById(1);
    }

    private RemOrders getOrders(String phone, Integer page, Integer id) {
        try {
            HttpEntity post = Request.Post("https://api.remonline.ru/token/new").connectTimeout(2000).useExpectContinue()
                    .bodyForm(Form.form().add("api_key", "c78e141c0c4247eab658f2a6e39ba920").build())
                    .execute()
                    .returnResponse().getEntity();
            Gson g = new Gson();
            RemToken token = g.fromJson(EntityUtils.toString(post), RemToken.class);
            String url = BASE_URL + token.token;
            url += (phone != null) ? ("&client_phones[]=" + phone.replaceAll(" ", "")) : "";
            url += (page != null) ? ("&page=" + page) : "";
            url += (id != null) ? ("&label_id=" + id) : "";
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

            log.info("Pages count: " + count + ", token: " + token.token);

            g = new Gson();
            return g.fromJson(EntityUtils.toString(get), RemOrders.class);
        } catch (JsonSyntaxException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/status_json")
    @ResponseBody
    public String customStatus(@RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "id", required = false) Integer id) {
        RemOrders remOrders = getOrders(phone, page, id);
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

    @RequestMapping(value = "/widget", method = RequestMethod.GET)
    public String widgetStatus(ModelMap modelMap,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "id", required = false) Integer id,
                               Principal principal) {
        User nowUser = utils.getUser(principal);
        boolean isAdmin = false;
        if (nowUser != null && nowUser.getType() == Consts.USER_ADMIN)
            isAdmin = true;
        if (phone != null)
            phone = digits(phone);
        if ((phone != null && phone.length() >= props.getCountDigitsForWidget() && !isZeros(phone)) || isAdmin) {
            if (!isAdmin) {
                page = null;
                id = null;
            }
            RemOrders remOrders = getOrders(phone, page, id);
            if (remOrders != null && remOrders.data != null) {
                try {
                    remOrders.data.sort((o1, o2) -> Long.compare(o2.created_at, o1.created_at));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            modelMap.addAttribute("orders", remOrders);
        } else {
            modelMap.addAttribute("orders", null);
        }
        modelMap.addAttribute("phone", phone != null ? phone : "");
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "order/widget";
    }

}
