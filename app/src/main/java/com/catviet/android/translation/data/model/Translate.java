package com.catviet.android.translation.data.model;

/**
 * Created by ducvietho on 26/04/2018.
 */

public class Translate {
    private String mText;
    private int mImage;
    private String mCode;
    private String mLanguage;
    private int mType;

    public Translate() {
    }

    public Translate(String text, int image, String code, String language, int type) {
        mText = text;
        mImage = image;
        mCode = code;
        mLanguage = language;
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }
}
