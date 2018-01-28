package com.test.eventbustest;

/**
 * Created by Administrator on 2018/1/28 0028.
 */

public class MyEvent {
    private String msg;

    public MyEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
