package com.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

import javax.persistence.*;

@Entity
@Table(appliesTo = "coord")
@NoArgsConstructor
@Data
public class Coord {
    @Column
    private double lat;
    @Column
    private double lng;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id")
    Segment segment;

    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public static double dist(Coord c1, Coord c2) {
        return Math.pow(c1.lat - c2.lat, 2) + Math.pow(c1.lng - c2.lng, 2);
    }
}
