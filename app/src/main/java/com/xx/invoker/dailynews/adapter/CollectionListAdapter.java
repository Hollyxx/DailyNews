package com.xx.invoker.dailynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xx.invoker.dailynews.R;
import com.xx.invoker.dailynews.model.News;

import java.util.List;

/**
 * Created by invoker on 2017/4/12.
 */

public class CollectionListAdapter extends BaseAdapter {

    private Context context;
    private List<News> data;

    public CollectionListAdapter(Context context, List<News> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_item_list_home, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image_item_item_list_home);
            holder.title = (TextView) convertView.findViewById(R.id.title_item_item_list_home);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        News news = data.get(position);
        holder.title.setText(news.getTitle());
        Glide.with(context).load(news.getImage()).into(holder.image);

        return convertView;
    }

    class ViewHolder {
        TextView title;
        ImageView image;
    }
}
