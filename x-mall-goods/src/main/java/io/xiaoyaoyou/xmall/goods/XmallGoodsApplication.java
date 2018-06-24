package io.xiaoyaoyou.xmall.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({ "io.xiaoyaoyou.xmall"})
@ImportResource({"classpath:dubbo/spring-dubbo.xml"})
@EnableAsync
public class XmallGoodsApplication {
	public static void main(String[] args) {
		SpringApplication.run(XmallGoodsApplication.class, args);
	}
}
