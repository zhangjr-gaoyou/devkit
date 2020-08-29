package com.zhang.devkit;

import java.util.List;

public interface DemoService {

    List<DemoItem> getItems(String userID);

    boolean addItem(String userID, String itemID, int itemNum);


}


