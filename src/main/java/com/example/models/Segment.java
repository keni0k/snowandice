package com.example.models;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;

@Entity
@Table(appliesTo = "segment")
public class Segment {
    private String nameOfStreet;
    @Column
    @Id
    private int id;
    @Column
    private Coord start;
    @Column
    private Coord end;
    @Column
    private int priority;
    @Column
    private double length;
    @Column
    private int countOfNeedCars;
    private int countOfCars = 0;
    @Column
    private boolean isClean = false;
    @Column
    private ArrayList<Car> cars;

    /* It will use in production version */
    private Time lastCleaning;
    private Time nextClean;
    private String nameOfStart;
    private String nameOfEnd;


    public Segment(String nameOfStreet, Coord start, Coord end, int priority,
                   double length, int countOfNeedCars) {
        this.nameOfStreet = nameOfStreet;
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.length = length;
        this.countOfNeedCars = countOfNeedCars;
    }

    public Segment(Coord start, Coord end) {
        this.start = start;
        this.end = end;
    }

    public ArrayList<Car> getMinDistAloneCars(ArrayList<Car> cars) {
        if (cars == null || cars.size() == 0)
            return null;
        ArrayList<Car> clearCars = (ArrayList<Car>) cars.clone();
        clearCars.removeIf(
                car -> car.getSegments().get(car.getSegments().size() - 1)
                        .getCountOfNeedCars() != 1
        );
        return clearCars;
    }

    public Car getMinDistAloneCar(ArrayList<Car> cars) {
        ArrayList<Car> clearCars = getMinDistAloneCars(cars);
        clearCars.sort(Comparator.comparingDouble
                (car -> Coord.dist(car.getCoord(), start))
        );
        return clearCars.size() > 0 ? clearCars.get(0) : null;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public int getCountOfNeedCars() {

        return countOfNeedCars;
    }

    public void setCountOfNeedCars(int countOfNeedCars) {
        this.countOfNeedCars = countOfNeedCars;
    }

    public int getCountOfCars() {
        return countOfCars;
    }

    public void setCountOfCars(int countOfCars) {
        this.countOfCars = countOfCars;
    }

    public Coord getStart() {
        return start;
    }

    public void setStart(Coord start) {
        this.start = start;
    }

    public Coord getEnd() {
        return end;
    }

    public void setEnd(Coord end) {
        this.end = end;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }
}