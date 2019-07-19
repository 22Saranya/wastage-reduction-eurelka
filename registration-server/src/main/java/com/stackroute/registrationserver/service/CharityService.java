package com.stackroute.registrationserver.service;

import com.stackroute.registrationserver.domain.Charities;
import com.stackroute.registrationserver.domain.CharityProfile;

import java.util.List;

public interface CharityService {

    CharityProfile saveCharity(Charities charity) throws Exception;
    List<CharityProfile> displayCharity();
    CharityProfile updateCharity(Charities charity)throws Exception;
    CharityProfile displayCharityByUsername(String username) throws  Exception;
}