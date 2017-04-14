package com.chatapp.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chatapp.R;


public class CustomFontHelper {
    /**
     * Sets a font on a textView based on the custom com.my.package:font
     * attribute If the custom font attribute isn't found in the attributes
     * nothing happens
     *
     * @param textview
     * @param context
     * @param attrs
     */
    public static void setCustomFont(TextView textview, Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
        String font = a.getString(R.styleable.CustomFont_font_name);
        setCustomFont(textview, font, context);
        a.recycle();
    }

    /**
     * Sets a font on a textView
     *
     * @param textview
     * @param font
     * @param context
     */
    public static void setCustomFont(TextView textview, String font, Context context) {
        if (font == null) {
            return;
        }
        final Typeface tf = FontCache.get(font, context);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }

}
