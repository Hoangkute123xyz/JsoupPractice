package com.hoangpro.jsouppractice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPost;
    private String TAG = "MainActivity";
    private List<SongJSONObject.Song> list;
    private int currentPage = 0;
    private final String URL = "http://pikasmart.com/api/Songs/listnew?limit=10&skip=";
    PostAdapter adapter;
    private ProgressBar pgLoadmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        list = new ArrayList<>();
        adapter = new PostAdapter(this, list);
        rvPost.setAdapter(adapter);
        rvPost.setLayoutManager(new GridLayoutManager(this, 2));
        rvPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager= (GridLayoutManager) recyclerView.getLayoutManager();
                Log.e("TAG", "position: "+layoutManager.findLastVisibleItemPosition());
                if(layoutManager.findLastVisibleItemPosition()==list.size()-1 && list.size()>0){
                    pgLoadmore.setVisibility(View.VISIBLE);
                    new GetContent().execute(URL+currentPage);
                    Log.e(TAG, "isLoadMore: true");
                }
            }
        });
        new GetContent().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URL+currentPage);
    }

    private void initView() {
        rvPost = findViewById(R.id.rvPost);
        pgLoadmore = findViewById(R.id.pgLoadmore);
    }

    class GetContent extends AsyncTask<String, String, List<SongJSONObject.Song>> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();

        @Override
        protected List<SongJSONObject.Song> doInBackground(String... strings) {
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);
            Request request = builder.build();
            List<SongJSONObject.Song> list = new ArrayList<>();
            try {
                Response response = okHttpClient.newCall(request).execute();
                String json = response.body().string();
                SongJSONObject jsonObject = new Gson().fromJson(json, SongJSONObject.class);
                list=jsonObject.getSong();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<SongJSONObject.Song> list1) {
            super.onPostExecute(list1);
            list.addAll(list1);
            adapter.notifyDataSetChanged();
            pgLoadmore.setVisibility(View.GONE);
            currentPage++;
        }
    }
}
