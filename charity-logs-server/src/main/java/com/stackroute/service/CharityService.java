package com.stackroute.service;

import com.stackroute.domain.Charity;
import com.stackroute.domain.CharityLiveStatus;
import com.stackroute.rabbitmq.model.CharityStatus;

import java.util.List;

public interface CharityService {
    public Charity saveCharityLogs(Charity charity) throws Exception;

    public List<Charity> displayCharityLogs();

    public Charity fetchCharity(String username);

    public CharityLiveStatus fetchCharityStatus(String username);


}
