package com.example.controllers;

import com.example.utils.UtilsForWeb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping(method = RequestMethod.GET)
    public String error(ModelMap model){
        model.addAttribute("utils", new UtilsForWeb());
        return "error";
    }
}
