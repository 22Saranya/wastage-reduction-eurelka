package com.stackroute.service;

import com.stackroute.domain.Charity;
import com.stackroute.domain.CharityLiveStatus;
import com.stackroute.domain.Logs;
import com.stackroute.rabbitmq.model.CharityStatus;
import com.stackroute.rabbitmq.model.CharityStatusList;
import com.stackroute.repository.CharityRepository;
import com.stackroute.repository.CharityLiveStatusRepository;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RabbitListener(queues = "${charityLogs.rabbitmq.queue}")
public class RabbitService {

    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private CharityLiveStatusRepository charityLiveStatusRepository;

    @RabbitHandler
    public void receivedmsg(CharityStatusList charityStatusListWrapper) {
        List<CharityStatus> charityStatusList = charityStatusListWrapper.getCharityStatusList();
        System.out.println("Recieved List :" + charityStatusList);
//        List<Charity> charityList = charityRepository.findAll();
        for (int i = 0; i < charityStatusList.size(); i++) {
            CharityStatus charityStatus = charityStatusList.get(i);
            System.out.println("Recieved Message For Charity : " + charityStatus.getUsername() + " => " + charityStatus);
//            System.out.println(charityRepository.findById(charityStatus.getUsername()).get());
            Charity charity = new Charity();
            System.out.println(charity);
            try{
                charity = charityRepository.findById(charityStatus.getUsername()).get();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(charity.getUsername()!=null){
                List<Logs> logsList = charity.getLogs();
                Logs logs = new Logs();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                charity.setUsername(charityStatus.getUsername());
                logs.setId("1");
                logs.setDate(dtf.format(now));
                logs.setCharityStatus(charityStatus);
                logsList.add(logs);
                charity.setLogs(logsList);
                charityRepository.save(charity);
                CharityLiveStatus charityLiveStatus = new CharityLiveStatus(charityStatus.getUsername(),logs);
                charityLiveStatusRepository.save(charityLiveStatus);
            }
            else {
                charity = new Charity();
                List<Logs> logsList = new ArrayList<>();
                Logs logs = new Logs();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                charity.setUsername(charityStatus.getUsername());
                logs.setId("1");
                logs.setDate(dtf.format(now));
                logs.setCharityStatus(charityStatus);
                logsList.add(logs);
                charity.setLogs(logsList);
                charityRepository.save(charity);
                CharityLiveStatus charityLiveStatus = new CharityLiveStatus(charityStatus.getUsername(),logs);
                charityLiveStatusRepository.save(charityLiveStatus);
            }
        }
    }
}

