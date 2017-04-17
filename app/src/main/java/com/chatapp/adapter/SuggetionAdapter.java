package com.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chatapp.R;
import com.chatapp.model.SuggetionModel;
import com.mindorks.placeholderview.ViewHolder;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ANKIT on 4/1/2017.
 */

public class SuggetionAdapter extends BaseAdapter {
    Context context;
    ArrayList<SuggetionModel> suggetionList;
    private static LayoutInflater inflater = null;

    public SuggetionAdapter(Context context, ArrayList<SuggetionModel> suggetionList) {
        // TODO Auto-generated constructor stub
        this.suggetionList = suggetionList;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return suggetionList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class ViewHolder {
        TextView tvName;
        TextView tvProfession;
        TextView tvAddress;
        TextView tvDistance;
        ImageView imgProfile;
        ImageView imgLike;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_item_suggestion, parent,false);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.row_item_suggetion_tv_address);
            holder.tvName = (TextView) convertView.findViewById(R.id.row_item_suggetion_tv_name);
            holder.tvProfession = (TextView) convertView.findViewById(R.id.row_item_suggetion_tv_profession);
            holder.tvDistance = (TextView) convertView.findViewById(R.id.row_item_suggetion_tv_distance);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.row_item_suggestion_img_profile);
            holder.imgLike = (ImageView) convertView.findViewById(R.id.row_item_suggestion_img_like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(suggetionList.get(position).getName() + "," + suggetionList.get(position).getAge());
        holder.tvAddress.setText(suggetionList.get(position).getLocation());
        holder.tvDistance.setText(suggetionList.get(position).getDistance());
        holder.tvProfession.setText(suggetionList.get(position).getProfession());
        Glide.with(context).load(suggetionList.get(position).getImageUrl()).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
                .bitmapTransform(new RoundedCornersTransformation(context, context.getResources().getDimensionPixelSize(R.dimen._2sdp), 0))
                .into(holder.imgProfile);
        return convertView;
    }
}
