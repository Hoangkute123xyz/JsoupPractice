package com.hoangpro.jsouppractice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubePlayer;
import com.hoangpro.jsouppractice.R;
import com.hoangpro.jsouppractice.model.LyricObject;
import com.hoangpro.jsouppractice.morefunc.MySession;

import java.util.List;
import java.util.logging.Handler;

public class LyricAdapater extends RecyclerView.Adapter<LyricAdapater.LyricHolder> {
    private List<LyricObject.Datum> list;
    private Context context;
    private YouTubePlayer player;
    private Handler handler;
    private Thread thread;
    private RecyclerView rvLyrics;

    public LyricAdapater(List<LyricObject.Datum> list, Context context, YouTubePlayer player, RecyclerView rvLyrics) {
        this.list = list;
        this.context = context;
        this.player = player;
        this.rvLyrics = rvLyrics;
    }

    @NonNull
    @Override
    public LyricHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LyricHolder(LayoutInflater.from(context).inflate(R.layout.item_lyric, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final LyricHolder holder, int position) {
        final LyricObject.Datum datum = list.get(position);
        holder.tvValue.setText(datum.getSentenceValue());
        holder.tvRo.setText(datum.getSentenceRo());
        holder.tvTraslate.setText(datum.getSentenceTranslates());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekToMillis((int) MySession.toMilies(datum.getStartTime()));
            }
        });
        if (MySession.toMilies(datum.getStartTime())<=player.getCurrentTimeMillis() &&
        MySession.toMilies(datum.getEndTime())>=player.getCurrentTimeMillis()){
            if (position < list.size() - 3)
                rvLyrics.smoothScrollToPosition(position + 2);
            holder.itemView.setBackgroundColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class LyricHolder extends RecyclerView.ViewHolder {
        ImageView imgPlay;
        TextView tvValue;
        TextView tvRo;
        TextView tvTraslate;

        public LyricHolder(@NonNull View itemView) {
            super(itemView);
            imgPlay = itemView.findViewById(R.id.imgPlay);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvRo = itemView.findViewById(R.id.tvRo);
            tvTraslate = itemView.findViewById(R.id.tvTraslate);
        }
    }
}
