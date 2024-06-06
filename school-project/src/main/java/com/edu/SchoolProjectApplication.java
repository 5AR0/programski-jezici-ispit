package com.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolProjectApplication.class, args);
	}

}


//-- OVERRIDE findALL() method in CrudRepository - returns List, not Iterable (  Iterable<T> findAll(); )
//public interface NotificationRepository extends CrudRepository<NotificationEntity, Integer> {
//
//	@Override
//	List<NotificationEntity> findAll();