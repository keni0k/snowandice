package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product", schema = "public")
public class Product {

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String name;
    private int oldPrice;
    private int price;
    private int type;
    private int category;
    private int subcategory;
    private String description;
    private String dictionary;
    private String token;

    @Transient
    public ArrayList<String> pathToPhoto = new ArrayList<>();

    public Product(String name, int price, int type, int category, int subcategory,
            String description, String dictionary, String token) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.dictionary = dictionary;
        this.token = token;
    }

    ;
}
