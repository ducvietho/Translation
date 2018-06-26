package com.catviet.android.translation.screen.voice;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.data.resource.local.TranslateDataHelper;
import com.catviet.android.translation.screen.text.TranslateAdapter;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.OnClickSpeak;
import com.catviet.android.translation.utils.TextToSpeechManager;
import com.catviet.android.translation.utils.TranslateAPI;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.catviet.android.translation.utils.dialogs.DialogLanguage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoiceActivity extends AppCompatActivity implements OnClickSpeak<Translate>,View.OnClickListener {
    public static final int REQ_CODE_SPEECH_INPUT_DETECT = 100;
    public static final int REQ_CODE_SPEECH_INPUT_TRANSLATE = 101;
    @BindView(R.id.rec_voice)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_voice_detect)
    FloatingActionButton mVoiceDetect;
    @BindView(R.id.bt_voice_translate)
    FloatingActionButton mVoiceTranslate;
    @BindView(R.id.img_back)
    ImageView imgBack;
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
    List<Translate> mTranslates = new ArrayList<>();
    TranslateAdapter mAdapter;
    TranslateAPI mTranslateAPI;
    TextToSpeechManager mToSpeechManager = null;
    TranslateDataHelper mDataHelper;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, VoiceActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);
        mDataHelper = new TranslateDataHelper(VoiceActivity.this);
        mTranslates = mDataHelper.getDataVoice();
        GridLayoutManager manager = new GridLayoutManager(VoiceActivity.this, 1);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new TranslateAdapter(mTranslates, this,1);
        mRecyclerView.setAdapter(mAdapter);
        mVoiceDetect.setOnClickListener(this);
        mVoiceTranslate.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgChange.setOnClickListener(this);
        imgChooseDetect.setOnClickListener(this);
        imgChooseTranslate.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PRE_DETECT_VOICE, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesTranslate = getSharedPreferences(Constants.PRE_TRANSLATE_VOICE, Context
                .MODE_PRIVATE);
        String extra = sharedPreferences.getString(Constants.EXTRA_DETECT_VOICE, "");
        String extraTranslate = sharedPreferencesTranslate.getString(Constants.EXTRA_TRANSLATE, "");
        if (extra.equals("")) {
            SharedPreferences.Editor editorDetect = sharedPreferences.edit();
            editorDetect.putString(Constants.EXTRA_DETECT, new Gson().toJson(new Language("Auto Detect", "auto", R.drawable.ic_vietnam)));
            editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, 0);
            editorDetect.commit();
        } else {
            Language lan = new Gson().fromJson(extra, Language.class);
            languageDetect.setText(lan.getName().toString());
        }
        if (extraTranslate.equals("")) {
            SharedPreferences.Editor editorTranslate = sharedPreferencesTranslate.edit();
            editorTranslate.putString(Constants.EXTRA_TRANSLATE, new Gson().toJson(new Language("Auto Detect", "auto", R.drawable.ic_vietnam)));
            editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, 0);
            editorTranslate.commit();
        }else {
            Language lan = new Gson().fromJson(extraTranslate, Language.class);
            languageTranslate.setText(lan.getName());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT_DETECT:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    SharedPreferences preferencesDetectSend = getSharedPreferences(Constants.PRE_DETECT,
                            MODE_PRIVATE);
                    String detectSend = preferencesDetectSend.getString(Constants.EXTRA_DETECT, null);
                    Language lanDetectSend = new Gson().fromJson(detectSend, Language.class);
                    SharedPreferences preferencesTranslateSend = getSharedPreferences(Constants.PRE_TRANSLATE,
                            MODE_PRIVATE);
                    String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE, null);
                    Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);
                    Translate translate = new Translate(result.get(0),lanDetectSend.getImage(),lanDetectSend.getCode(),
                            lanDetectSend.getName(),0,"");
                    mTranslates.add(translate);
                    mAdapter.notifyDataSetChanged();
                    mDataHelper.insert(translate,2);
                    mTranslateAPI = new TranslateAPI(VoiceActivity.this,lanDetectSend.getCode(),lanTranslateSend
                            .getCode(),mTranslates,mAdapter,mRecyclerView,2);
                    mTranslateAPI.execute(result.get(0));
                    Toast.makeText(VoiceActivity.this, result.get(0), Toast.LENGTH_LONG).show();
                }
                break;
            case REQ_CODE_SPEECH_INPUT_TRANSLATE:
                if (resultCode == RESULT_OK) {
                    SharedPreferences preferencesDetectSend = getSharedPreferences(Constants.PRE_DETECT,
                            MODE_PRIVATE);
                    String detectSend = preferencesDetectSend.getString(Constants.EXTRA_DETECT, null);
                    Language lanDetectSend = new Gson().fromJson(detectSend, Language.class);
                    SharedPreferences preferencesTranslateSend = getSharedPreferences(Constants.PRE_TRANSLATE,
                            MODE_PRIVATE);
                    String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE, null);
                    Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Translate translate = new Translate(result.get(0),lanTranslateSend.getImage(),
                            lanTranslateSend.getCode(), lanTranslateSend.getName(),1,"");
                    mTranslates.add(translate);
                    mAdapter.notifyDataSetChanged();
                    mDataHelper.insert(translate,2);
                    mTranslateAPI = new TranslateAPI(VoiceActivity.this,lanTranslateSend.getCode(),
                            lanDetectSend.getCode(),mTranslates,mAdapter,mRecyclerView,2);
                    mTranslateAPI.execute(result.get(0));
                    Toast.makeText(VoiceActivity.this, result.get(0), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @Override
    public void speakText(Translate translate) {
        mToSpeechManager = new TextToSpeechManager();
        mToSpeechManager.init(VoiceActivity.this, translate.getCode(), translate.getText());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_voice_detect:
                promptSpeechInput(0);
                break;
            case R.id.bt_voice_translate:
                promptSpeechInput(1);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_change:
                SharedPreferences preferencesDetect = getSharedPreferences(Constants.PRE_DETECT, MODE_PRIVATE);
                SharedPreferences preferencesTranslate = getSharedPreferences(Constants.PRE_TRANSLATE, MODE_PRIVATE);
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
                new DialogLanguage(VoiceActivity.this, languageDetect, languageTranslate,0).showDialogVoice();
                break;
            case R.id.img_choose_translate:
                new DialogLanguage(VoiceActivity.this, languageDetect, languageTranslate,0).showDialogVoice();
                break;
        }
    }
    private void promptSpeechInput(int type) {
        switch (type) {
            case 0:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                SharedPreferences sharedPreferencesDetect = getSharedPreferences(Constants.PRE_DETECT_VOICE, MODE_PRIVATE);
                String detect = sharedPreferencesDetect.getString(Constants.EXTRA_DETECT_VOICE, "");
                Language languageDetect = new Gson().fromJson(detect, Language.class);
                Locale aLocale = new Locale(languageDetect.getCode());
                Locale.setDefault(aLocale);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT_DETECT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(VoiceActivity.this, getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                Intent intentTranslate = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intentTranslate.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                SharedPreferences sharedPreferencesTranslate = getSharedPreferences(Constants.PRE_TRANSLATE_VOICE,
                        MODE_PRIVATE);
                String translate = sharedPreferencesTranslate.getString(Constants.EXTRA_TRANSLATE_VOICE, "");
                Language languageTranslate = new Gson().fromJson(translate, Language.class);
                Locale aLocaleTranslate = new Locale(languageTranslate.getCode());
                Locale.setDefault(aLocaleTranslate);
                intentTranslate.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intentTranslate.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
                try {
                    startActivityForResult(intentTranslate, REQ_CODE_SPEECH_INPUT_TRANSLATE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(VoiceActivity.this, getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
}
