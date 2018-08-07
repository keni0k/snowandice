package com.example.order;

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

    private long idOfUser;
    private long idOfProduct;
    private int priceOfProduct;
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

    public long getIdOfProduct() {
        return idOfProduct;
    }

    public void setIdOfProduct(long idOfProduct) {
        this.idOfProduct = idOfProduct;
    }

    public int getPriceOfProduct() {
        return priceOfProduct;
    }

    public void setPriceOfProduct(int priceOfProduct) {
        this.priceOfProduct = priceOfProduct;
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
}
