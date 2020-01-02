package com.hoangpro.jsouppractice.activity;

import android.animation.Keyframe;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.service.autofill.UserData;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.hoangpro.jsouppractice.R;
import com.hoangpro.jsouppractice.adapter.LyricAdapater;
import com.hoangpro.jsouppractice.model.LyricObject;
import com.hoangpro.jsouppractice.model.SongJSONObject;
import com.hoangpro.jsouppractice.morefunc.MySession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VIewVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener {

    private YouTubePlayerView ytPlay;
    private SongJSONObject.Song song;
    private YouTubePlayer player;
    private List<LyricObject.Datum> list;
    String URL = "http://pikasmart.com/api/appLyricFinderSongSentences/getListLyricsBylanguageCode?song_id=";
    private RecyclerView rvDatum;
    private LyricAdapater adapter;
    private Thread thread;
    private Handler handler;
    private final int UPDATE_CODE=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        song = new Gson().fromJson(MySession.currentJson, SongJSONObject.Song.class);
        URL += song.getId() + "&language_code=vn";
        initView();
        ytPlay.initialize(getString(R.string.Youtube_api_key), this);
        list = new ArrayList<>();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what== UPDATE_CODE && player!=null){
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }

    private void initView() {
        ytPlay = findViewById(R.id.ytPlay);
        rvDatum = findViewById(R.id.rvDatum);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        player = youTubePlayer;
        youTubePlayer.cueVideo(song.getUrl().split("v=")[1]);
        player.setPlayerStateChangeListener(this);
        adapter = new LyricAdapater(list, this, player,rvDatum);
        rvDatum.setAdapter(adapter);
        rvDatum.setLayoutManager(new LinearLayoutManager(this));
        new GetLyrics().execute(URL.trim());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 123);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            ytPlay.initialize(getString(R.string.Youtube_api_key), this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {
        player.play();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Message message = new Message();
                    message.what = UPDATE_CODE;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    class GetLyrics extends AsyncTask<String, Void, List<LyricObject.Datum>> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();

        @Override
        protected List<LyricObject.Datum> doInBackground(String... strings) {
            List<LyricObject.Datum> list = new ArrayList<>();
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                LyricObject object = new Gson().fromJson(json, LyricObject.class);
                list.addAll(object.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<LyricObject.Datum> data) {
            super.onPostExecute(data);
            for (LyricObject.Datum datum:data){
                if (datum.getSentenceValue().length()>0){
                    list.add(datum);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null)
            player.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null)
            player.pause();
    }
}
