package com.example.user;

import com.example.utils.Consts;
import com.example.utils.validation.LoginConstraint;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class User implements UserDetails{

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @LoginConstraint
    private String login = "";
    //    @PasswordConstraint
    private String pass = "";
    private String lastName = "";
    private int type = 0;
    private String email = "";
    private String firstName = "";
    private String phoneNumber = "";
    private String city = "";
    private String token = "";
    private String role = "";
    private String time = "";
    private String vk = "";
    private String address = "";
    private String birthday = "";
    private boolean subscription = false;
    private int reviewsCount = 0;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getType() {
        return type;
    }

    public String getStringType(int language){
        String[] typesRu = {"Не активирован", "Заблокирован", "Активирован", "Администратор"};
        if (language==0) {
            switch (type) {
                case Consts.USER_DISABLED: return typesRu[0];
                case Consts.USER_BLOCKED: return typesRu[1];
                case Consts.USER_ENABLED:return typesRu[2];
                case Consts.USER_ADMIN: return typesRu[3];
                default: return "TYPE NULL";
            }
        } else
            return "LANGUAGE NULL";
    }

    public void setType(int type) {
        this.type = type;
    }

    public User() {
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public User(String login, String pass, String lastName, int type, String email, String firstName, String phoneNumber, String city, String token) {
        this.login = login;
        this.pass = pass;
        this.lastName = lastName;
        this.type = type;
        this.email = email;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.token = token;
    }

    @Override
    public String toString() {

        return "{\n" +

                "\t\"id\":\"" + id + "\",\n" +
                "\t\"login\":\"" + login + "\",\n" +
                "\t\"pass\":\"" + pass + "\",\n" +
                "\t\"lastName\":\"" + lastName + "\",\n" +
                "\t\"firstName\":\"" + firstName + "\",\n" +
                "\t\"type\":\"" + type + "\",\n" +
                "\t\"email\":\"" + email + "\",\n" +
                "\t\"phoneNumber\":\"" + phoneNumber + "\",\n" +
                "\t\"city\":\"" + city + "\",\n" +
                "\t\"token\":\"" + token + "\",\n" +
                "}";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority(getRole()));
        return grantedAuths;
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return type!=-3;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return type>0;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }
}
