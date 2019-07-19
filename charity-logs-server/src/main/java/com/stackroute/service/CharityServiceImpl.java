package com.stackroute.service;

import com.stackroute.domain.Charity;
import com.stackroute.domain.CharityLiveStatus;
import com.stackroute.rabbitmq.model.CharityStatus;
import com.stackroute.repository.CharityRepository;
import com.stackroute.repository.CharityLiveStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CharityServiceImpl implements CharityService {

    @Autowired
    CharityRepository charityRepository;

    @Autowired
    CharityLiveStatusRepository charityLiveStatusRepository;

    public CharityServiceImpl(){}

    public CharityServiceImpl(CharityRepository charityRepository){this.charityRepository = charityRepository;}

    @Override
    public Charity saveCharityLogs(Charity charity) throws Exception{

        if(charityRepository.existsById(charity.getUsername())) {
            throw new Exception("Charity already exists");
        }
        else
            return charityRepository.save(charity);
    }

    @Override
    public Charity fetchCharity(String username) {
        return charityRepository.findById(username).get();
    }

    @Override
    public List<Charity> displayCharityLogs() {
        return charityRepository.findAll();
    }

    @Override
    public CharityLiveStatus fetchCharityStatus(String username) {
        return charityLiveStatusRepository.findById(username).get();
    }
}
