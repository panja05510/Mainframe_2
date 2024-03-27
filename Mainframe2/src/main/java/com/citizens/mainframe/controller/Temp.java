package com.citizens.mainframe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citizens.mainframe.model.Accounts;
import com.citizens.mainframe.repository.AccountRepository;

import java.util.List;

@RestController
public class Temp {

    @Autowired
    private AccountRepository accountRepo;

    @GetMapping("/acc")
    @Cacheable("accountsCache") // Define cache name
    public ResponseEntity<List<Accounts>> getAllAccounts() {
        List<Accounts> findAll = this.accountRepo.findAll();
        String message = "*****************************************Data from database**************************";
        if (!findAll.isEmpty()) {
            message = "*********************************************Data from cache****************************************";
        }
        System.out.println(message); // Print the message
        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }
    
    @PostMapping("/acc")
    public ResponseEntity<?> addAccount(@RequestBody Accounts acc){
    	System.out.println("post mapping hit...");
    	System.out.println("request we get : "+ acc);
    	Accounts savedAcc = accountRepo.save(acc);
    	return ResponseEntity.ok(savedAcc);
    }
}