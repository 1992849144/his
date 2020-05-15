package org.java.his;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.UUID;

@MapperScan(basePackages = "org.java.his.drugwarehouse.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class DrugwarehouseApp {
    public static void main(String[] args) {
//        String uuid = UUID.randomUUID().toString();
//        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
//        uuid = uuid.replace("-", "");
//        System.out.println(uuid);

        SpringApplication.run(DrugwarehouseApp.class,args);
    }
}
