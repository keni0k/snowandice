package com.example.order;

import com.example.cart.CartInfo;
import com.example.cart.CartLineInfo;
import com.example.product.Product;
import com.example.product.ProductServiceImpl;
import com.example.repo.CartLineInfoRepository;
import com.example.repo.OrderRepository;
import com.example.repo.ProductRepository;
import com.example.repo.UserRepository;
import com.example.user.User;
import com.example.user.UserServiceImpl;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderServiceImpl orderService;
    private ProductServiceImpl productService;
    private UserServiceImpl userService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private Utils utils;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository,
                           CartLineInfoRepository cartLineRepository) {
        this.orderService = new OrderServiceImpl(orderRepository, cartLineRepository);
        this.productService = new ProductServiceImpl(productRepository);
        userService = new UserServiceImpl(userRepository);
        utils = new Utils(userService);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cart")
    public String cart(HttpServletRequest request, ModelMap modelMap, Principal principal) {
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
                           @RequestParam("postcode") String postcode,
                           @RequestParam("ship_method") int shippingMethod) {
        User user = utils.getUser(principal);
        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.setShippingMethod(shippingMethod);
        modelMap.addAttribute("cart_info", cartInfo);
        modelMap.addAttribute("region", region);
        modelMap.addAttribute("postcode", postcode);
        modelMap.addAttribute("customer_info", new CustomerInfo());
        modelMap.addAttribute("order", new Order());
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "order/checkout";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrder(HttpServletRequest request,
                           @ModelAttribute("order") Order order,
                           @ModelAttribute("customer_info") CustomerInfo customerInfo,
                           @ModelAttribute("region") String region,
                           @ModelAttribute("postcode") String postcode,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "district", required = false) String district,
                           @ModelAttribute("street-home") String streetAndHome,
                           @ModelAttribute("city") String city,
                           ModelMap modelMap, Principal principal) {
        User user = utils.getUser(principal);
        CartInfo cartInfo = Utils.getCartInSession(request);
        if (user == null) {
            user = new User("login", password, customerInfo.getLastName(), 0,
                    customerInfo.getEmail(), customerInfo.getFirstName(), customerInfo.getPhone(),
                    city, Utils.randomToken(32));
            userService.add(user);
        }
        else customerInfo.setEmail(user.getEmail());
        order.setIdOfUser(user.getId());
        setCustomerAddress(customerInfo, region, district, city, streetAndHome, postcode);
        orderPreprocess(order, customerInfo, cartInfo.getAmountTotal(),
                        cartInfo.getShippingCost(), cartInfo.getShippingMethod());
        orderService.add(order);
        for(CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
            cartLineInfo.setOrderId(order.getId());
            orderService.addCartLine(cartLineInfo);
        }
        Utils.removeCartInSession(request);
        return "redirect:/users/account";
    }

    private void orderPreprocess(Order order, CustomerInfo customerInfo,
                                 int totalPrice, int priceOfShip, int typeOfShip){
        order.setTypeOfShip(typeOfShip);
        order.setDate(utils.getTime());
        order.setTotalPrice(totalPrice);
        order.setCustomerInfo(customerInfo);
        order.setPriceOfShip(priceOfShip);
    }

    @RequestMapping("/get_orders")
    @ResponseBody
    public String getOrders(Principal principal){
        List<Order> orders = orderService.getByIdOfUser(utils.getUser(principal).getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (Order order : orders) {
            stringBuilder.append("DATE:").append(order.getDate())
                    .append(" ID: ").append(order.getId());
        }
        return stringBuilder.toString();
    }

    @RequestMapping("/get_cartlines")
    @ResponseBody
    public String getCartLines(){
        List<CartLineInfo> cartLineInfos = orderService.getAllCartLines();
        StringBuilder stringBuilder = new StringBuilder();
        for (CartLineInfo cartLine : cartLineInfos)
            stringBuilder.append("ID: ").append(cartLine.getId())
            .append(" NAME: ").append(cartLine.getName());
        return stringBuilder.toString();
    }

    @RequestMapping("/get")
    @ResponseBody
    public String getOrder(@RequestParam("id") long id){
        Optional<Order> order = Optional.ofNullable(orderService.findById(id));
        List<CartLineInfo> cartLineInfos = orderService.getCartLinesByOrderId(id);
        return ""+cartLineInfos.size();
    }

    @RequestMapping("/remove_product_in_cart")
    public String removeProductHandler(HttpServletRequest request,
                                       @RequestParam(value = "id", required = false) Long id) {
        Product product = null;
        if (id != null) {
            product = productService.findById(id);
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
            product = productService.findById(id);
        }
        if (product != null) {

            // Cart info stored in Session.
            CartInfo cartInfo = Utils.getCartInSession(request);

            cartInfo.addProduct(product, quantity);
        }
        // Redirect to shoppingCart page.
        return "redirect:/orders/cart";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.POST)
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
                                             @RequestParam("duration") int duration) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        CartLineInfo cartLineInfo = cartInfo.findLineById(id);
        if (cartLineInfo != null) {
            cartInfo.updateProduct(id, cartLineInfo.getQuantity() + duration);
        }
        return "redirect:/orders/cart";
    }

    private void setCustomerAddress(CustomerInfo customerInfo, String region, String district,
                                    String city, String streetAndHome, String postcode) {
        if (district == null || district.equals("")) district = "";
        else district = ", " + district;
        customerInfo.setAddress(region + district + ", " + city + ", " +
                                streetAndHome + ", " + postcode);
    }
}
