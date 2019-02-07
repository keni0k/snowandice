package com.example.models;

import com.example.models.order.Order;
import com.example.utils.Consts;
import com.example.utils.validation.LoginConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", schema = "public")
public class User implements UserDetails {

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, targetEntity = Order.class)
    private List<Order> orders;


    public String getStringType(int language) {
        String[] typesRu = {"Не активирован", "Заблокирован", "Активирован", "Администратор"};
        if (language == 0) {
            switch (type) {
                case Consts.USER_DISABLED:
                    return typesRu[0];
                case Consts.USER_BLOCKED:
                    return typesRu[1];
                case Consts.USER_ENABLED:
                    return typesRu[2];
                case Consts.USER_ADMIN:
                    return typesRu[3];
                default:
                    return "TYPE NULL";
            }
        } else
            return "LANGUAGE NULL";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority(role));
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

    public User(String login, String pass, String lastName, String email, String firstName, String phoneNumber, String city, String token, String time, String address, boolean subscription) {
        this.login = login;
        this.pass = pass;
        this.lastName = lastName;
        this.email = email;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.token = token;
        this.time = time;
        this.address = address;
        this.subscription = subscription;
    }

    @Override
    public boolean isAccountNonLocked() {
        return type != -3;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return type > 0;
    }

    public String getFullName() {
        return lastName + ' ' + firstName;
    }

    public void setAddress(String region, String district, String city, String address, String postcode) {
        this.address = "РФ" + "$" + region + "$" + district + "$" + city + "$" + address + "$" + postcode;
    }

    public String[] getListOfAddress() {
        if (address != null && !address.equals(""))
            return address.split("\\$");
        else
            return new String[]{"", "", "", "", "", ""};
    }
}
