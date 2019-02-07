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
    private int price;
    private int type;
    private int category;
    private int subcategory;
    private String description;
    private String dictionary;

    @Transient
    public ArrayList<String> pathToPhoto = new ArrayList<>();

}
