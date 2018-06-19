package com.catviet.android.translation.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by ducvietho on 26/4/2018.
 */

public class TextToSpeechManager {

    private TextToSpeech mTTS = null;
    private String mCode;
    private int result;
    private String mText;

    public void init(Context context, String code,String text) {
        mTTS = new TextToSpeech(context, onInitListener);
        mText = text;
        mCode = code;
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {

        @Override
        public void onInit(int status) {

            if (status == TextToSpeech.SUCCESS) {
                //set Language
                if(!mTTS.isSpeaking()){
                    Locale aLocale = new Locale(mCode);
                    Locale.setDefault(aLocale);
                    result = mTTS.setLanguage(Locale.getDefault());
                    mTTS.speak(mText, TextToSpeech.QUEUE_FLUSH, null);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    }
                }

            } else {
                Log.e("error", "Initialization Failed!");
            }
        }
    };

    public void shutDown() {
        mTTS.stop();
        mTTS.shutdown();
    }


}
