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
import com.example.models.Image;
import com.example.repo.ImageRepository;
import com.example.utils.Consts;
import com.example.utils.UtilsForWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.example.utils.Utils.*;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

  ImageServiceImpl imageService;

  @Autowired
  public MainController(ImageRepository imageRepository){
    imageService = new ImageServiceImpl(imageRepository);
  }

  @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
  public String index(ModelMap modelMap) {
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "index";
  }

  @RequestMapping("/fix")
  public String fix(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "other/fix";
  }

  @RequestMapping("/contacts")
  public String contacts(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "other/contacts";
  }

  @RequestMapping("/ship_and_pay")
  public String ShipAndPay(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "other/ship_and_pay";
  }

  @RequestMapping("/privacy_policy")
  public String privacyPolicy(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "privacy_policy";
  }

  @RequestMapping("/exchange_and_returns")
  public String exchangeAndReturns(ModelMap modelMap){
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

        if (serverFile.isPresent() && getFileSizeMegaBytes(serverFile.get()) > 1 )
          serverFile = Optional.of(compress(serverFile.get(), getFileExtension(fileName.get()), getFileSizeMegaBytes(serverFile.get())));

        photoToken+=".jpg";
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