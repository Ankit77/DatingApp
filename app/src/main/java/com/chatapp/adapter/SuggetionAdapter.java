package com.chatapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.model.SuggetionModel;
import com.chatapp.model.dashboard.UserDetailModel;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.internal.Utils;

/**
 * Created by ANKIT on 4/1/2017.
 */

public class SuggetionAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserDetailModel> suggetionList;
    private static LayoutInflater inflater = null;

    public SuggetionAdapter(Context context, ArrayList<UserDetailModel> suggetionList) {
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
            convertView = inflater.inflate(R.layout.row_item_suggestion, parent, false);
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
        String distance = com.chatapp.util.Utils.getDistanceinString(Double.parseDouble(suggetionList.get(position).getLat()), Double.parseDouble(suggetionList.get(position).getLong()), DatingApp.getsInstance().getCurrentLocation().getLatitude(), DatingApp.getsInstance().getCurrentLocation().getLongitude());
        if (!TextUtils.isEmpty(distance)) {
            holder.tvDistance.setText(distance);
        }
        if (TextUtils.isEmpty(suggetionList.get(position).getCurrentWork())) {
            holder.tvProfession.setText("N/A");
        } else {
            holder.tvProfession.setText(suggetionList.get(position).getCurrentWork());
        }
//        if (suggetionList.get(position).getPhotos().size() > 0)
//            Glide.with(context).load(suggetionList.get(position).getPhotos().get(0).getPhoto()).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
//                    .bitmapTransform(new RoundedCornersTransformation(context, context.getResources().getDimensionPixelSize(R.dimen._2sdp), 0))
//                    .into(holder.imgProfile);
        Glide.with(context).load("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg").placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
                .bitmapTransform(new RoundedCornersTransformation(context, context.getResources().getDimensionPixelSize(R.dimen._2sdp), 0))
                .into(holder.imgProfile);
        return convertView;
    }
}
