package com.example.product;

import com.example.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductServiceImpl productService;

    ProductController (ProductRepository productRepository){
        this.productService = new ProductServiceImpl(productRepository);
    }

    @RequestMapping(method = RequestMethod.GET)
    String index(ModelMap modelMap){
        return "shop";
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
