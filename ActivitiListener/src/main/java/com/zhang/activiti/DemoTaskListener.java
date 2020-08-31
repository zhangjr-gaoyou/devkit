package com.zhang.devkit.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class DemoTaskListener implements TaskListener {


    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        if ("create".endsWith(eventName)) {

            delegateTask.setAssignee("Demo_"+delegateTask.getAssignee());
            System.out.println("create=========");
        }else
            return;

    }
}
