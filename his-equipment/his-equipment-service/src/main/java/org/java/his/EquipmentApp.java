package org.java.his;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "org.java.his.equipment.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class EquipmentApp {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentApp.class,args);
    }
}
