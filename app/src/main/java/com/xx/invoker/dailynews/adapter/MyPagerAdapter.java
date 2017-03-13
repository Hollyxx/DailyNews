package com.xx.invoker.dailynews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xx.invoker.dailynews.R;
import com.xx.invoker.dailynews.model.HeadNews;

import java.util.List;

/**
 * Created by invoker on 2017/3/5.
 */

public class MyPagerAdapter extends PagerAdapter {

    private List<HeadNews> data;
    private Context context;
    private LayoutInflater inflater;

    public MyPagerAdapter(Context context, List<HeadNews> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view= (View) object;
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_head__pager_list_home, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image_header_list_home);
        TextView title = (TextView) view.findViewById(R.id.title_pager_header_list_home);

        HeadNews news = data.get(position);

        title.setText(news.getTitle());
        Glide.with(context).load(news.getImage()).into(image);
        container.addView(view);
        return view;
    }
}
