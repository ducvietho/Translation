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
    private String mToTranslate;
    private String mTextTranslate;
    private int mImageTranslate;
    private String mCodeTranslate;
    private String mLanguageTranslate;
    public Translate() {
    }

    public Translate(String text, int image, String code, String language, int type, String toTranslate) {
        mText = text;
        mImage = image;
        mCode = code;
        mLanguage = language;
        mType = type;
        mToTranslate = toTranslate;
    }

    public Translate(String text, int image, String code, String language, int type, String toTranslate,
                     String textTranslate, int imageTranslate, String codeTranslate, String languageTranslate) {
        mText = text;
        mImage = image;
        mCode = code;
        mLanguage = language;
        mType = type;
        mToTranslate = toTranslate;
        mTextTranslate = textTranslate;
        mImageTranslate = imageTranslate;
        mCodeTranslate = codeTranslate;
        mLanguageTranslate = languageTranslate;
    }

    public String getTextTranslate() {
        return mTextTranslate;
    }

    public void setTextTranslate(String textTranslate) {
        mTextTranslate = textTranslate;
    }

    public int getImageTranslate() {
        return mImageTranslate;
    }

    public void setImageTranslate(int imageTranslate) {
        mImageTranslate = imageTranslate;
    }

    public String getCodeTranslate() {
        return mCodeTranslate;
    }

    public void setCodeTranslate(String codeTranslate) {
        mCodeTranslate = codeTranslate;
    }

    public String getLanguageTranslate() {
        return mLanguageTranslate;
    }

    public void setLanguageTranslate(String languageTranslate) {
        mLanguageTranslate = languageTranslate;
    }

    public String getToTranslate() {
        return mToTranslate;
    }

    public void setToTranslate(String toTranslate) {
        mToTranslate = toTranslate;
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
