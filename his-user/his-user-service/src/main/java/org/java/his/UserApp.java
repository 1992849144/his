package org.java.his;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "org.java.his.user.dao")
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class,args);
    }
}
