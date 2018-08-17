package com.example.image;

import com.example.utils.Consts;

import javax.persistence.*;

@Entity
@Table(name = "image", schema = "public")
public class Image {

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    public String getToken() {
        return token;
    }

    public String getData() {
        return Consts.URL_PATH+token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIdOfProduct() {
        return idOfProduct;
    }

    public void setIdOfProduct(int idOfProduct) {
        this.idOfProduct = idOfProduct;
    }

    private String token;
    private int idOfProduct;
}
