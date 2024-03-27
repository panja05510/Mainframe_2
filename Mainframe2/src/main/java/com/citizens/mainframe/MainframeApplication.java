package com.citizens.mainframe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MainframeApplication implements CommandLineRunner{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MainframeApplication.class, args);
		System.out.println(
				"**************************************************************************** \n"
				+ "*                          MAINFRAME                                     *\n"
			    +"**************************************************************************** \n"
				);
	}
	@Override
    public void run(String... args) throws Exception {
        try {
            // Attempt to perform a simple database operation
            mongoTemplate.getCollectionNames();
            System.out.println("*************************************MongoDB connected successfully!*******************************************");
        } catch (Exception e) {
            System.err.println("MongoDB connection error: " + e.getMessage());
            // Optionally, you can rethrow the exception or handle it differently
        }
    }

}
