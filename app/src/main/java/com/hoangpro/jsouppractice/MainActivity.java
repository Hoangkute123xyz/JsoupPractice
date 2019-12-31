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
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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
        new GetNetWorkStatus().execute();
    }

    List<SongJSONObject.Song> getSongFromdb() {
        return SQLite.select().from(SongJSONObject.Song.class).queryList();
    }

    private void initView() {
        rvPost = findViewById(R.id.rvPost);
        pgLoadmore = findViewById(R.id.pgLoadmore);
        pgLoadmore.setVisibility(View.VISIBLE);
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
                list = jsonObject.getSong();
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
            if (currentPage == 0) {
                FlowManager.getDatabase(SongDbFlow.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<SongJSONObject.Song>() {
                                    @Override
                                    public void processModel(SongJSONObject.Song song, DatabaseWrapper wrapper) {
                                        song.save();
                                    }
                                }).addAll(list).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {

                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {

                            }
                        }).build().execute();
            }
            currentPage++;
        }
    }

    class GetNetWorkStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                int timeoutMs = 1500;
                Socket sock = new Socket();
                SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

                sock.connect(sockaddr, timeoutMs);
                sock.close();

                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean b) {
            super.onPostExecute(b);
            pgLoadmore.setVisibility(View.GONE);
            list = b ? new ArrayList<SongJSONObject.Song>() : getSongFromdb();
            Log.e("size", list.size() + "");
            adapter = new PostAdapter(MainActivity.this, list);
            rvPost.setAdapter(adapter);
            rvPost.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            rvPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager.findLastVisibleItemPosition() == list.size() - 1 && list.size() > 0 && b) {
                        pgLoadmore.setVisibility(View.VISIBLE);
                        new GetContent().execute(URL + currentPage);
                    }
                }
            });
            new GetContent().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URL + currentPage);
        }
    }

}
