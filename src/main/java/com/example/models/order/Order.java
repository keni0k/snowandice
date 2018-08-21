package com.example.models.order;

import com.example.cart.CartLineInfo;
import com.example.models.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order", schema = "public")
public class Order {

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "id_of_user", nullable = false)
    private User user;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, targetEntity = CartLineInfo.class)
    private List<CartLineInfo> cartLineInfos;

    @Embedded
    private CustomerInfo customerInfo;
    private int totalPrice;
    private int typeOfShip;
    private int priceOfShip;
    private int type;
    private String date;


    public int getTypeOfShip() {
        return typeOfShip;
    }

    public void setTypeOfShip(int typeOfShip) {
        this.typeOfShip = typeOfShip;
    }

    public int getPriceOfShip() {
        return priceOfShip;
    }

    public void setPriceOfShip(int priceOfShip) {
        this.priceOfShip = priceOfShip;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTypeOfShipToString(){
        switch (typeOfShip){
            case 1: return "Самовывоз";
            case 2: return "Курьером";
        }
        return "Обрабатывается";
    }

    public String getDateByRUFormat(){
        return date.substring(8,9)+date.substring(5,6)+date.substring(2,3);
    }

    public String getTypeToString(){
        return "Обрабатывается";
    }

    public List<CartLineInfo> getCartLineInfos() {
        return cartLineInfos;
    }

    public void setCartLineInfos(List<CartLineInfo> cartLineInfos) {
        this.cartLineInfos = cartLineInfos;
    }
}
