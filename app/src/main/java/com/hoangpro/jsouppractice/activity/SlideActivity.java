package com.hoangpro.jsouppractice.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.hoangpro.jsouppractice.R;
import com.hoangpro.jsouppractice.adapter.SlideAdapter;
import com.hoangpro.jsouppractice.model.SongJSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SlideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private String URL = "http://pikasmart.com/api/Songs/listnew?limit=6&skip=0";
    private List<SongJSONObject.Song> list;
    SlideAdapter adapter;
    private Handler handler;
    private int currentItem = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_giude);
        initView();
        list = new ArrayList<>();
        new MyApi().execute(URL);
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
    }

    class MyApi extends AsyncTask<String, Void, List<SongJSONObject.Song>> {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();

        @Override
        protected List<SongJSONObject.Song> doInBackground(String... strings) {
            List<SongJSONObject.Song> list = new ArrayList<>();
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                SongJSONObject object = new Gson().fromJson(json, SongJSONObject.class);
                list = object.getSong();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<SongJSONObject.Song> songs) {
            super.onPostExecute(songs);
            list.addAll(songs);
            adapter = new SlideAdapter(list, getApplicationContext());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentItem=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    switch (msg.what) {
                        case 1:
                            viewPager.setCurrentItem(currentItem);
                            currentItem++;
                            if (currentItem==list.size()){
                                currentItem=0;
                            }
                            break;
                    }
                }
            };
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(3000);
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

    }
}
