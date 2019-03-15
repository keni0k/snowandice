package com.example.controllers;

import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.User;
import com.example.models.callbacks.Callback;
import com.example.models.callbacks.Status;
import com.example.repo.CallbackRepository;
import com.example.repo.StatusCallbackRepository;
import com.example.repo.UserRepository;
import com.example.services.UserService;
import com.example.utils.Consts;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/admin")
class AdminController {

    private UserService userService;
    private Utils utils;
    private CallbackRepository callbackRepository;
    private StatusCallbackRepository statusCallbackRepository;

    @Autowired
    public AdminController(UserRepository userRepository, CallbackRepository callbackRepository, StatusCallbackRepository statusCallbackRepository){
        this.callbackRepository = callbackRepository;
        this.statusCallbackRepository = statusCallbackRepository;
        this.userService = new UserServiceImpl(userRepository);
        this.utils = new Utils(userService);
    }

    @RequestMapping(value = "/callbacks", method = RequestMethod.GET)
    public String widgetCallback(ModelMap modelMap,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "status", required = false) Integer status,
                               Principal principal) {
        User nowUser = utils.getUser(principal);
        boolean isAdmin = false;
        if (nowUser != null && nowUser.getType() == Consts.USER_ADMIN)
            isAdmin = true;
        if (isAdmin){
            if (phone!=null){
                modelMap.addAttribute("callbacks", callbackRepository.getCallbacksByPhone(phone));
            } else if (id!=null) {
                modelMap.addAttribute("callbacks", new ArrayList<Callback>().add(callbackRepository.getCallbackById(id)));
            } else if (status!=null) {
                modelMap.addAttribute("callbacks", callbackRepository.getCallbacksByStatus(status));
            }
        }
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "admin/callbacks";
    }

    @RequestMapping(value = "/statuses", method = RequestMethod.GET)
    public String widgetStatus(ModelMap modelMap, Principal principal) {
        User nowUser = utils.getUser(principal);
        boolean isAdmin = false;
        if (nowUser != null && nowUser.getType() == Consts.USER_ADMIN)
            isAdmin = true;
        if (isAdmin){
            modelMap.addAttribute("statuses", statusCallbackRepository.findAll());
        }
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "admin/statuses";
    }

    @RequestMapping(value = "/add_status", method = RequestMethod.POST)
    public String addStatus(ModelMap modelMap, Principal principal, @Valid Status status) {
        User nowUser = utils.getUser(principal);
        boolean isAdmin = false;
        if (nowUser != null && nowUser.getType() == Consts.USER_ADMIN)
            isAdmin = true;
        if (isAdmin){
            statusCallbackRepository.save(status);
        }
        return widgetStatus(modelMap, principal);
    }
}
