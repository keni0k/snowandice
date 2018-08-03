package com.example.user;

import com.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UsersController {

    private Utils utils;
    private UserServiceImpl userService;

//    @Autowired
//    public UsersController(UserRepository userRepository){
//        userService = new UserServiceImpl(userRepository);
//        utils = new Utils(userService);
//    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String persons(ModelMap model) {
        model.addAttribute("insertPerson", new User());
        return "registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String signIn(ModelMap modelMap, Principal principal) {
        User user = null;//utils.getUser(principal);
        modelMap.addAttribute("user", user);
        if (user == null)
            return "login";
        else
            return "index";
    }

}
