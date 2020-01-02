package com.hoangpro.jsouppractice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hoangpro.jsouppractice.R;
import com.hoangpro.jsouppractice.activity.VIewVideo;
import com.hoangpro.jsouppractice.model.SongJSONObject;

import java.util.List;

import static com.hoangpro.jsouppractice.morefunc.MySession.currentJson;

public class SlideAdapter extends PagerAdapter {
    private List<SongJSONObject.Song> list;
    private Context context;

    public SlideAdapter(List<SongJSONObject.Song> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_slide, container,false);

        final SongJSONObject.Song song=list.get(position);
        ImageView imgSlide=view.findViewById(R.id.imgSlide);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.append(song.getName()+"\n");
        tvTitle.append(song.getNameVn());
        Glide.with(context).load(song.getThumbnail()).into(imgSlide);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentJson= new Gson().toJson(song);
                context.startActivity(new Intent(context, VIewVideo.class));
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

}
