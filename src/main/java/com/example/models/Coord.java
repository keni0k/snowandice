package com.example.models;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//@Entity
//@Table(appliesTo = "coord")
public class Coord {
    @Column
    private double lat;
    @Column
    private double lng;
    @Id
    @Column(name = "id")
    private long id;

    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public static double dist(Coord c1, Coord c2) {
        return Math.pow(c1.lat - c2.lat, 2) + Math.pow(c1.lng - c2.lng, 2);
    }
}
