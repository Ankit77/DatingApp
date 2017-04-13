package com.chatapp.tutorial.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;


/**
 * OnBoardingAdapter: provide data for dialogFragment
 */
public class OnBoardingAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private TypedArray images;
    private String[] titles;
    private String[] messages;

    public OnBoardingAdapter(Context context, TypedArray images, String[] titles, String[] messages) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.images = images;
        this.titles = titles;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return images.length();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = layoutInflater.inflate(R.layout.row_walkthrough_dialog, null);

        if (images != null) {
            final ImageView imageView = (ImageView) view.findViewById(R.id.fragment_my_badges_ivBadgeImage);
            imageView.setBackgroundResource(images.getResourceId(position,R.drawable.walkthrough1));
        }

        if (titles != null) {
            final TextView tvTitle = (TextView) view.findViewById(R.id.tv1);
            tvTitle.setText(titles[position]);
        }

        if (messages != null) {
            final TextView tvMessages = (TextView) view.findViewById(R.id.tv2);
            tvMessages.setText(messages[position]);
        }

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView((View) obj);
    }

    @Override
    public boolean isViewFromObject(View container, Object obj) {
        return container == obj;
    }
}

