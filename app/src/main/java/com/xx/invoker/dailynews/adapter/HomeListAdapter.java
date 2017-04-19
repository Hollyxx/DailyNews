package com.xx.invoker.dailynews.adapter;

import android.content.Context;
import android.text.TextUtils;
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
 * Created by invoker on 2017/3/4.
 */

public class HomeListAdapter extends BaseAdapter {

    private Context context;
    private List<Object> data;

    public HomeListAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = getItemViewType(position);

        if (type==0){
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.title_item_list_home, parent, false);

                holder = new ViewHolder();
                holder.date = (TextView) convertView.findViewById(R.id.title_item_list_home);
                convertView.setTag(holder);
            }else
                holder = (ViewHolder) convertView.getTag();

            String date = (String) data.get(position);
            holder.date.setText(date);
        }
        if (type == 1){
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_item_list_home, parent, false);

                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image_item_item_list_home);
                holder.title = (TextView) convertView.findViewById(R.id.title_item_item_list_home);
                convertView.setTag(holder);
            }else
                holder = (ViewHolder) convertView.getTag();

            News news = (News) data.get(position);
            holder.title.setText(news.getTitle());
            if (!TextUtils.isEmpty(news.getImage()))
                Glide.with(context).load(news.getImage()).into(holder.image);
            else
                holder.image.setVisibility(View.GONE);
        }


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Object o = data.get(position);

        if (o instanceof String) {
            return 0;
        } else {
            return 1;
        }
    }

    class ViewHolder {
        TextView date;
        TextView title;
        ImageView image;
    }


}
