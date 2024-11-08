package cn.edu.xmu.javaee.Lab5_SpringDataJPA;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.javaee.core",
		"cn.edu.xmu.javaee.Lab5_SpringDataJPA"})
@EnableJpaRepositories(basePackages = "cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.jpa")
@MapperScan({"cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.manual",
		"cn.edu.xmu.javaee.Lab5_SpringDataJPA.mapper.generator"})
public class SpringDataJPAApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJPAApplication.class, args);
	}

}
