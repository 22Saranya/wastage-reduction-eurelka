package com.stackroute.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Logs {

    @Id
    private String id;

    private String date;

    private List<RestaurantSave> restaurants;

    private List<CharitySave> charities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RestaurantSave> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantSave> restaurants) {
        this.restaurants = restaurants;
    }

    public List<CharitySave> getCharities() {
        return charities;
    }

    public void setCharities(List<CharitySave> charities) {
        this.charities = charities;
    }
}
