package com.example.utils;

import com.example.models.Car;
import com.example.models.Coord;
import com.example.models.Segment;
import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URLParsingSettings;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


public class Utils {


    private static void clear(ArrayList<Car> cars, ArrayList<Segment> segments) {
        for (Car car : cars) {
            car.setFree();
        }
        for (Segment segment : segments) {
            segment.setClean(false);
            segment.setCountOfCars(0);
        }
    }

    private static Segment getMinDistFreeSegment(ArrayList<Segment> segments, Car car) {
        if (car == null || segments == null || segments.size() == 0)
            return null;
        Segment minDistFreeSegment = segments.get(0);
        double minDist = Coord.dist(segments.get(0).getStart(), car.getCoord());

        for (Segment segment : segments) {
            if (segment.getCountOfNeedCars() - segment.getCountOfCars() > 0 && !segment.isClean()) {
                if (Coord.dist(segment.getStart(), car.getCoord()) < minDist) {
                    minDistFreeSegment = segment;
                    minDist = Coord.dist(segment.getStart(), car.getCoord());
                }
            }
        }
        return minDistFreeSegment;
    }

    private static void carsTicksDec(ArrayList<Car> cars) {
        for (Car car : cars) {
            car.decTicks();
            if (car.isFree())
                car.getSegments()
                        .get(car.getSegments().size() - 1).setClean(true);
        }
    }

    public static int algorithm(int tick, ArrayList<Car> cars, ArrayList<Segment> segments) {
        if (tick == 0)
            clear(cars, segments);
        carsTicksDec(cars);
        boolean isFinish = true;
        for (Segment segment : segments) {
            if (!segment.isClean()) {
                isFinish = false;
                break;
            }
        }
        segments.sort((segment, t1) -> Integer.compare(t1.getCountOfNeedCars(), segment.getCountOfNeedCars()));
        if (isFinish) return tick;
        tick++;
        for (Car car : cars) {
            if (car.isFree()) {
                Segment segment = getMinDistFreeSegment(segments, car);
                car.setTicks((int) (segment.getLength() / car.getAvgSpeedWithKOVSH() / 60));
                car.getSegments().add(segment);
                segment.getCars().add(car);
            }
        }
        for (Segment segment : segments) {
            if (segment.getCountOfCars() > 0)
                while (segment.getCountOfNeedCars() - segment.getCountOfCars() < segment.getMinDistAloneCars(cars).size() &&
                        segment.getMinDistAloneCar(cars) != null) {
                    Car car = segment.getMinDistAloneCar(cars);
                    segment.getCars().add(car);
                    car.getSegments().remove(car.getSegments().size() - 1);
                    car.getSegments().add(segment);
                }
        }
        return algorithm(tick, cars, segments);
    }

    public static URI getUrl(String url) throws GalimatiasParseException, URISyntaxException {
        URLParsingSettings settings = URLParsingSettings.create();
        io.mola.galimatias.URL myUrl = io.mola.galimatias.URL.parse(settings, url);
        return myUrl.toJavaURI();
    }

}
