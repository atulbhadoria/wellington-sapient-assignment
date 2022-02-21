package com.sapient.wellingtonsapientassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WellingtonSapientAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellingtonSapientAssignmentApplication.class, args);
	}

}
