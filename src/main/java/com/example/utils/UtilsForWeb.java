package com.example.utils;

import com.example.models.Car;
import com.example.models.Coord;
import com.example.models.Segment;

import java.util.ArrayList;

public class UtilsForWeb {

    public String algorithmMorning(Integer countOfCars) {
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < countOfCars; i++) {
            cars.add(new Car(30, new Coord(52.2797298616311, 104.34527349498241), i));
        }
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("Lenina", new Coord(52.28576568355886, 104.28068161010744),
                new Coord(52.28209020513799, 104.28089618682863), 10, 10.5, 3, 0));
        segments.add(new Segment("Lenina", new Coord(52.28209020513799, 104.28089618682863),
                new Coord(52.27599874106406, 104.28626060485841), 10, 10.5, 2, 1));
        segments.add(new Segment("Sovetskya", new Coord(52.27634797179878, 104.30354261385219),
                new Coord(52.27991890346087, 104.32371282564418), 10, 10.5, 1, 2));
        segments.add(new Segment("Sovetskya", new Coord(52.27991890346087, 104.32371282564418),
                new Coord(52.2797298616311, 104.34527349498241), 10, 10.5, 1, 3));
        segments.add(new Segment("Sovetskya", new Coord(52.2797298616311, 104.34527349498241),
                new Coord(52.2806094257078, 104.34774112727611), 10, 10.5, 1, 4));
        return Utils.algorithm(0, cars, segments);
    }

}
