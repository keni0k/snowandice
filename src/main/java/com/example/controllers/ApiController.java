package com.example.controllers;

import com.example.utils.UtilsForWeb;
import groovy.util.logging.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j
@Controller
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/routes")
    @ResponseBody
    public String routes(@RequestParam(value = "machinesCount") Integer machineCount) {
        UtilsForWeb utilsForWeb = new UtilsForWeb();
        return utilsForWeb.algorithmMorning(machineCount);
    }

}
