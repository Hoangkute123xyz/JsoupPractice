package com.hoangpro.jsouppractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

class PostHolder extends RecyclerView.ViewHolder {

    ImageView imgThumb;
    TextView tvView;
    TextView tvDuration;
    TextView tvNameJ;
    TextView tvNameVn;
    TextView tvLevel;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        imgThumb = itemView.findViewById(R.id.imgThumb);
        tvView = itemView.findViewById(R.id.tvView);
        tvDuration = itemView.findViewById(R.id.tvDuration);
        tvNameJ = itemView.findViewById(R.id.tvNameJ);
        tvNameVn = itemView.findViewById(R.id.tvNameVn);
        tvLevel = itemView.findViewById(R.id.tvLevel);
    }
}

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {
    private Context context;
    private List<SongJSONObject.Song> list;

    public PostAdapter(Context context, List<SongJSONObject.Song> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostHolder(LayoutInflater.from(context).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        SongJSONObject.Song post = list.get(position);
        Glide.with(context).load(post.getThumbnail()).into(holder.imgThumb);
        holder.tvView.setText(post.getView() + "");
        if (post.getView()>=1000){
            holder.tvView.setText(String.format("%.2fk", (double)post.getView()/1000));
        }
        holder.tvDuration.setText(post.getVideoLength());
        holder.tvNameJ.setText(post.getName());
        holder.tvNameVn.setText(post.getNameVn());
        holder.tvLevel.setText(getLevel(post.getLevelId(),holder));
    }

    private String getLevel(int Level,PostHolder holder) {
        switch (Level) {
            case 12:
                holder.tvLevel.setBackgroundResource(R.drawable.label_medium);
                return context.getString(R.string.medium);
            case 13:
                holder.tvLevel.setBackgroundResource(R.drawable.label_hard);
                return context.getString(R.string.hard);
            default:
                holder.tvLevel.setBackgroundResource(R.drawable.label_easy);
                return context.getString(R.string.easy);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
