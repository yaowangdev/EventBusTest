package com.test.eventbustest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2018/1/28 0028.
 */

public class RightFragment extends Fragment {
    private TextView tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.right_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv = view.findViewById(R.id.tv_right);
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
}
