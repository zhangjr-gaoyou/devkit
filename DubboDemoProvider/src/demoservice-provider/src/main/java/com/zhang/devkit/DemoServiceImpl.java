package com.zhang.devkit;

import java.util.List;

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
        List<DemoItem> res = demoItems.getOrDefault(key, Collections.emptyList());
        List<DemoItem> newRes = new ArrayList<>();
        if (res != null) {
            for (DemoItem demoItem : res) {
                DemoItem newDemo = new DemoItem(demoItem.id, demoItem.num,demoItem.name);
                newRes.add(newDemo);
                System.out.println(newDemo);
            }
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
