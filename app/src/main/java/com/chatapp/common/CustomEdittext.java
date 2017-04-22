package com.chatapp.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import com.chatapp.R;

/**
 * Created by indianic on 22/04/17.
 */

public class CustomEdittext extends EditText {
    public CustomEdittext(Context context) {
        super(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, attrs);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context, attrs);
    }
    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);
        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);
        setTypeface(FontCache.get(fontName, context));

        attributeArray.recycle();
    }
}
