package com.example.controllers;

import com.example.cart.CartInfo;
import com.example.cart.CartLineInfo;
import com.example.jpa_services_impl.OrderServiceImpl;
import com.example.jpa_services_impl.ProductServiceImpl;
import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.Product;
import com.example.models.User;
import com.example.models.order.CustomerInfo;
import com.example.models.order.Order;
import com.example.repo.CartLineInfoRepository;
import com.example.repo.OrderRepository;
import com.example.repo.ProductRepository;
import com.example.repo.UserRepository;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderServiceImpl orderService;
    private ProductServiceImpl productService;
    private UserServiceImpl userService;
    private Utils utils;

    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository,
                           CartLineInfoRepository cartLineRepository) {
        this.orderService = new OrderServiceImpl(orderRepository, cartLineRepository);
        this.productService = new ProductServiceImpl(productRepository);
        this.userService = new UserServiceImpl(userRepository);
        this.utils = new Utils(userService);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cart")
    public String cart(HttpServletRequest request, ModelMap modelMap, Principal principal) {
        var user = utils.getUser(principal);
        var cartInfo = Utils.getCartInSession(request);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("utils", new UtilsForWeb());
        modelMap.addAttribute("cartForm", cartInfo);
        return "order/cart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(HttpServletRequest request, ModelMap modelMap,
                           Principal principal,
                           @RequestParam("region") String region,
                           @RequestParam("postcode") String postcode,
                           @RequestParam("ship_method") int shippingMethod) {
        var user = utils.getUser(principal);
        var cartInfo = Utils.getCartInSession(request);
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
        var user = Optional.ofNullable(utils.getUser(principal));
        var cartInfo = Utils.getCartInSession(request);
        setCustomerAddress(customerInfo, region, district, city, streetAndHome, postcode);
        if (!user.isPresent()) {
            user = Optional.of(new User("withoutLogin", password, customerInfo.getLastName(),
                    customerInfo.getEmail(), customerInfo.getFirstName(), customerInfo.getPhone(), city,
                    Utils.randomToken(32), "TIME", customerInfo.getAddress(), false));
            userService.add(user.get());
        } else customerInfo.setEmail(user.get().getEmail());
        order.setUser(user.get());
        orderPreprocess(order, customerInfo, cartInfo.getAmountTotal(),
                cartInfo.getShippingCost(), cartInfo.getShippingMethod());
        orderService.add(order);
        for (CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
            cartLineInfo.setOrder(order);
            orderService.addCartLine(cartLineInfo);
        }
        Utils.removeCartInSession(request);
        return "redirect:/users/account";
    }

    private void orderPreprocess(Order order, CustomerInfo customerInfo,
                                 int totalPrice, int priceOfShip, int typeOfShip) {
        order.setTypeOfShip(typeOfShip);
        order.setDate(utils.getTime());
        order.setTotalPrice(totalPrice);
        order.setCustomerInfo(customerInfo);
        order.setPriceOfShip(priceOfShip);
    }

    @RequestMapping("/get_orders")
    @ResponseBody
    public String getOrders(Principal principal) {
        var orders = orderService.getByUser(utils.getUser(principal));
        var stringBuilder = new StringBuilder();
        for (Order order : orders) {
            stringBuilder.append("DATE:").append(order.getDate())
                    .append(" ID: ").append(order.getId());
        }
        return stringBuilder.toString();
    }

    @RequestMapping("/get_cartlines")
    @ResponseBody
    public String getCartLines() {
        var cartLineInfos = orderService.getAllCartLines();
        var stringBuilder = new StringBuilder();
        for (CartLineInfo cartLine : cartLineInfos)
            stringBuilder.append("ID: ").append(cartLine.getId())
                    .append(" NAME: ").append(cartLine.getName());
        return stringBuilder.toString();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getOrder(@RequestParam("id") long id, ModelMap modelMap, Principal principal) {
        var order = orderService.findById(id);
        var user = utils.getUser(principal);

        if (user == null)
            return "redirect:/users/login";

        if (order != null && order.getUser().getId() == user.getId()) {
            modelMap.addAttribute("order", order);
        } else {
            modelMap.addAttribute("order", null);
        }
        modelMap.addAttribute("utils", new UtilsForWeb());
        return "order/order_info";
    }

    @RequestMapping("/remove_product_in_cart")
    public String removeProductHandler(HttpServletRequest request,
                                       @RequestParam(value = "id", required = false) Long id) {
        var cartInfo = Utils.getCartInSession(request);
        Optional<Product> product = Optional.empty();
        if (id != null) {
            product = Optional.ofNullable(productService.findById(id));
        }
        product.ifPresent(cartInfo::removeProduct);
        return "redirect:/orders/cart";
    }

    @RequestMapping(value = "/buy_product", method = RequestMethod.POST)
    public String listProductHandler(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "id", required = false) Long id,
                                     @RequestParam("quantity") int quantity) {
        var cartInfo = Utils.getCartInSession(request);
        Optional<Product> product = Optional.empty();
        if (id != null) {
            product = Optional.ofNullable(productService.findById(id));
        }
        product.ifPresent(product1 -> cartInfo.addProduct(product1, quantity));
        // Redirect to shoppingCart page.
        return "redirect:/orders/cart";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, Model model,
                                        @ModelAttribute("cartForm") CartInfo cartForm) {

        var cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);

        // Redirect to shoppingCart page.
        return "redirect:/orders/cart";
    }

    @RequestMapping(value = {"/cart_quantity"})
    public String shoppingCartUpdateQuantity(HttpServletRequest request,
                                             ModelMap model, @RequestParam("id") long id,
                                             @RequestParam("duration") int duration) {

        var cartInfo = Utils.getCartInSession(request);
        var cartLineInfo = cartInfo.findLineById(id);
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
