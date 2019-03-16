package com.example.controllers;

import com.example.jpa_services_impl.LogServiceImpl;
import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.Log;
import com.example.models.User;
import com.example.models.callbacks.Callback;
import com.example.models.callbacks.Status;
import com.example.repo.CallbackRepository;
import com.example.repo.LogRepository;
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
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/admin")
class AdminController {

    private UserService userService;
    private LogServiceImpl logService; //TODO: LogService without impl
    private Utils utils;
    private CallbackRepository callbackRepository;
    private StatusCallbackRepository statusCallbackRepository;

    @Autowired
    public AdminController(UserRepository userRepository, CallbackRepository callbackRepository,
                           StatusCallbackRepository statusCallbackRepository, LogRepository logRepository) {
        this.callbackRepository = callbackRepository;
        this.statusCallbackRepository = statusCallbackRepository;
        this.userService = new UserServiceImpl(userRepository);
        this.logService = new LogServiceImpl(logRepository);
        this.utils = new Utils(userService);
    }

    private boolean isAdmin(Principal principal) {
        User nowUser = utils.getUser(principal);
        return nowUser != null && nowUser.getType() == Consts.USER_ADMIN;
    }

    @RequestMapping(value = "/callbacks", method = RequestMethod.GET)
    public String widgetCallback(ModelMap modelMap,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam(value = "id", required = false) Integer id,
                                 @RequestParam(value = "status_id", required = false) Long status,
                                 Principal principal) {
        if (isAdmin(principal)) {
            modelMap.addAttribute("statuses", statusCallbackRepository.findAll());
            if (phone != null) {
                modelMap.addAttribute("callbacks", callbackRepository.getCallbacksByPhone(phone));
            } else if (id != null) {
                modelMap.addAttribute("callbacks", new ArrayList<Callback>().add(callbackRepository.getCallbackById(id)));
            } else if (status != null) {
                modelMap.addAttribute("callbacks", callbackRepository.getCallbacksByStatus(statusCallbackRepository.getOne(status)));
                modelMap.addAttribute("statusId", status);
            } else {
                modelMap.addAttribute("callbacks", callbackRepository.findAll());
            }
        }
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "admin/callbacks";
    }

    @RequestMapping(value = "/statuses", method = RequestMethod.GET)
    public String widgetStatus(ModelMap modelMap, Principal principal) {
        if (isAdmin(principal)) {
            modelMap.addAttribute("statuses", statusCallbackRepository.findAll());
        }
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "admin/statuses";
    }
//TODO: log
    @RequestMapping(value = "/add_status", method = RequestMethod.POST)
    public String addStatus(Principal principal, @Valid Status status) {
        if (isAdmin(principal)) {
            statusCallbackRepository.save(status);
        }
        return "redirect:/admin/statuses";
    }

    @RequestMapping(value = "/edit_status", method = RequestMethod.GET)
    public String editStatus(Principal principal, ModelMap modelMap,
                             @RequestParam("id") long id) {
        if (isAdmin(principal)) {
            modelMap.addAttribute("statuses", statusCallbackRepository.findAll());
            modelMap.addAttribute("status", statusCallbackRepository.getOne(id));
        }
        return "admin/statuses";
    }

    @RequestMapping(value = "/edit_callback", method = RequestMethod.GET)
    public String editCallback(Principal principal, ModelMap modelMap,
                               @RequestParam("id") long id) {
        if (isAdmin(principal)) {
            modelMap.addAttribute("statuses", statusCallbackRepository.findAll());
            modelMap.addAttribute("callback", callbackRepository.getOne(id));
        }
        return "admin/callbacks";
    }
    //TODO: log
    @RequestMapping(value = "/edit_callback", method = RequestMethod.POST)
    public String editCallbackPost(Principal principal,
                                   @RequestParam("id") long id,
                                   @RequestParam("status") long statusId,
                                   @RequestParam("phone") String phone) {
        if (isAdmin(principal)) {
            Callback callback = callbackRepository.getOne(id);
            callback.setPhone(phone);
            callback.setStatus(statusCallbackRepository.getOne(statusId));
            callbackRepository.save(callback);
        }
        return "redirect:/admin/callbacks";
    }

    //TODO: log
    @RequestMapping(value = "/del_callback", method = RequestMethod.GET)
    public String delCallback(Principal principal, @RequestParam("id") long id) {
        if (isAdmin(principal)) {
            callbackRepository.deleteById(id);
        }
        return "redirect:/admin/callbacks";
    }

    @RequestMapping(value = "/del_status", method = RequestMethod.GET)
    public String delStatus(Principal principal, @RequestParam("id") long id) {
        if (isAdmin(principal) && id != 3) {
            Status status = statusCallbackRepository.getOne(id);
            String logDescription = "Коллбэк статус \"" + status.getName() + "\" (id = " + status.getId() + ") цвета " + status.getColor() + " был удален.";
            Log log = Log.builder()
                    .date(new Date())
                    .level(Log.DELETE)
                    .user(utils.getUser(principal))
                    .description(logDescription)
                    .build();
            statusCallbackRepository.delete(status);
            logService.add(log);
        }
        return "redirect:/admin/statuses";
    }
}
