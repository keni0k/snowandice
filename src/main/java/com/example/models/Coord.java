package com.example.models;

public class Coord {
    private double lat;
    private double lng;

    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public static double dist(Coord c1, Coord c2) {
        return Math.pow(c1.lat - c2.lat, 2) + Math.pow(c1.lng - c2.lng, 2);
    }
}
