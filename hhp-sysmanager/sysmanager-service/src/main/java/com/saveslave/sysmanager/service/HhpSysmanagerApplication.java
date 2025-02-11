package com.saveslave.sysmanager.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication
@EnableWebMvc
@EnableFeignClients
@ComponentScan({"com.saveslave.sysmanager.service", "com.saveslave.commons"})
@MapperScan({"com.saveslave.sysmanager.service.persistent.mapper", "com.saveslave.commons.aspect.persistent.mapper"})
public class HhpSysmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhpSysmanagerApplication.class, args);
    }

}
