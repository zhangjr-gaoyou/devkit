package com.zhang.devit.nacos.controller;

import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("demo")
public class DemoController {

    @RequestMapping(value = "/get", method = GET)
    public String get(@RequestParam(value = "name") String name)  {
        return "Hello,"+name;
    }
}
