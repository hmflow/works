package com.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.web.mapper")
public class StartWeb {
	public static void main(String[] args) {
		SpringApplication.run(StartWeb.class, args);
	}
}
