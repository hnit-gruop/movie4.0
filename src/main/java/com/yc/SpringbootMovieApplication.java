package com.yc;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.aliyuncs.exceptions.ClientException;
import com.yc.util.OrderNoGenerator;
import com.yc.util.PhoneUtil;

@SpringBootApplication
@MapperScan("com.yc.dao")
@EnableCaching
@EnableScheduling
public class SpringbootMovieApplication {
	public static void main(String[] args) throws ClientException {
		SpringApplication.run(SpringbootMovieApplication.class, args);
		
//		OrderNoGenerator orderNoGenerator = new OrderNoGenerator(5, 11);
//		
//		for(int i = 0;i<100;i++) {
//			String generatorOrderNo = orderNoGenerator.generatorOrderNo();
//			System.out.println(generatorOrderNo);
//		}
		
		
	}
}
