package io.xiaoyaoyou.xmall.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ImportResource({"classpath:/dubbo/spring-dubbo.xml"})
public class XmallSimulatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(XmallSimulatorApplication.class, args);
	}
}
