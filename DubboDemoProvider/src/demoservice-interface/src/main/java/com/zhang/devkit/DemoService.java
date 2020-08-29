package com.zhang.devkit;

import java.util.List;

public interface DemoService {

    List<DemoItem> getItems(String key);

    boolean addItem(String key, String itemID, int itemNum,String itemName);


}


