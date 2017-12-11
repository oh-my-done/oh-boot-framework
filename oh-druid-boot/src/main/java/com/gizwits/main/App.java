package com.gizwits.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.gizwits"})
@Slf4j
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}
