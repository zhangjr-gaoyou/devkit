package com.zhang.devit.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Component
public class DemoProviderDisposer implements DisposableBean {


    @NacosInjected
    private NamingService namingService;

    @Resource
    private Environment env;

    @Override
    public void destroy() {


        String hostAddress = "localhost";



        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        }


        try {
            namingService.deregisterInstance(env.getProperty("spring.application.name"),
                    hostAddress,
                    Integer.valueOf(env.getProperty("server.port")));
        }catch(NacosException e){
            e.printStackTrace();
        }

    }

}
