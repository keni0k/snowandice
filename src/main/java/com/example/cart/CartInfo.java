package com.example.cart;

import com.example.models.Product;
import com.example.models.order.CustomerInfo;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {

    private int shippingMethod;
    private int shippingCost;

    private CustomerInfo customerInfo;

    private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

    public CartInfo() {

    }

    public int getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(int shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<CartLineInfo> getCartLines() {
        return this.cartLines;
    }

    public CartLineInfo findLineById(long id) {
        for (CartLineInfo line : this.cartLines) {
            if (line.getProductId()==id) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(Product product, int quantity) {
        CartLineInfo line = this.findLineById(product.getId());

        if (line == null) {
            line = new CartLineInfo();
            line.setQuantity(0);
            line.setProductId(product.getId());
            line.setName(product.getName());
            line.setPrice(product.getPrice());
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }

    public void validate() {

    }

    public void updateProduct(long id, int quantity) {
        CartLineInfo line = this.findLineById(id);

        if (line != null) {
            if (quantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }

    public void removeProduct(Product product) {
        CartLineInfo line = this.findLineById(product.getId());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }

    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

    public boolean isValidCustomer() {
        return this.customerInfo != null;
    }

    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLineInfo line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    public int getAmountTotal() {
        int total = 0;
        for (CartLineInfo line : this.cartLines) {
            total += line.getAmount();
        }
        return total;
    }

    public void updateQuantity(CartInfo cartForm) {
        if (cartForm != null) {
            List<CartLineInfo> lines = cartForm.getCartLines();
            for (CartLineInfo line : lines) {
                this.updateProduct(line.getProductId(), line.getQuantity());
            }
        }

    }

    public int getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(int shippingCost) {
        this.shippingCost = shippingCost;
    }
}
