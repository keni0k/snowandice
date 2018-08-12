package com.example.cart;

import com.example.product.Product;

public class CartLineInfo {

    private Product product;
    private int quantity;

    public CartLineInfo() {
        this.quantity = 0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productInfo) {
        this.product = productInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getName(){
        return product.getName();
    }

    public long getId(){
        return product.getId();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return this.product.getPrice() * this.quantity;
    }

}
