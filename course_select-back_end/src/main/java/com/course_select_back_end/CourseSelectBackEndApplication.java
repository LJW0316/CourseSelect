package com.course_select_back_end;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.course_select_back_end.mapper")
public class CourseSelectBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseSelectBackEndApplication.class, args);
    }

}
