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

import com.example.utils.UtilsForWeb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class Main {

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

}
