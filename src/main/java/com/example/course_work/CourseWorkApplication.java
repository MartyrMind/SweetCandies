package com.example.course_work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
public class CourseWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseWorkApplication.class, args);
    }

}
