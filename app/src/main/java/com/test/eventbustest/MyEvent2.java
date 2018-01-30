package com.test.eventbustest;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class MyEvent2 {
    private String msg;

    public MyEvent2(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
