package com.test.eventbustest;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/1/28 0028.
 */

public class LeftFragment extends ListFragment {
    public static String TAG = "Test";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String strs[] = {"主线程消息1","子线程消息1","主线程消息2"};
        setListAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,strs));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position){
            case 0:
                EventBus.getDefault().post(new MyEvent("Hello,我来自主线程1"));
                Log.d(TAG,"从主线程1出发，threadName="+Thread.currentThread().getName()
                +";threadId="+Thread.currentThread().getId());
                break;
            case 1:
                new Thread(){
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new MyEvent("Hello,我来自子线程1"));
                        Log.d(TAG,"从子线程1出发，threadName="+Thread.currentThread().getName()
                                +";threadId="+Thread.currentThread().getId());
                    }
                }.start();

                break;
            case 2:
                EventBus.getDefault().post(new MyEvent("Hello,我来自主线程2"));
                Log.d(TAG,"从主线程2出发，threadName="+Thread.currentThread().getName()
                        +";threadId="+Thread.currentThread().getId());
                break;

        }
    }
}
