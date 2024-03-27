package com.citizens.mainframe.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.citizens.mainframe.model.AccountDto;
import com.citizens.mainframe.repository.AccountRepository;

public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;
	
	public String response(String serviceName, Integer accountNumber) {
		/*call cache
		find the object corrosponding to servcie name;
		return response;*/
		return null;
	}
}
