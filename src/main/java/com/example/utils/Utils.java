package com.example.utils;

import com.example.models.Car;
import com.example.models.Coord;
import com.example.models.Segment;
import groovy.util.logging.Log4j;
import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URLParsingSettings;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

@Log4j
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
        Segment minDistFreeSegment = null;
        double minDist = 0;
        for (Segment segment : segments)
            if (!segment.isClean() && segment.getCountOfNeedCars() - segment.getCountOfCars() > 0) {
                minDistFreeSegment = segment;
                minDist = Coord.dist(segment.getStart(), car.getCoord());
                break;
            }
        if (minDistFreeSegment == null)
            return null;

        for (Segment segment : segments) {
            if (!segment.isClean() && segment.getCountOfNeedCars() - segment.getCountOfCars() > 0) {
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
            if (car.isFree() && car.getSegments() != null && car.getSegments().size() > 0) {
                car.getSegments()
                        .get(car.getSegments().size() - 1).setClean(true);
            }
        }
    }

    static String algorithm(ArrayList<Car> cars, ArrayList<Segment> segments) {
        clear(cars, segments);
        while (true) {
            Logger logger = Logger.getLogger("SnowANdIce");
            carsTicksDec(cars);
            boolean isFinish = true;
            for (Segment segment : segments) {
                if (!segment.isClean()) {
                    isFinish = false;
                    break;
                }
            }
            if (isFinish) {
                StringBuilder sb = new StringBuilder("[[");
                for (Car car : cars) {
                    sb.append("[");
                    sb.append(car.getCoord().getLat())
                            .append(",").append(car.getCoord().getLng()).append("]");
                    for (Segment segment : car.getSegments()) {
                        sb.append(",[").append(segment.getStart().getLat()).append(",")
                                .append(segment.getStart().getLng()).append("],[")
                                .append(segment.getEnd().getLat()).append(",")
                                .append(segment.getEnd().getLng()).append("]");
                    }
                    sb.append("]");
                }
                return sb.append("]").toString();
            }
            for (Car car : cars) {
                if (car.isFree()) {
                    Segment segment = getMinDistFreeSegment(segments, car);
                    if (segment != null) {
                        car.setTicks((int) (segment.getLength() / car.getAvgSpeedWithKOVSH() / 60));
                        car.getSegments().add(segment);
                        segment.getCars().add(car);
                        segment.setCountOfCars(segment.getCountOfCars() + 1);
                    }
                }
            }
            /*for (Segment segment : segments) {
                if (segment.getCountOfCars() > 0)
                    while (segment.getCountOfNeedCars() - segment.getCountOfCars() < segment.getMinDistAloneCars(cars).size() &&
                            segment.getMinDistAloneCar(cars) != null) {
                        Car car = segment.getMinDistAloneCar(cars);
                        segment.getCars().add(car);
                        car.getSegments().remove(car.getSegments().size() - 1);
                        car.getSegments().add(segment);
                    }
            }*/
        }
    }

    public static URI getUrl(String url) throws GalimatiasParseException, URISyntaxException {
        URLParsingSettings settings = URLParsingSettings.create();
        io.mola.galimatias.URL myUrl = io.mola.galimatias.URL.parse(settings, url);
        return myUrl.toJavaURI();
    }


}
