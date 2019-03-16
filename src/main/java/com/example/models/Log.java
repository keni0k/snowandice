package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "log", schema = "public")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    // 0 - INFO, 1 - EDIT, 2 - DELETE, 3 - WARN, 4 - ERROR
    private int level;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "id_of_user", nullable = false)
    private User user;
    private Date date;

    public static final int INFO = 0;
    public static final int EDIT = 1;
    public static final int WARN = 2;
    public static final int DELETE = 3;
    public static final int ERROR = 4;
}