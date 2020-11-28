package com.cjh.tp.sdk.example;

import com.cjh.tp.sdk.ThreadPoolFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: tp
 * @description:
 * @author: chenjiehan
 * @create: 2020-10-26 22:37
 **/
@RestController
@RequestMapping("demo")
public class Demo {
    ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.getThreadPoolExecutor("abcd");

    @PostMapping("/hello")
    public String demo() {
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return "hello";
    }

    @PostMapping("/hello2")
    public String demo2() {
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return "hello2";
    }

    @PostMapping("/hello3")
    public String demo3() {
        threadPoolExecutor.getQueue().clear();
        return "hello3";
    }

}
