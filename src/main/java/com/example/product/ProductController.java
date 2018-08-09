package com.example.product;

import com.example.repo.ProductRepository;
import com.example.repo.UserRepository;
import com.example.user.User;
import com.example.user.UserServiceImpl;
import com.example.utils.Utils;
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
        if (id == null) return "shop";
        Product p = productService.getById(id);
        modelMap.addAttribute("product", p);
        return "single-product";
    }

    @RequestMapping("/list")
    String shop(ModelMap modelMap,
                @RequestParam(value = "category", required = false) Integer category,
                @RequestParam(value = "subcategory", required = false) Integer subcategory) {
        List<Product> products;
        if (category == null) products = productService.getAll();
        else if (subcategory == null) products = productService.getByCategory(category);
        else products = productService.getByCategoryAndSubcategory(category, subcategory);
        modelMap.addAttribute("products", products);
        return "shop";
    }


}
