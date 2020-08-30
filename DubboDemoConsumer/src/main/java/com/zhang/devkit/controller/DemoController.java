package com.zhang.devkit.controller;

import com.zhang.devkit.DemoItem;
import com.zhang.devkit.wrapper.DemoServiceWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DemoController {

    @Autowired
    DemoServiceWrapper demoServiceWrapper;



    @GetMapping("/")
    public String index() {
        return "This is home page!";
    }



    @GetMapping("/demo2/{key}")
    public List<DemoItem> getItems2(@PathVariable(name="key")  String key) {
        return demoServiceWrapper.getItems2(key);
    }



    @GetMapping("/demo/{key}")
    public String getItems(@PathVariable(name="key") String key) {
        return demoServiceWrapper.getItems(key);
    }







}
