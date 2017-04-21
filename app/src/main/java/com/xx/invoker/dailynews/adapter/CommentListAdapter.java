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
import com.xx.invoker.dailynews.model.Comment;
import com.xx.invoker.dailynews.view.CircleImageView;

import java.util.List;

/**
 * Created by invoker on 2017/4/21.
 */

public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> data;

    public CommentListAdapter(Context context, List<Comment> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_comment, parent, false);
            holder = new ViewHolder();
            holder.author = (TextView) convertView.findViewById(R.id.author_comment);
            holder.content = (TextView) convertView.findViewById(R.id.content_comment);
            holder.image = (CircleImageView) convertView.findViewById(R.id.image_author_comment);
            holder.praise = (TextView) convertView.findViewById(R.id.like_comment);
            holder.replyContent = (TextView) convertView.findViewById(R.id.replyContent_comment);
            holder.time = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        Comment comment = data.get(position);
        holder.praise.setText(comment.getLike());
        holder.author.setText(comment.getAuthor());
        holder.content.setText(comment.getContent());
        holder.time.setText(comment.getTime());
        if (!TextUtils.isEmpty(comment.getReplyAuthor()))
            holder.replyContent.setText("//" + comment.getReplyAuthor() + ": " + comment.getReplyContent());
        else
            holder.replyContent.setVisibility(View.GONE);

        Glide.with(context).load(comment.getUrl()).into(holder.image);
        return convertView;
    }

    class ViewHolder {
        TextView content, replyContent, author, praise,time;
        CircleImageView image;
    }
}
