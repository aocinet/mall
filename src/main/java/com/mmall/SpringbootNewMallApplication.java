package com.mmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.mmall.dao")
@EnableScheduling
@ServletComponentScan   //使用过滤器时使用
public class SpringbootNewMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootNewMallApplication.class, args);
	}

}
