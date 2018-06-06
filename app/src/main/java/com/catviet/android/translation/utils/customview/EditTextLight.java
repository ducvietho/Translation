package com.catviet.android.translation.utils.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.FontCache;

/**
 * Created by ducvietho on 26/04/2018.
 */

@SuppressLint("AppCompatCustomView")
public class EditTextLight extends EditText {
    public EditTextLight(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public EditTextLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public EditTextLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont;
        customFont = FontCache.getTypeface(Constants.SF_UI_LIGHT, context);
        setTypeface(customFont);
    }
}
