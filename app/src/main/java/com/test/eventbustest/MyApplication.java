package com.test.eventbustest;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}
