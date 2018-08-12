package com.example.order;

import com.example.cart.CartInfo;
import com.example.cart.CartLineInfo;
import com.example.product.Product;
import com.example.product.ProductServiceImpl;
import com.example.repo.OrderRepository;
import com.example.repo.ProductRepository;
import com.example.repo.UserRepository;
import com.example.user.User;
import com.example.user.UserServiceImpl;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderServiceImpl orderService;
    private ProductServiceImpl productService;
    private UserServiceImpl userService;
    private Utils utils;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository){
        this.orderService = new OrderServiceImpl(orderRepository);
        this.productService = new ProductServiceImpl(productRepository);
        userService = new UserServiceImpl(userRepository);
        utils = new Utils(userService);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cart")
    public String cart(HttpServletRequest request, ModelMap modelMap, Principal principal){
        User user = utils.getUser(principal);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("utils", new UtilsForWeb());
        CartInfo myCart = Utils.getCartInSession(request);
        modelMap.addAttribute("cartForm", myCart);
        return "order/cart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(HttpServletRequest request, ModelMap modelMap,
                           Principal principal,
                           @RequestParam("region") String region,
                           @RequestParam("postcode") String postcode){
        User user = utils.getUser(principal);
        CartInfo cartInfo = Utils.getCartInSession(request);
        modelMap.addAttribute("order", new Order());
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "order/checkout";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrder(@ModelAttribute("order") Order order,
                           ModelMap modelMap, Principal principal){
        User user = utils.getUser(principal);
        /*if (user==null)
            user = new User("login", );*/
        order.setIdOfUser(user.getId());
        orderService.addOrder(order);
        return "redirect:/users/account";
    }

    @RequestMapping("/remove_product_in_cart")
    public String removeProductHandler(HttpServletRequest request,
                                       @RequestParam(value = "id", required = false) Long id) {
        Product product = null;
        if (id != null) {
            product = productService.getById(id);
        }
        if (product != null) {

            // Cart Info stored in Session.
            CartInfo cartInfo = Utils.getCartInSession(request);
            cartInfo.removeProduct(product);

        }
        // Redirect to shoppingCart page.
        return "redirect:/orders/cart";
    }

    @RequestMapping(value = "/buy_product", method = RequestMethod.POST)
    public String listProductHandler(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "id", required = false) Long id,
                                     @RequestParam("quantity") int quantity) {

        Product product = null;
        if (id != null) {
            product = productService.getById(id);
        }
        if (product != null) {

            // Cart info stored in Session.
            CartInfo cartInfo = Utils.getCartInSession(request);

            cartInfo.addProduct(product, quantity);
        }
        // Redirect to shoppingCart page.
        return "redirect:/orders/cart";
    }

    @RequestMapping(value = { "/cart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, //
                                        Model model, //
                                        @ModelAttribute("cartForm") CartInfo cartForm) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);

        // Redirect to shoppingCart page.
        return "redirect:/orders/cart";
    }

    @RequestMapping(value = {"/cart_quantity"})
    public String shoppingCartUpdateQuantity(HttpServletRequest request,
                                             ModelMap model, @RequestParam("id") long id,
                                             @RequestParam("duration") int duration){

        CartInfo cartInfo = Utils.getCartInSession(request);
        CartLineInfo cartLineInfo = cartInfo.findLineById(id);
        if (cartLineInfo != null) {
            cartInfo.updateProduct(id, cartLineInfo.getQuantity()+duration);
        }
        return "redirect:/orders/cart";
    }
}
