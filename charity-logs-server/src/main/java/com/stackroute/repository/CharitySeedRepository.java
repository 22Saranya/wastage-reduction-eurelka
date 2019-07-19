package com.stackroute.repository;

import com.stackroute.domain.CharitySeed;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharitySeedRepository extends MongoRepository<CharitySeed, String> {

}
