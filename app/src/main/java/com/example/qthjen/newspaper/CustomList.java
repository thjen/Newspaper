package com.example.qthjen.newspaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomList extends BaseAdapter {

    public Context          mContext;
    public List<Attributes> mList;
    public int              mLayout;

    public CustomList(Context context, List<Attributes> list, int layout) {
        mContext = context;
        mList    = list;
        mLayout  = layout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {

        ImageView ivList;
        TextView  tvList;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = new ViewHolder();

        if ( view == null) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(mLayout, null);
            /** Ánh xạ **/
            viewHolder.ivList = (ImageView) view.findViewById(R.id.ivList);
            viewHolder.tvList = (TextView)  view.findViewById(R.id.tvList);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        /** gán giá trị **/
        Picasso.with(mContext).load(mList.get(i).mImage).into(viewHolder.ivList);
        viewHolder.tvList.setText(mList.get(i).mTitle);

        /** sử dụng animation**/
//        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_listview);
//        view.startAnimation(animation);

        return view;
    }
}
