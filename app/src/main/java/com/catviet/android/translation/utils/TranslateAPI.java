package com.catviet.android.translation.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.data.resource.local.TranslateDataHelper;
import com.catviet.android.translation.screen.text.TranslateAdapter;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.catviet.android.translation.utils.Constants.USER_AGENT;


/**
 * Created by ducvietho on 4/27/2018.
 */

public class TranslateAPI extends AsyncTask<String, String, Void> {

    private String mDetectCode;
    private String mTranslateCode;
    private List<Translate> mList;
    private TranslateAdapter mAdapter;
    private Context mContext;
    private int mType;
    private RecyclerView mRecyclerView;

    public TranslateAPI(Context context, String detectCode, String translateCode, List<Translate> list,
                        TranslateAdapter adapter, RecyclerView recyclerView, int type) {
        mDetectCode = detectCode;
        mTranslateCode = translateCode;
        mList = list;
        mAdapter = adapter;
        mContext = context;
        mType = type;
        mRecyclerView = recyclerView;
    }
    public TranslateAPI(Context context, String detectCode, String translateCode, int type) {
        mDetectCode = detectCode;
        mTranslateCode = translateCode;
        mContext = context;
        mType = type;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(String... params) {

        Document document = null;
        try {
            String key = URLEncoder.encode(params[0], "UTF-8");
            String url = "http://translate.google.com/m?hl=vi&sl=" + mDetectCode + "&tl=" + mTranslateCode + "&ie=UTF-8&prev=_m&q=" + key;
            Log.i("url", url);
            Random random = new Random();
            int pos = random.nextInt(USER_AGENT.length);
            document = Jsoup.connect(url).userAgent(USER_AGENT[pos]).get();
            if (document != null) {
                Elements meanElement = document.select("div.t0");
                if (meanElement != null && meanElement.size() >= 0) {
                    for (int i = 0; i < meanElement.size(); i++) {
                        String mean = meanElement.get(i).text();
                        publishProgress(mean);
                        // return mean;
                    }
                } else {
                    Log.e("element mean", "null");
                }
            }
        } catch (IOException e) {
//                    if (document!= null) document.remove();
            Log.e("error", e.getMessage() + "");
        }
        return null;
    }


    @Override

    protected void onProgressUpdate(String... values) {
        switch (mType) {
            case 0:
                SharedPreferences preferencesTranslateSend = mContext.getSharedPreferences(Constants.PRE_TRANSLATE, MODE_PRIVATE);
                String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE, null);
                Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);

                Translate translate = new Translate(values[0], lanTranslateSend.getImage(), lanTranslateSend.getCode(), lanTranslateSend.getName(), 1);
                mList.add(translate);
                mAdapter.notifyDataSetChanged();
                new TranslateDataHelper(mContext).insert(translate, 0);
                mRecyclerView.smoothScrollToPosition(mList.size());
                break;
            case 1:
                SharedPreferences preferencesTranslate = mContext.getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
                String translateSendCamera = preferencesTranslate.getString(Constants.EXTRA_TRANSLATE_CAMERA, null);
                Language lanTranslate = new Gson().fromJson(translateSendCamera, Language.class);
                Translate translateCamera = new Translate(values[0], lanTranslate.getImage(), lanTranslate.getCode(), lanTranslate.getName(), 1);
                mList.add(translateCamera);
                mAdapter.notifyDataSetChanged();
                new TranslateDataHelper(mContext).insert(translateCamera, 1);
                mRecyclerView.smoothScrollToPosition(mList.size());
                break;
            case 2:
                SharedPreferences preferencesTranslateVoice = mContext.getSharedPreferences(Constants.PRE_TRANSLATE_VOICE,
                        MODE_PRIVATE);
                String inforVoice = preferencesTranslateVoice.getString(Constants.EXTRA_TRANSLATE_VOICE, null);
                Language lanTranslateVoice = new Gson().fromJson(inforVoice, Language.class);
                SharedPreferences preferencesDetectVoice = mContext.getSharedPreferences(Constants.PRE_DETECT_VOICE,
                        MODE_PRIVATE);
                String detectVoice = preferencesDetectVoice.getString(Constants.EXTRA_DETECT_VOICE, null);
                Language lanDetectVoice = new Gson().fromJson(detectVoice, Language.class);
                if (mDetectCode.equals(lanDetectVoice.getCode())) {
                    Translate translateVoice = new Translate(values[0], lanTranslateVoice.getImage(), lanTranslateVoice.getCode(), lanTranslateVoice.getName(), 1);
                    mList.add(translateVoice);
                    mAdapter.notifyDataSetChanged();
                    new TranslateDataHelper(mContext).insert(translateVoice, 2);
                } else {
                    Translate translateVoice = new Translate(values[0], lanDetectVoice.getImage(), lanDetectVoice.getCode(), lanDetectVoice.getName(), 1);
                    mList.add(translateVoice);
                    mAdapter.notifyDataSetChanged();
                    new TranslateDataHelper(mContext).insert(translateVoice, 2);
                }
                mRecyclerView.smoothScrollToPosition(mList.size());

            default:
                break;

        }


    }

    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
    }
}
