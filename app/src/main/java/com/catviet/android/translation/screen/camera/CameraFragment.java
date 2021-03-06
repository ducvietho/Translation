package com.catviet.android.translation.screen.camera;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.data.resource.local.TranslateDataHelper;
import com.catviet.android.translation.screen.text.TranslateAdapter;
import com.catviet.android.translation.service.InternetService;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.OnClickSpeak;
import com.catviet.android.translation.utils.TextToSpeechManager;
import com.catviet.android.translation.utils.TranslateAPI;
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
public class CameraFragment extends Fragment implements View.OnClickListener, OnClickSpeak<Translate> {
    private static final int REQUEST_CODE_CAMERA = 1002;

    private static final String EXTRA_TEXT = "text";
    private final String TAG = "take photo";
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
    @BindView(R.id.rec_translate)
    RecyclerView mRecyclerTranslate;
    @BindView(R.id.bt_camera)
    FloatingActionButton btCamera;
    @BindView(R.id.layout_detect)
    LinearLayout mLayoutDetect;
    @BindView(R.id.layout_translate)
    LinearLayout mLayoutTranslate;
    @BindView(R.id.layout_empty)
    LinearLayout emptyLayout;
    View v;
    public static TextViewLight tvInternet;
    List<Translate> mList = new ArrayList<>();
    TranslateAdapter mAdapter;
    private TranslateDataHelper mDataHelper;


    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment newInstance(String text) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this, v);
        tvInternet = (TextViewLight) v.findViewById(R.id.tv_internet_connection);
        ConnectivityManager cm = (ConnectivityManager) v.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            tvInternet.setVisibility(View.VISIBLE);
        } else {
            tvInternet.setVisibility(View.GONE);

        }
        btCamera.setOnClickListener(this);
        imgChange.setOnClickListener(this);
        imgChooseDetect.setOnClickListener(this);
        imgChooseTranslate.setOnClickListener(this);
        mLayoutDetect.setOnClickListener(this);
        mLayoutTranslate.setOnClickListener(this);
        mDataHelper = new TranslateDataHelper(v.getContext());
        mList = mDataHelper.getDataCamera();
        if(mList.size()>0){
            emptyLayout.setVisibility(View.GONE);
        }
        mAdapter = new TranslateAdapter(mList, CameraFragment.this, 3);
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        manager.setStackFromEnd(true);
        mRecyclerTranslate.setLayoutManager(manager);
        mRecyclerTranslate.setAdapter(mAdapter);
        mRecyclerTranslate.smoothScrollToPosition(mList.size());
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
        SharedPreferences sharedPreferencesTranslate = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
        String extra = sharedPreferences.getString(Constants.EXTRA_DETECT_CAMERA, null);
        String extraTranslate = sharedPreferencesTranslate.getString(Constants.EXTRA_TRANSLATE_CAMERA, null);
        if (extra == null) {
            SharedPreferences.Editor editorDetect = sharedPreferences.edit();
            editorDetect.putString(Constants.EXTRA_DETECT_CAMERA, new Gson().toJson(new Language("English", "en", R.drawable.ic_uk)));
            editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, 1);
            editorDetect.commit();
        }
        if (extraTranslate == null) {
            SharedPreferences.Editor editorTranslate = sharedPreferencesTranslate.edit();
            editorTranslate.putString(Constants.EXTRA_TRANSLATE_CAMERA, new Gson().toJson(new Language("Vietnamese", "vi", R.drawable.ic_vietnam)));
            editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, 0);
            editorTranslate.commit();
        }
        if (extra != null) {
            Language lan = new Gson().fromJson(extra, Language.class);
            languageDetect.setText(lan.getName());

        }
        if (extraTranslate != null) {
            Language lan = new Gson().fromJson(extraTranslate, Language.class);
            languageTranslate.setText(lan.getName());
        }
        String string = getArguments().getString(EXTRA_TEXT, null);
        if (string != null) {
            emptyLayout.setVisibility(View.GONE);
            SharedPreferences preferencesDetectSend = v.getContext().getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
            SharedPreferences preferencesTranslateSend = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
            SharedPreferences preferencesLan = v.getContext().getSharedPreferences(Constants.PRE_TOTRANSLATE_CAMERA, MODE_PRIVATE);
            String lang = preferencesLan.getString(Constants.EXTRA_TOTRANSLATE, null);
            String detectSend = preferencesDetectSend.getString(Constants.EXTRA_DETECT_CAMERA, null);
            String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE_CAMERA, null);
            Language lanDetectSend = new Gson().fromJson(detectSend, Language.class);
            Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);
            String info = lanDetectSend.getName() + " to " + lanTranslateSend.getName();
            if (lang == null) {
                Translate translate = new Translate("", 0, "", "", 3, lanDetectSend.getName() + " to " + lanTranslateSend.getName());
                mList.add(translate);
                mDataHelper.insert(translate, 1);
                Translate translate1 = new Translate(string, lanDetectSend.getImage(), lanDetectSend.getCode(), lanDetectSend.getName(), 0, lanDetectSend.getName() + " to " + lanTranslateSend.getName());
                //mDataHelper.insert(translate1, 1);
                mList.add(translate1);
                SharedPreferences.Editor editor = preferencesLan.edit();
                editor.putString(Constants.EXTRA_TOTRANSLATE, lanDetectSend.getName() + " to " + lanTranslateSend.getName());
                editor.commit();

            } else {
                if (info.equals(lang)) {
                    Translate translate = new Translate(string, lanDetectSend.getImage(), lanDetectSend.getCode(), lanDetectSend.getName(), 0, lanDetectSend.getName() + " to " + lanTranslateSend.getName());
                    // mDataHelper.insert(translate, 1);
                    mList.add(translate);
                } else {
                    Translate translate = new Translate("", 0, "", "", 3, lanDetectSend.getName() + " to " + lanTranslateSend.getName());
                    mList.add(translate);
                    mDataHelper.insert(translate, 1);
                    Translate translate1 = new Translate(string, lanDetectSend.getImage(), lanDetectSend.getCode(), lanDetectSend.getName(), 0, lanDetectSend.getName() + " to " + lanTranslateSend.getName());
                    // mDataHelper.insert(translate1, 1);
                    mList.add(translate1);
                    SharedPreferences.Editor editor = preferencesLan.edit();
                    editor.putString(Constants.EXTRA_TOTRANSLATE, lanDetectSend.getName() + " to " + lanTranslateSend.getName());
                    editor.commit();
                }
            }
            mAdapter.notifyDataSetChanged();
            mRecyclerTranslate.smoothScrollToPosition(mList.size());
            TranslateAPI translateAPI = new TranslateAPI(v.getContext(), lanDetectSend, lanTranslateSend, mList, mAdapter, mRecyclerTranslate, 1, mList.size() - 1, string);
            translateAPI.execute(string);
        }
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_choose_detect:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate, 1).showDialogCamera();
                break;
            case R.id.img_choose_translate:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate, 1).showDialogCamera();
                break;

            case R.id.img_change:
                SharedPreferences preferencesDetect = v.getContext().getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
                SharedPreferences preferencesTranslate = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
                String detect = preferencesDetect.getString(Constants.EXTRA_DETECT_CAMERA, null);
                String translate = preferencesTranslate.getString(Constants.EXTRA_TRANSLATE_CAMERA, null);
                Language lanDetect = new Gson().fromJson(detect, Language.class);
                Language lanTranslate = new Gson().fromJson(translate, Language.class);
                int posDetect = preferencesDetect.getInt(Constants.EXTRA_DETECT_POSITION, -1);
                int posTranslate = preferencesTranslate.getInt(Constants.EXTRA_TRANSLATE_POSITION, -1);
                SharedPreferences.Editor editorDetect = preferencesDetect.edit();
                editorDetect.putString(Constants.EXTRA_DETECT_CAMERA, new Gson().toJson(lanTranslate));
                editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, posTranslate);
                editorDetect.commit();
                SharedPreferences.Editor editorTranslate = preferencesTranslate.edit();
                editorTranslate.putString(Constants.EXTRA_TRANSLATE_CAMERA, new Gson().toJson(lanDetect));
                editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, posDetect);
                editorTranslate.commit();
                languageDetect.setText(lanTranslate.getName());
                languageTranslate.setText(lanDetect.getName());
                break;
            case R.id.bt_camera:
                startActivity(new CameraActivity().getIntent(v.getContext()));
                getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                break;
            case R.id.layout_detect:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate, 1).showDialogCamera();
                break;
            case R.id.layout_translate:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate, 1).showDialogCamera();
                break;
            default:
                break;
        }
    }


    @Override
    public void speakText(Translate translate) {
        TextToSpeechManager mToSpeechManager = new TextToSpeechManager();
        mToSpeechManager.init(v.getContext(), translate.getCode(), translate.getText());
    }

}
