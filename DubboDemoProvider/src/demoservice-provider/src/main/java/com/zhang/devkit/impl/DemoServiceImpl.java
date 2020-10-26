package com.zhang.devkit.impl;

import java.util.List;

import com.zhang.devkit.DemoItem;
import com.zhang.devkit.DemoService;
import org.apache.dubbo.config.annotation.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RefreshScope
@Service(version = "1.0.0")
public class DemoServiceImpl implements DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    private ConcurrentHashMap<String, List<DemoItem>> demoItems = new ConcurrentHashMap<>();


    public List<DemoItem> getItems(String key) {

        List<DemoItem> newRes = new ArrayList<>();
        /*
        List<DemoItem> res = demoItems.getOrDefault(key, Collections.emptyList());

        if (res != null) {
            for (DemoItem demoItem : res) {
                DemoItem newDemo = new DemoItem(demoItem.id, demoItem.num,demoItem.name);
                newRes.add(newDemo);
                System.out.println(newDemo);
            }
        }
        */
        // demp

        for (int i=1; i<10; i++) {
            DemoItem newDemo;
            newDemo = new DemoItem("key"+i, i+100,"demo"+i);
            newRes.add(newDemo);
            System.out.println(newDemo);
        }

        return newRes;
    }

    public boolean addItem(String key, String itemID, int itemNum, String itemName) {

        List<DemoItem> itemList = demoItems.computeIfAbsent(key, k -> new ArrayList<>());
        for (DemoItem item : itemList) {
            if (item.id.equals(itemID)) {
                item.num = item.num + itemNum;
                return true;
            }
        }
        itemList.add(new DemoItem(itemID, itemNum, itemName));
        return false;
    }
}
