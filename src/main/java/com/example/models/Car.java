package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {

    /* It will use in production version */
    float avgSpeedWithoutKOVSH;
    String name;
    String phone;

    public Car(float avgSpeedWithKOVSH, Coord coord) {
        this.avgSpeedWithKOVSH = avgSpeedWithKOVSH;
        this.coord = coord;
    }

    private int ticks;
    private float avgSpeedWithKOVSH;
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
}
