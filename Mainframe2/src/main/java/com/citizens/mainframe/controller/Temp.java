package com.citizens.mainframe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citizens.mainframe.model.Accounts;
import com.citizens.mainframe.repository.AccountRepository;

@RestController
@RequestMapping("/acc")
public class Temp {

	@Autowired
	private AccountRepository accountRepo;
	
	@PostMapping("/")
	public ResponseEntity<?> addAccount(@RequestBody Accounts acc){
		Accounts save = this.accountRepo.save(acc);
		System.out.println("account saved " + save);
		return ResponseEntity.ok(save);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Accounts>> getAllAccounts()
	{
		List<Accounts> findAll = this.accountRepo.findAll();
		return new ResponseEntity<List<Accounts>>(findAll,HttpStatus.OK);
	}
	
	
}
