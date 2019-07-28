package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(appliesTo = "car")
public class Car {

    /* It will use in production version */
    /*float avgSpeedWithoutKOVSH;
    String name;
    String phone;*/

    public Car(float avgSpeedWithKOVSH, Coord coord) {
        this.avgSpeedWithKOVSH = avgSpeedWithKOVSH;
        this.coord = coord;
    }
    @Column(name = "car_id")
    @Id
    private long id;
    private int ticks;

    @Column(name = "avg_speed")
    private float avgSpeedWithKOVSH;
    @Column(name = "coord")
    private Coord coord;
    private ArrayList<Segment> segments;

    public boolean isFree() {
        return ticks == 0;
    }

    public void setFree() {
        ticks = 0;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void decTicks() {
        if (ticks > 0)
            ticks--;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    public float getAvgSpeedWithKOVSH() {
        return avgSpeedWithKOVSH;
    }

    public void setAvgSpeedWithKOVSH(float avgSpeedWithKOVSH) {
        this.avgSpeedWithKOVSH = avgSpeedWithKOVSH;
    }

    public long getId() {
        return id;
    }
}
