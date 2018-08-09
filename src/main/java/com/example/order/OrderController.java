package com.example.order;

import com.example.repo.OrderRepository;
import com.example.repo.UserRepository;
import com.example.user.User;
import com.example.user.UserServiceImpl;
import com.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderServiceImpl orderService;
    private UserServiceImpl userService;
    private Utils utils;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository){
        this.orderService = new OrderServiceImpl(orderRepository);
        userService = new UserServiceImpl(userRepository);
        utils = new Utils(userService);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cart")
    public String cart(ModelMap modelMap, Principal principal){
        User user = utils.getUser(principal);
        modelMap.addAttribute("user", user);
        return "cart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(ModelMap modelMap, Principal principal,
                           @RequestParam("region") String region,
                           @RequestParam("postcode") String postcode){
        User user = utils.getUser(principal);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("isHaveACoupon", false);
        return "checkout";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrder(ModelMap modelMap, Principal principal){
        return "redirect:/users/account";
    }

}
