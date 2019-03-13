/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.controllers;

import com.example.jpa_services_impl.ImageServiceImpl;
import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.Image;
import com.example.models.User;
import com.example.repo.ImageRepository;
import com.example.repo.UserRepository;
import com.example.utils.Consts;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import com.example.utils.remonlineAPI.RemOrders;
import com.example.utils.remonlineAPI.RemToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
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
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

import static com.example.utils.Utils.*;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {
    private Utils utils;
    private ImageServiceImpl imageService;

    @Autowired
    public MainController(ImageRepository imageRepository, UserRepository userRepository) {
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        imageService = new ImageServiceImpl(imageRepository);
        utils = new Utils(userService);
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "index";
    }

    RemOrders getOrders(String phone, Integer page, Integer id) {
        try {
            HttpEntity post = Request.Post("https://api.remonline.ru/token/new").connectTimeout(2000).useExpectContinue()
                    .bodyForm(Form.form().add("api_key", "c78e141c0c4247eab658f2a6e39ba920").build())
                    .execute()
                    .returnResponse().getEntity();
            Gson g = new Gson();
            RemToken token = g.fromJson(EntityUtils.toString(post), RemToken.class);
            String url = "https://api.remonline.ru/order/?token=" + token.token;
            url += (phone != null) ? ("&client_phones[]=" + phone.replaceAll(" ", "")) : "";
            url += (page != null) ? ("&page=" + page) : "";
            url += (id != null) ? ("&label_id=" + id) : "";
            HttpEntity get = Request.Get(url)
                    .connectTimeout(2000)
                    .socketTimeout(2000)
                    .execute()
                    .returnResponse().getEntity();
            g = new Gson();
            return g.fromJson(EntityUtils.toString(get), RemOrders.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/status_custom")
    public @ResponseBody
    String customStatus(@RequestParam(value = "phone", required = false) String phone,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "id", required = false) Integer id) {
        RemOrders remOrders = getOrders(phone, page, id);
        if (remOrders == null) return "NULL";
        Gson g = new Gson();
        return g.toJson(remOrders);
    }

    private String digits(String phone){
        StringBuilder solve = new StringBuilder("");
        for (int i = 0; i<phone.length(); i++){
            if (Character.isDigit(phone.charAt(i)))
                solve.append(phone.charAt(i));
        }
        return solve.toString();
    }

    @RequestMapping(value = "/order_widget", method = RequestMethod.GET)
    public String widgetStatus(ModelMap modelMap,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "id", required = false) Integer id,
                               Principal principal) {
        User nowUser = utils.getUser(principal);
        boolean isAdmin = false;
        if (nowUser != null && nowUser.getType() == Consts.USER_ADMIN)
            isAdmin = true;
        if (phone!=null)
            phone = digits(phone);
        if ((phone != null && phone.length()>4) || isAdmin) {
            if (!isAdmin){
                page = null;
                id = null;
            }
            RemOrders remOrders = getOrders(phone, page, id);
            modelMap.addAttribute("orders", remOrders);
        } else {
            modelMap.addAttribute("orders", null);
        }
        modelMap.addAttribute("phone", phone!=null?phone:"");
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "order/widget";
    }

    @RequestMapping("/status")
    public String status(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "other/status";
    }

    @RequestMapping("/fix")
    public String fix(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "other/fix";
    }

    @RequestMapping("/contacts")
    public String contacts(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "other/contacts";
    }

    @RequestMapping("/ship_and_pay")
    public String ShipAndPay(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "other/ship_and_pay";
    }

    @RequestMapping("/privacy_policy")
    public String privacyPolicy(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "privacy_policy";
    }

    @RequestMapping("/exchange_and_returns")
    public String exchangeAndReturns(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "exchange_and_returns";
    }

    @RequestMapping(value = "/upload_images", method = RequestMethod.POST)
    public @ResponseBody
    String uploadMultipleFileHandler(@RequestParam("img") MultipartFile[] files) {

        var message = new StringBuilder();
        for (MultipartFile file : files) {
            try {
                var fileName = Optional.ofNullable(file.getOriginalFilename());
                var photoToken = randomToken(32);
                var input = file.getInputStream();
                var serverFile = Optional.ofNullable(streamToFile(getFileExtension(fileName.orElseThrow(Exception::new)), input));

                if (serverFile.isPresent() && getFileSizeMegaBytes(serverFile.get()) > 1)
                    serverFile = Optional.of(compress(serverFile.get(), getFileExtension(fileName.get()), getFileSizeMegaBytes(serverFile.get())));

                photoToken += ".jpg";
                Image img = new Image();
                img.setToken(photoToken);
                serverFile.orElseThrow(Exception::new);
                putImg(serverFile.get().getAbsolutePath(), photoToken);
                imageService.add(img);
                message.append(Consts.URL_PATH).append(photoToken);
            } catch (Exception e) {
                message.append("<p>").append(e.getMessage()).append("</p>");
            }
        }
        return message.toString();
    }

}