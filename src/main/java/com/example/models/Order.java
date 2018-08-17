package com.example.models;

import com.example.order.CustomerInfo;

import javax.persistence.*;

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

//    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
//    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
//    private User user;

//    @OneToMany(mappedBy="order", cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER, targetEntity = CartLineInfo.class)
//    private List<CartLineInfo> cartLineInfos;

    @Embedded
    private CustomerInfo customerInfo;
    private long idOfUser;
    private int totalPrice;
    private int typeOfShip;
    private int priceOfShip;
    private int type;
    private String date;

    public long getIdOfUser() {
        return idOfUser;
    }

    public void setIdOfUser(long idOfUser) {
        this.idOfUser = idOfUser;
    }

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

    public String getTypeToString(){
        return "Обрабатывается";
    }
}
