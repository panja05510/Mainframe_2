package com.citizens.mainframe.Config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.citizens.mainframe.model.Accounts;
import com.citizens.mainframe.repository.AccountRepository;


@Component
public class CacheInitializer implements ApplicationRunner {

    private boolean cacheInitialized = false;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CacheManager cacheManager;
    public void run(ApplicationArguments args) throws Exception {
        if (!cacheInitialized) {
            List<Accounts> accounts = accountRepo.findAll();
            Cache cache = cacheManager.getCache("accountsCache");
            if (cache != null) {
                for (Accounts acc : accounts) {
                    cache.put(acc.getId(), acc); // Assuming getId() gives the unique identifier for account
                    System.out.println("Data stored in cache: " + acc.toString());
                }
                System.out.println("Data fetched to cache from db");
                cacheInitialized = true;
            }
        }
    }
}