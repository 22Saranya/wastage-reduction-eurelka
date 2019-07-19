package com.stackroute.registrationserver.service;

import com.stackroute.registrationserver.domain.RestaurantProfile;
import com.stackroute.registrationserver.domain.Restaurants;

import java.util.List;

public interface RestaurantService {

    RestaurantProfile saveRestaurant(Restaurants restaurant) throws Exception;
    List<RestaurantProfile> displayRestaurants() throws Exception;
    RestaurantProfile updateRestaurant(Restaurants restaurants)throws Exception;
    RestaurantProfile displayRestaurantByUsername(String username) throws  Exception;
}