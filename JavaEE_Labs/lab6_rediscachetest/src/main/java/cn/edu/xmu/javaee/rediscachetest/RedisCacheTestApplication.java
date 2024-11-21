package cn.edu.xmu.javaee.rediscachetest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.javaee.core",
		"cn.edu.xmu.javaee.rediscachetest"})
@EnableJpaRepositories(basePackages = "cn.edu.xmu.javaee.rediscachetest.mapper.jpa")
@MapperScan({"cn.edu.xmu.javaee.rediscachetest.mapper.manual",
		"cn.edu.xmu.javaee.rediscachetest.mapper.generator"})
public class RedisCacheTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheTestApplication.class, args);
	}

}
