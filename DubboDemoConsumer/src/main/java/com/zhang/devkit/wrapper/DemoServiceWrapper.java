package com.zhang.devkit.wrapper;

import com.zhang.devkit.DemoItem;
import com.zhang.devkit.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DemoServiceWrapper {

    @Reference(version = "1.0.0")
    private DemoService demoService;


    public List<DemoItem> getItems2(String key) {


        return demoService.getItems(key);
    }


    public String getItems(String key) {


        return demoService.getItems(key).toString();
    }

}
