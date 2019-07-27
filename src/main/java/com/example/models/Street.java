package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Time;
import java.util.ArrayList;

@Data
public class Street {
    private ArrayList<Segment> segments;
    private String name;

    Street(ArrayList<Segment> segments, String name) {
        this.segments = segments;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Street{" +
                "name =" + name +
                '}';
    }
}