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

    public List<DemoItem> getItems(String userID) {
        return null;
    }

    public boolean addItem(String userID, String itemID, int itemNum) {
        return false;
    }
}
