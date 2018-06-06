package com.catviet.android.translation.data.model;

/**
 * Created by ducvietho on 26/04/2018.
 */

public class Language {
    private String mName;
    private String mCode;
    private int mImage;

    public Language(String name, String code, int image) {
        mName = name;
        mCode = code;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }
}
