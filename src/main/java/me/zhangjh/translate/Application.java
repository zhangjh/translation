package me.zhangjh.translate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "me.zhangjh")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
