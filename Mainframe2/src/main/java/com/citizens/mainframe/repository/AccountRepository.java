package com.citizens.mainframe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.citizens.mainframe.model.Accounts;

public interface AccountRepository extends MongoRepository<Accounts,Integer>{

	
	
}
