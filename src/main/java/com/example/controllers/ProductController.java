package com.example.controllers;

import com.example.jpa_services_impl.ProductServiceImpl;
import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.Product;
import com.example.models.User;
import com.example.repo.ProductRepository;
import com.example.repo.UserRepository;
import com.example.utils.Consts;
import com.example.utils.Errors;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private Utils utils;
    private ProductServiceImpl productService;
    private UserServiceImpl userService;

    ProductController (ProductRepository productRepository, UserRepository userRepository) {
        this.productService = new ProductServiceImpl(productRepository);
        this.userService = new UserServiceImpl(userRepository);
        this.utils = new Utils(userService);
    }

    @RequestMapping(method = RequestMethod.GET)
    String singleProduct(@RequestParam(value = "id", required = false) Long id,
                         ModelMap modelMap, Principal principal){
        User user = utils.getUser(principal);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("utils", new UtilsForWeb());
        if (id == null) return "product/shop";
        Product p = productService.findById(id);
        modelMap.addAttribute("product", p);
        return "product/single-product";
    }

    @RequestMapping("/list")
    String shop(ModelMap modelMap,
                @RequestParam(value = "category", required = false) Integer category,
                @RequestParam(value = "subcategory", required = false) Integer subcategory) {
        List<Product> products;
        if (category == null) products = productService.findAll();
        else if (subcategory == null) products = productService.getByCategory(category);
        else products = productService.getByCategoryAndSubcategory(category, subcategory);
        modelMap.addAttribute("products", products);
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "product/shop";
    }

    @RequestMapping(value = "/add_product")
    public String addProduct(ModelMap modelMap, Principal principal){
        User user = utils.getUser(principal);
        if (user==null || user.getType()!= Consts.USER_ADMIN)
            return "redirect:/users/login";
        modelMap.addAttribute("isEdit", false);
        modelMap.addAttribute("inputProduct", new Product());
        modelMap.addAttribute("errors", new Errors(false));
        modelMap.addAttribute("utils", new UtilsForWeb());
        modelMap.addAttribute("photos", null);
        return "admin/add_product";
    }

}
