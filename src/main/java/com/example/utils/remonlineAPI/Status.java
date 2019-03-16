package com.example.utils.remonlineAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    public long id;
    public String name;
    public long group;
    public String color;
}
