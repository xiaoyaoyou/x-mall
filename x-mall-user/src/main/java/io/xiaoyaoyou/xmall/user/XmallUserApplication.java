package io.xiaoyaoyou.xmall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Configuration
@ImportResource({"classpath:/dubbo/spring-dubbo.xml"})
@EnableAsync
public class XmallUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(XmallUserApplication.class, args);
	}
}
