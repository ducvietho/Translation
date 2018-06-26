package com.catviet.android.translation.screen.text;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.data.resource.local.TranslateDataHelper;
import com.catviet.android.translation.service.InternetService;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.OnClickSpeak;
import com.catviet.android.translation.utils.TextToSpeechManager;
import com.catviet.android.translation.utils.TranslateAPI;
import com.catviet.android.translation.utils.customview.EditTextLight;
import com.catviet.android.translation.utils.customview.TextViewLight;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.catviet.android.translation.utils.dialogs.DialogLanguage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment implements View.OnClickListener,OnClickSpeak<Translate>{

    @BindView(R.id.tv_language_detect)
    TextViewRegular languageDetect;
    @BindView(R.id.tv_language_translate)
    TextViewRegular languageTranslate;
    @BindView(R.id.img_change)
    ImageView imgChange;
    @BindView(R.id.img_choose_detect)
    ImageView imgChooseDetect;
    @BindView(R.id.img_choose_translate)
    ImageView imgChooseTranslate;
    @BindView(R.id.ed_type)
    EditTextLight edType;
    @BindView(R.id.bt_send)
    ImageView imgSend;
    @BindView(R.id.rec_translate)
    RecyclerView mRecyclerTranslate;
    @BindView(R.id.layout_detect)
    LinearLayout mLayoutDetect;
    @BindView(R.id.layout_translate)
    LinearLayout mLayoutTranslate;

    TranslateDataHelper mDataHelper;
    List<Translate> mTranslates = new ArrayList<>();
    TranslateAdapter mTranslateAdapter;
    TextToSpeechManager mToSpeechManager = null;
    View v;
    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_text, container, false);
        ButterKnife.bind(this,v);
        mDataHelper = new TranslateDataHelper(v.getContext());
        imgChange.setOnClickListener(this);
        imgChooseDetect.setOnClickListener(this);
        imgChooseTranslate.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        mLayoutDetect.setOnClickListener(this);
        mLayoutTranslate.setOnClickListener(this);
        mTranslates = mDataHelper.getDataText();

        final LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        manager.setStackFromEnd(true);
        mRecyclerTranslate.setLayoutManager(manager);
        mTranslateAdapter = new TranslateAdapter(mTranslates, TextFragment.this,2);
        mRecyclerTranslate.setAdapter(mTranslateAdapter);
        mRecyclerTranslate.smoothScrollToPosition(mTranslates.size());
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(Constants.PRE_DETECT, MODE_PRIVATE);
        SharedPreferences sharedPreferencesTranslate = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE,
                MODE_PRIVATE);
        String extra = sharedPreferences.getString(Constants.EXTRA_DETECT, "");
        String extraTranslate = sharedPreferencesTranslate.getString(Constants.EXTRA_TRANSLATE, "");
        if (extra.equals("")) {
            SharedPreferences.Editor editorDetect = sharedPreferences.edit();
            editorDetect.putString(Constants.EXTRA_DETECT, new Gson().toJson(new Language("English", "en", R.drawable
                    .ic_uk)));
            editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, 1);
            editorDetect.commit();
        } else {
            Language lan = new Gson().fromJson(extra, Language.class);
            languageDetect.setText(lan.getName().toString());
        }
        if (extraTranslate.equals("")) {
            SharedPreferences.Editor editorTranslate = sharedPreferencesTranslate.edit();
            editorTranslate.putString(Constants.EXTRA_TRANSLATE, new Gson().toJson(new Language("Vietnamese", "vi", R
                    .drawable.ic_vietnam)));
            editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, 0);
            editorTranslate.commit();
        }else {
            Language lan = new Gson().fromJson(extraTranslate, Language.class);
            languageTranslate.setText(lan.getName());
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_change:
                SharedPreferences preferencesDetect = v.getContext().getSharedPreferences(Constants.PRE_DETECT,
                        MODE_PRIVATE);
                SharedPreferences preferencesTranslate = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE,
                        MODE_PRIVATE);
                String detect = preferencesDetect.getString(Constants.EXTRA_DETECT, null);
                String translate = preferencesTranslate.getString(Constants.EXTRA_TRANSLATE, null);
                Language lanDetect = new Gson().fromJson(detect, Language.class);
                Language lanTranslate = new Gson().fromJson(translate, Language.class);
                int posDetect = preferencesDetect.getInt(Constants.EXTRA_DETECT_POSITION, -1);
                int posTranslate = preferencesTranslate.getInt(Constants.EXTRA_TRANSLATE_POSITION, -1);
                SharedPreferences.Editor editorDetect = preferencesDetect.edit();
                editorDetect.putString(Constants.EXTRA_DETECT, new Gson().toJson(lanTranslate));
                editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, posTranslate);
                editorDetect.commit();
                SharedPreferences.Editor editorTranslate = preferencesTranslate.edit();
                editorTranslate.putString(Constants.EXTRA_TRANSLATE, new Gson().toJson(lanDetect));
                editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, posDetect);
                editorTranslate.commit();
                languageDetect.setText(lanTranslate.getName());
                languageTranslate.setText(lanDetect.getName());
                break;
            case R.id.img_choose_detect:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate,0).showDialogText();
                break;
            case R.id.img_choose_translate:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate,0).showDialogText();
                break;
            case R.id.bt_send:
                translate();
                break;
            case R.id.layout_detect:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate,0).showDialogText();
                break;
            case R.id.layout_translate:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate,0).showDialogText();
                break;
            default:
                break;
        }
    }

    @Override
    public void speakText(Translate translate) {
        mToSpeechManager = new TextToSpeechManager();
        mToSpeechManager.init(v.getContext(), translate.getCode(), translate.getText());
    }
    private void translate() {
        InputMethodManager imm = (InputMethodManager)((Activity)v.getContext()).getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        int pos = mTranslates.size();

        if (!edType.getText().toString().matches("")) {
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(Constants.PRE_TOTRANSLATE,
                    MODE_PRIVATE);
            String lang = sharedPreferences.getString(Constants.EXTRA_TOTRANSLATE,null);
            SharedPreferences preferencesDetectSend = v.getContext().getSharedPreferences(Constants.PRE_DETECT,
                    MODE_PRIVATE);
            SharedPreferences preferencesTranslateSend = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE,
                    MODE_PRIVATE);
            String detectSend = preferencesDetectSend.getString(Constants.EXTRA_DETECT, null);
            String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE, null);
            Language lanDetectSend = new Gson().fromJson(detectSend, Language.class);
            Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);
            String infor = lanDetectSend.getName()+" to "+lanTranslateSend.getName();

            if(lang==null){
                Log.i("To translate","no language");
                SharedPreferences sharedPreferences1 = v.getContext().getSharedPreferences(Constants.PRE_TOTRANSLATE,
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString(Constants.EXTRA_TOTRANSLATE,lanDetectSend.getName()+" to "+lanTranslateSend.getName());
                editor.commit();
                Translate translate = new Translate("",0,"","",3,lanDetectSend.getName()+" to "+lanTranslateSend
                        .getName());
                mTranslates.add(translate);
                mDataHelper.insert(translate,0);
                Translate translate1 = new Translate(edType.getText().toString(), lanDetectSend.getImage(),
                        lanDetectSend.getCode(), lanDetectSend.getName(), 0,
                        lanDetectSend.getName()+" to "+lanTranslateSend.getName());
                mTranslates.add(translate1);
                mDataHelper.insert(translate1,0);
            }else {
                Log.i("To translate",lang);
                if(lang.equals(infor)){
                    Translate translate = new Translate(edType.getText().toString(), lanDetectSend.getImage(),
                            lanDetectSend.getCode(), lanDetectSend.getName(), 0,
                            lanDetectSend.getName()+" to "+lanTranslateSend.getName());
                    mTranslates.add(translate);
                    mDataHelper.insert(translate,0);
                }else {
                    SharedPreferences sharedPreferencesText = v.getContext().getSharedPreferences(Constants.PRE_TOTRANSLATE,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferencesText.edit();
                    editor.putString(Constants.EXTRA_TOTRANSLATE,lanDetectSend.getName()+" to "+lanTranslateSend.getName());
                    editor.commit();
                    Translate translate = new Translate("",0,"","",3,lanDetectSend.getName()+" to "+lanTranslateSend
                            .getName());
                    mTranslates.add(translate);
                    mDataHelper.insert(translate,0);
                    Translate translate1 = new Translate(edType.getText().toString(), lanDetectSend.getImage(),
                            lanDetectSend.getCode(), lanDetectSend.getName(), 0,
                            lanDetectSend.getName()+" to "+lanTranslateSend.getName());
                    mTranslates.add(translate1);
                    mDataHelper.insert(translate1,0);
                }
            }

            mTranslateAdapter.notifyDataSetChanged();
            mRecyclerTranslate.smoothScrollToPosition(mTranslates.size());
            TranslateAPI translateAPI = new TranslateAPI(v.getContext(), lanDetectSend.getCode(), lanTranslateSend.getCode(),
                    mTranslates, mTranslateAdapter,mRecyclerTranslate, 0);
            translateAPI.execute(edType.getText().toString());
            edType.setText("");
        } else {
            Toast.makeText(v.getContext(), "Please enter text", Toast.LENGTH_LONG).show();
        }

    }
}
