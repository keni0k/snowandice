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

import com.example.models.Car;
import com.example.models.Coord;
import com.example.models.Segment;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import io.mola.galimatias.GalimatiasParseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import static com.example.utils.Consts.URL_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {
    private Utils utils;

    @Autowired
    public MainController() {

    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
//        List goods = goodsRepository.findAll();
//        goods.sort(Comparator.comparingDouble(Good::getPriority));
        modelMap.addAttribute("utils", new UtilsForWeb());
//        modelMap.addAttribute("goods", goods);
        return "index";
    }

    @RequestMapping(value = "/do_class", method = RequestMethod.GET)
    public String algorithm(ModelMap modelMap,
                            @RequestParam(value = "cars") Integer cars) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        modelMap.addAttribute("result", algorithmMorning(cars));
        return "index";
    }

    private int algorithmMorning(Integer countOfCars) {
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < countOfCars; i++)
            cars.add(new Car(30, new Coord(52.2797298616311, 104.34527349498241)));
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("Lenina", new Coord(52.28576568355886, 104.28068161010744),
                new Coord(52.28209020513799, 104.28089618682863), 10, 10.5, 3));
        return Utils.algorithm(0, cars, segments);
    }

    @RequestMapping("/status")
    public String status(ModelMap modelMap) {
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "other/status";
    }


    private String redirectWithMsg(HttpServletRequest request, String msg, String msgType) {
        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        String redirect = request.getHeader("referer") != null ? request.getHeader("referer") : "";
        if (!redirect.startsWith("http")) {
            redirect = URL_PATH + "index";
        }
        redirect += (redirect.contains("?") ? "&" : "?");
        redirect += "msg=" + msg + "&msg_type=" + msgType;
        return "redirect:" + redirect;
    }

    @RequestMapping(method = POST, value = "/get_phone")
    public String getPhone(HttpServletRequest request,
                           @RequestParam("phone") String phone) {
        String uri = "https://sms.ru/sms/send?api_id=142B1FE9-02B5-9595-74C6-AD05A4F07EA2&to=79039193100&msg=Новый коллбэк, телефон: " +
                phone.replace("+7", "8") + "&json=1";

        //TODO: need ajax
        if (phone.length() == 17) {
            HttpEntity get;
            boolean isStatus = false;
            boolean isStatusSMS = false;
            try {
                get = Request.Get(Utils.getUrl(uri))//smotret
                        .connectTimeout(2000)
                        .socketTimeout(2000)
                        .execute()
                        .returnResponse().getEntity();
                String response = EntityUtils.toString(get);
                int start = response.indexOf("\"status\": \"");
                int startTwo = response.lastIndexOf("\"status\": \"");
                if (start > 0)
                    isStatus = response.substring(start + 11, start + 13).equals("OK");
                if (startTwo > 0)
                    isStatusSMS = response.substring(startTwo + 11, startTwo + 13).equals("OK");
            } catch (IOException ignored) {
            } catch (GalimatiasParseException | URISyntaxException e) {
                e.printStackTrace();
            }
//            callbackRepository.save(new Callback(phone, statusCallbackRepository.getOne(3L)));
            if (!isStatus || !isStatusSMS) {
                return redirectWithMsg(request, "Не удалось отправить запрос водителю. Пожалуйста, позвоните ему контактному телефону", "danger");
            }
        }
        return redirectWithMsg(request, "Запрос передан водителю!", "success");
    }

}