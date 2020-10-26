package com.zhang.devit.nacos;


import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.annotation.NacosProperty;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication
public class SpringBootDemoProvider implements CommandLineRunner {



    @NacosInjected
    private NamingService namingService;


    @Resource
    private Environment env;

    public static void main(String[] args) {

        SpringApplication.run(SpringBootDemoProvider.class,args);
    }

    @Override
    public void run(String... args) throws Exception {




        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        }


        try {
            namingService.registerInstance(env.getProperty("spring.application.name"),
                    hostAddress,
                    Integer.valueOf(env.getProperty("server.port")));
        }catch(NacosException e){
            e.printStackTrace();
        }


    }



}
