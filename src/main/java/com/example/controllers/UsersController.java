package com.example.controllers;

import com.example.jpa_services_impl.OrderServiceImpl;
import com.example.jpa_services_impl.UserServiceImpl;
import com.example.models.User;
import com.example.repo.CartLineInfoRepository;
import com.example.repo.OrderRepository;
import com.example.repo.UserRepository;
import com.example.utils.Consts;
import com.example.utils.MessageUtil;
import com.example.utils.Utils;
import com.example.utils.UtilsForWeb;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Contact;
import com.mailjet.client.resource.Email;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;

import static com.example.utils.Utils.randomToken;

@Slf4j
@Controller
@RequestMapping("/users")
public class UsersController {

    private Utils utils;
    private UserServiceImpl userService;
    private OrderServiceImpl orderService;
    private final MessageSource messageSource;

    @Autowired
    public UsersController(UserRepository userRepository, MessageSource messageSource,
                           OrderRepository orderRepository, CartLineInfoRepository cartLineInfoRepository){
        userService = new UserServiceImpl(userRepository);
        orderService = new OrderServiceImpl(orderRepository, cartLineInfoRepository);
        this.messageSource = messageSource;
        utils = new Utils(userService);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String persons(ModelMap model) {
        model.addAttribute("insertUser", new User());
        model.addAttribute("utils", new UtilsForWeb());
        return "user/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String signUp(@ModelAttribute("insertUser") @Valid User person,
                         BindingResult result,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @ModelAttribute("pass2") String pass2,
                         ModelMap model, Locale locale) {
        person.setToken(randomToken(32));
        model.addAttribute("utils", new UtilsForWeb());
        if (!userService.throwsErrors(person, pass2) || result.hasErrors()) {
            model.addAttribute("error_login", !userService.isLoginFree(person.getLogin()));
            if (!person.getPhoneNumber().equals(""))
                model.addAttribute("error_phone", !userService.isPhoneFree(person.getPhoneNumber()));
            model.addAttribute("error_pass", !person.getPass().equals(pass2));
            model.addAttribute("error_email_free", !userService.isEmailFree(person.getEmail()));
            model.addAttribute("error_email_valid", !userService.isEmailCorrect(person.getEmail()));
            model.addAttribute("insertUser", person);
            model.addAttribute("utils", new UtilsForWeb());
            model.addAttribute("message", new MessageUtil("danger", messageSource.getMessage("error.user.add", null, locale)));
            return "user/registration";
        }
        person.setEmail(person.getEmail().toLowerCase());
        person.setLogin(person.getLogin().toLowerCase());

        person.setRole("ROLE_USER");
        person.setType(Consts.USER_DISABLED);
        person.setTime(utils.getTime());
        try {
            sendMail(person.getToken(), person.getEmail());
            model.addAttribute("message", new MessageUtil("success", messageSource.getMessage("success.user.registration", null, locale)));
        } catch (MailjetSocketTimeoutException | MailjetException e) {
            model.addAttribute("message", new MessageUtil("danger", messageSource.getMessage("danger.user.registration.mail", null, locale)));
        }
        userService.add(person);
        return persons(model);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String signIn(ModelMap modelMap, Principal principal) {
        User user = utils.getUser(principal);
        modelMap.addAttribute("utils", new UtilsForWeb());
        modelMap.addAttribute("user", user);
        if (user == null)
            return "user/login";
        else
            return account(modelMap, principal);
    }

    @RequestMapping(value = "/edit_data", method = RequestMethod.POST)
    public String editData(ModelMap modelMap, Principal principal,
                           @RequestParam("first_name") String firstName,
                           @RequestParam("last_name") String lastName,
                           @RequestParam("phone_number") String phoneNumber,
                           @RequestParam(value = "birthday", required = false)
                                       String birthday,
                           @RequestParam(value = "subscription", required = false) Boolean subscription){
        User user = utils.getUser(principal);
        modelMap.addAttribute("utils", new UtilsForWeb());
        if (user==null) return "user/login";
        if (subscription==null) subscription = false;
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setBirthday(birthday);
        user.setSubscription(subscription);
        userService.update(user);
        return account(modelMap, principal);
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(ModelMap model, Principal principal) {

        User user = utils.getUser(principal);
        if (user == null) return signIn(model, principal);

        model.addAttribute("user", user);
        model.addAttribute("consts", new Consts());
        model.addAttribute("utils", new UtilsForWeb());
        model.addAttribute("orders", orderService.getByUser(user));
        model.addAttribute("answersCount", 1);
        model.addAttribute("ordersWaitCount", 2);
        model.addAttribute("ordersCompliteCount", 3);
        return "user/account";
    }

    private String sendMail(String token, String emailStr) throws MailjetSocketTimeoutException, MailjetException {
        MailjetRequest email;
        JSONArray recipients;
        MailjetResponse response;
        MailjetClient client = new MailjetClient("489ff3e95ebe1a6a3303dbd79ec3777f", "0be4f9f8ede6f035f85fd4393875f32d");

        recipients = new JSONArray()
                .put(new JSONObject().put(Contact.EMAIL, emailStr));

        email = new MailjetRequest(Email.resource)
                .property(Email.FROMNAME, "Цифровой центр")
                .property(Email.FROMEMAIL, "elishanto@gmail.com")
                .property(Email.SUBJECT, "Подтвердите свой e-mail")
                .property(Email.TEXTPART, "Вы зарегистрировались на сайте cifracentr.ru и для завершения регистрации должны нажать на ссылку: http://cifracentr.ru/users/confirm?token=" + token)
                .property(Email.RECIPIENTS, recipients)
                .property(Email.MJCUSTOMID, "JAVA-Email");

        response = client.post(email);
        return response.getData() + " " + response.getStatus();
    }

    @RequestMapping(value = "/add_admin", method = RequestMethod.GET)
    public String addAdmin(@RequestParam("email") String email, Principal principal, ModelMap modelMap){
        User nowUser = utils.getUser(principal);
        if (nowUser == null || nowUser.getType() != Consts.USER_ADMIN) return signIn(modelMap, principal);

        User u = userService.getByLoginOrEmail(email);
        u.setRole("ROLE_ADMIN");
        u.setType(Consts.USER_ADMIN);
        modelMap.addAttribute("message", new MessageUtil("success", u.getFullName()+" получил роль ADMIN"));
        return account(modelMap, principal);
    }

    @RequestMapping(value = "/edit_address", method = RequestMethod.POST)
    public String editAddress(ModelMap modelMap, Principal principal,
                              @RequestParam("region") String region,
                              @RequestParam(value = "district", required = false) String district,
                              @RequestParam("city") String city,
                              @RequestParam("address") String address,
                              @RequestParam("postcode") String postcode){
        User user = utils.getUser(principal);
        if (district == null) district = "";
        user.setAddress(region, district, city, address, postcode);
        userService.update(user);
        return account(modelMap, principal);
    }
}
