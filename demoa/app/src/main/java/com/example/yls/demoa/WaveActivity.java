package com.example.yls.demoa;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class WaveActivity extends AppCompatActivity {
    private static final  int MSG_REFRESH = 1001;
    private WaveSwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayAdapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        initHandler();
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        listView = findViewById(R.id.mylist);
        data.add("软件");
        adapter =new ArrayAdapter<String>(WaveActivity.this,
                android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (int i =0;i<10;i++){
                            data.add("下拉刷新"+i);
                        }
                        handler.sendEmptyMessage(MSG_REFRESH);
                    }
                }).start();
            }
        });
    }

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(message.what ==MSG_REFRESH){
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
                return false;
            }
        });
    }
}
