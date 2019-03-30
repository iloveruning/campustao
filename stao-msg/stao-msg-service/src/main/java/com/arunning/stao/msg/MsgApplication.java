package com.arunning.stao.msg;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.LockSupport;

/**
 * @author chenliangliang
 * @date 2019/3/30
 */
@EnableDubbo
@SpringBootApplication
public class MsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class, args);
        LockSupport.park();
    }
}
