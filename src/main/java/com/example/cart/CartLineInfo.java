package com.example.cart;

import com.example.models.order.Order;

import javax.persistence.*;

@Entity
@Table(name="cart_line_info", schema = "public")
public class CartLineInfo {

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Order.class)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private long productId;
    private String name;
    private int quantity;
    private int price;

    public CartLineInfo() {
        this.quantity = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName(){
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return this.price * this.quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

}