package com.test.eventbustest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2018/1/28 0028.
 */

public class RightFragment extends Fragment implements View.OnClickListener {
    private TextView tv;
    private Button btn1,btn2,btn3,btn4,btn5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long l1 = System.currentTimeMillis();
        Log.d("registEffic",l1+"");
        EventBus.getDefault().register(this);
        long l2 = System.currentTimeMillis();
        Log.d("registEffic",l2+"");
        Log.d("registEffic",l2-l1+"");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.right_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    //处理事件的线程与事件来源的线程一致
    @Subscribe(threadMode=ThreadMode.POSTING)
    public void onEvent(MyEvent event){
        Log.d(LeftFragment.TAG,"ThreadMode.POSTING==>threadName="+Thread.currentThread().getName()
                +";threadId="+Thread.currentThread().getId()+";"+event.getMsg());
    }
    //一直在主线程处理事件
    @Subscribe(threadMode =ThreadMode.MAIN)
    public void onEventMainThread(MyEvent event){
        Log.d(LeftFragment.TAG,"ThreadMode.MAIN==>threadName="+Thread.currentThread().getName()
                +";threadId="+Thread.currentThread().getId()+";"+event.getMsg());
    }
    //处理事件的线程与事件来源线程不一致
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsync(MyEvent event){
        Log.d(LeftFragment.TAG,"ThreadMode.ASYNC==>threadName="+Thread.currentThread().getName()
                +";threadId="+Thread.currentThread().getId()+";"+event.getMsg());
    }

    //如果事件来源线程为主线程，则新开线程处理事件，
    //如果事件来源线程为子线程，则与事件来源线程一致
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(MyEvent event){
        Log.d(LeftFragment.TAG,"ThreadMode.BACKGROUND==>threadName="+Thread.currentThread().getName()
                +";threadId="+Thread.currentThread().getId()+";"+event.getMsg());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                EventBus.getDefault().postSticky(new MyEvent2("消息1"));
                break;
            case R.id.btn2:
                EventBus.getDefault().postSticky(new MyEvent2("消息2"));
                break;
            case R.id.btn3:
                EventBus.getDefault().postSticky(new MyEvent2("消息3"));
                break;
            case R.id.btn4:
                EventBus.getDefault().register(this);//不能注册2次
                break;
            case R.id.btn5:
                EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 80)
    public void getEvent(MyEvent2 event2){
        Log.d("getEvent","getEvent=>"+event2.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.POSTING,priority = 90)
    public void getEvent2(MyEvent2 event2){
        Log.d("getEvent","getEvent2="+event2.getMsg());
//        EventBus.getDefault().cancelEventDelivery(event2);
        EventBus.getDefault().cancelEventDelivery(event2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 60)
    public void getEvent3(MyEvent2 event2){
        Log.d("getEvent","getEvent3="+event2.getMsg());
    }
}
