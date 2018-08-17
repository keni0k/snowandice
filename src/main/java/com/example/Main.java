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

package com.example;

import com.example.image.Image;
import com.example.image.ImageServiceImpl;
import com.example.repo.ImageRepository;
import com.example.utils.Consts;
import com.example.utils.UtilsForWeb;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.utils.Utils.*;

@Controller
@RequestMapping("/")
public class Main {

  ImageServiceImpl imageService;

  @Autowired
  public Main(ImageRepository imageRepository){
    imageService = new ImageServiceImpl(imageRepository);
  }

  @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
  String index(ModelMap modelMap) {
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "index";
  }

  @RequestMapping("/fix")
  String fix(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "other/fix";
  }

  @RequestMapping("/contacts")
  String contacts(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "other/contacts";
  }

  @RequestMapping("/ship_and_pay")
  String ShipAndPay(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "other/ship_and_pay";
  }

  @RequestMapping("/privacy_policy")
  String privacyPolicy(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "privacy_policy";
  }

  @RequestMapping("/exchange_and_returns")
  String exchangeAndReturns(ModelMap modelMap){
    modelMap.addAttribute("utils", new UtilsForWeb());
    return "exchange_and_returns";
  }

  private File streamToFile(String fileExtension, InputStream in) throws IOException {
    File tempFile = File.createTempFile(System.getProperty("catalina.home") + File.separator + "tmpFiles"+randomToken(10), fileExtension);
    tempFile.deleteOnExit();
    FileOutputStream out = new FileOutputStream(tempFile);
    IOUtils.copy(in, out);
    return tempFile;
  }

  @RequestMapping(value = "/upload_images", method = RequestMethod.POST)
  public @ResponseBody
  String uploadMultipleFileHandler(@RequestParam("img") MultipartFile[] files) {

    StringBuilder message = new StringBuilder();
    for (MultipartFile file : files) {
      try {
        String fileName = file.getOriginalFilename();
        String photoToken = randomToken(32);
        InputStream input = file.getInputStream();
        File serverFile = streamToFile(getFileExtension(fileName), input);

        if (getFileSizeMegaBytes(serverFile) > 1)
          serverFile = compress(serverFile, getFileExtension(fileName), getFileSizeMegaBytes(serverFile));

        photoToken+=".jpg";
        Image img = new Image();
        img.setToken(photoToken);
        putImg(serverFile.getAbsolutePath(), photoToken);
        imageService.add(img);
        message.append(Consts.URL_PATH).append(photoToken);
      } catch (Exception e) {
        message.append("<p>").append(e.getMessage()).append("</p>");
      }
    }
    return message.toString();
  }

}
