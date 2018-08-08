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

    ProductController (ProductRepository productRepository) {
        this.productService = new ProductServiceImpl(productRepository);
    }

    @RequestMapping("/cart")
    String cart() {
        return "cart";
    }

    @RequestMapping("/checkout")
    String checkout(){ return "checkout"; }

    @RequestMapping(method = RequestMethod.GET)
    String singleProduct(ModelMap modelMap, @RequestParam(value = "id", required = false) Long id){
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
