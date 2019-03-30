package com.arunning.stao.ic;

import com.arunning.vertx.web.spring.config.EnableVertxWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@EnableVertxWeb
@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
public class ICApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ICApplication.class, args);

    }
}
