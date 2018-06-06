package com.catviet.android.translation.utils.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.FontCache;

/**
 * Created by ducvietho on 24/04/2018.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewLight extends AppCompatTextView {

    public TextViewLight(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont;
        customFont = FontCache.getTypeface(Constants.SF_UI_LIGHT, context);
        setTypeface(customFont);
    }
}
