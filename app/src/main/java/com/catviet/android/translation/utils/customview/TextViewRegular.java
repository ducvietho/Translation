package com.catviet.android.translation.utils.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.FontCache;

/**
 * Created by ducvietho on 24/04/2018.
 */

public class TextViewRegular extends AppCompatTextView {
    public TextViewRegular(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont;
        customFont = FontCache.getTypeface(Constants.SF_UI_REGULAR, context);
        setTypeface(customFont);
    }
}
