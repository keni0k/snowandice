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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
public class ProductController {

    private Utils utils;
    private ProductServiceImpl productService;
    private UserServiceImpl userService;

    ProductController(ProductRepository productRepository, UserRepository userRepository) {
        this.productService = new ProductServiceImpl(productRepository);
        this.userService = new UserServiceImpl(userRepository);
        this.utils = new Utils(userService);
    }

    @RequestMapping(method = RequestMethod.GET)
    String singleProduct(@RequestParam(value = "id", required = false) Long id,
                         ModelMap modelMap, Principal principal) {
        User user = utils.getUser(principal);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("utils", new UtilsForWeb());
        if (id == null) return shop(modelMap, null, null);
        Product p = productService.findById(id);
        modelMap.addAttribute("product", p);
        return "product/single-product";
    }

    @RequestMapping("/list")
    public String shop(ModelMap modelMap,
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
    public String addProduct(ModelMap modelMap, Principal principal) {
        User user = utils.getUser(principal);
        if (user == null || user.getType() != Consts.USER_ADMIN)
            return "redirect:/users/login";
        modelMap.addAttribute("isEdit", false);
        modelMap.addAttribute("inputProduct", new Product());
        modelMap.addAttribute("errors", new Errors(false));
        modelMap.addAttribute("utils", new UtilsForWeb());
        modelMap.addAttribute("photos", null);
        return "admin/add_product";
    }

    @ResponseBody
//    @RequestMapping(value = "/add_all")
    public String addAllProducts(ModelMap modelMap, Principal principal) {
        User user = utils.getUser(principal);
        if (user == null || user.getType() != Consts.USER_ADMIN)
            return "redirect:/users/login";
        try {
            List<String> lines = Files.readAllLines(Paths.get("data.csv"), Charset.forName("windows-1251"));
            for (String line : lines) {
                String arr[] = line.split(";");
                boolean bol = true;
                Product product = null;
                try {
                    product = new Product(arr[3], (int)Float.parseFloat(arr[8]),
                            Consts.PRODUCT_DISABLED, -1, -1, arr[3], "", arr[0]);
                }
                catch(NumberFormatException e){
                    bol = false;
                    log.error(arr[8]);
                }
                if (bol) {
                    productService.add(product);
                    log.info("Product added");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Thanks For Posting!!!";
    }

}
