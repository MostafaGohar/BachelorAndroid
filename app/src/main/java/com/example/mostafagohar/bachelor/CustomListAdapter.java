package com.example.mostafagohar.bachelor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Mostafa Gohar on 29/11/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Object> listData;
    public LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<Object> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.comments_layout, null);
            holder = new ViewHolder();
            holder.commentView = (TextView) convertView.findViewById(R.id.comment);
            holder.commenterView = (TextView) convertView.findViewById(R.id.commenter);
            holder.dateView = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.commentView.setText(listData.get(position).getComment());
//        holder.commenterView.setText("By, " + listData.get(position).getCommenter());
//        holder.dateView.setText(listData.get(position).getDate());
        if(listData.get(position) instanceof Comment) {
            Comment comment = (Comment) listData.get(position);
            holder.commentView.setText(comment.getContent());
            holder.commenterView.setText("By " + comment.getUser().getFname());
            holder.dateView.setText(comment.getCreated_at());
        }else{
            if(listData.get(position) instanceof Post) {
                Post post = (Post) listData.get(position);
                holder.commentView.setText(post.getTitle());
                holder.commenterView.setText("By " + post.getUser().getFname());
                holder.dateView.setText(post.getCreated_at());
            }
        }
        return convertView;
    }

    static class ViewHolder {
        TextView commentView;
        TextView commenterView;
        TextView dateView;
    }
}
