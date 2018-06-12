package com.catviet.android.translation.screen.voice;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.catviet.android.translation.utils.speech_api.SpeechAPI;
import com.catviet.android.translation.utils.speech_api.VoiceRecorder;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class VoiceFragment extends Fragment implements OnClickSpeak<Translate>, View.OnClickListener {
    public static final int REQ_CODE_SPEECH_INPUT_DETECT = 100;
    public static final int REQ_CODE_SPEECH_INPUT_TRANSLATE = 101;
    private static final int RECORD_REQUEST_CODE = 101;
    @BindView(R.id.rec_voice)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_voice_detect)
    RelativeLayout mVoiceDetect;
    @BindView(R.id.bt_voice_translate)
    RelativeLayout mVoiceTranslate;
    @BindView(R.id.img_voice_detect)
    CircleImageView imgVOiceDetect;
    @BindView(R.id.img_voice_translate)
    CircleImageView imgVoiceTranslate;
    @BindView(R.id.img_country_detect)
    CircleImageView imgCountryDetect;
    @BindView(R.id.img_country_translate)
    CircleImageView imgCountryTranslate;
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
    View v;
    TranslateDataHelper mDataHelper;
    private SpeechAPI speechAPI;
    private VoiceRecorder mVoiceRecorder;
    SpeechAPI.Listener mSpeechServiceListener;
    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {

        @Override
        public void onVoiceStart() {
            if (speechAPI != null) {
                speechAPI.startRecognizing(mVoiceRecorder.getSampleRate());
            }
        }

        @Override
        public void onVoice(byte[] data, int size) {
            if (speechAPI != null) {
                speechAPI.recognize(data, size);
            }
        }

        @Override
        public void onVoiceEnd() {
            if (speechAPI != null) {
                speechAPI.finishRecognizing();
            }
        }

    };
    public VoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_voice, container, false);
        ButterKnife.bind(this, v);
        mDataHelper = new TranslateDataHelper(v.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(manager);
        mTranslates = mDataHelper.getDataVoice();
        mAdapter = new TranslateAdapter(mTranslates, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.smoothScrollToPosition(mTranslates.size());
        mVoiceDetect.setOnClickListener(this);
        mVoiceTranslate.setOnClickListener(this);
        imgChange.setOnClickListener(this);
        imgChooseDetect.setOnClickListener(this);
        imgChooseTranslate.setOnClickListener(this);
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(Constants.PRE_DETECT_VOICE, MODE_PRIVATE);
        SharedPreferences sharedPreferencesTranslate = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE_VOICE, MODE_PRIVATE);
        String extra = sharedPreferences.getString(Constants.EXTRA_DETECT_VOICE, "");
        String extraTranslate = sharedPreferencesTranslate.getString(Constants.EXTRA_TRANSLATE_VOICE, "");
        if (extra.equals("")) {
            SharedPreferences.Editor editorDetect = sharedPreferences.edit();
            editorDetect.putString(Constants.EXTRA_DETECT_VOICE, new Gson().toJson(new Language("English", "en-US",
                    R.drawable.ic_uk)));
            editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, 1);
            editorDetect.commit();
        } else {
            Language lan = new Gson().fromJson(extra, Language.class);
            languageDetect.setText(lan.getName().toString());
            imgCountryDetect.setImageResource(lan.getImage());
        }
        if (extraTranslate.equals("")) {
            SharedPreferences.Editor editorTranslate = sharedPreferencesTranslate.edit();
            editorTranslate.putString(Constants.EXTRA_TRANSLATE_VOICE, new Gson().toJson(new Language("Vietnamese", "vi-VN",
                    R.drawable.ic_vietnam)));
            editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, 0);
            editorTranslate.commit();
        } else {
            Language lan = new Gson().fromJson(extraTranslate, Language.class);
            languageTranslate.setText(lan.getName());
            imgCountryTranslate.setImageResource(lan.getImage());
        }
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT_DETECT:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    SharedPreferences preferencesDetectSend = v.getContext().getSharedPreferences(Constants.PRE_DETECT_VOICE, MODE_PRIVATE);
                    String detectSend = preferencesDetectSend.getString(Constants.EXTRA_DETECT_VOICE, null);

                    Language lanDetectSend = new Gson().fromJson(detectSend, Language.class);
                    SharedPreferences preferencesTranslateSend = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE_VOICE, MODE_PRIVATE);
                    String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE_VOICE, null);
                    Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);
                    Translate translate = new Translate(result.get(0), lanDetectSend.getImage(), lanDetectSend.getCode(), lanDetectSend.getName(), 0);
                    mTranslates.add(translate);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(mTranslates.size());
                    mDataHelper.insert(translate, 2);
                    mTranslateAPI = new TranslateAPI(v.getContext(), lanDetectSend.getCode(), lanTranslateSend.getCode(), mTranslates, mAdapter, mRecyclerView, 2);
                    mTranslateAPI.execute(result.get(0));

                }
                break;
            case REQ_CODE_SPEECH_INPUT_TRANSLATE:
                if (resultCode == RESULT_OK) {
                    SharedPreferences preferencesDetectSend = v.getContext().getSharedPreferences(Constants.PRE_DETECT_VOICE, MODE_PRIVATE);
                    String detectSend = preferencesDetectSend.getString(Constants.EXTRA_DETECT_VOICE, null);
                    Language lanDetectSend = new Gson().fromJson(detectSend, Language.class);
                    SharedPreferences preferencesTranslateSend = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE_VOICE, MODE_PRIVATE);
                    String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE_VOICE, null);
                    Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Translate translate = new Translate(result.get(0), lanTranslateSend.getImage(), lanTranslateSend.getCode(), lanTranslateSend.getName(), 0);
                    mTranslates.add(translate);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(mTranslates.size());
                    mDataHelper.insert(translate, 2);
                    mTranslateAPI = new TranslateAPI(v.getContext(), lanTranslateSend.getCode(), lanDetectSend.getCode(), mTranslates, mAdapter, mRecyclerView, 2);
                    mTranslateAPI.execute(result.get(0));

                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == RECORD_REQUEST_CODE) {
            if (grantResults.length == 0 && grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[0] == PackageManager.PERMISSION_DENIED) {

            } else {

            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_voice_detect:
                if(imgVOiceDetect.getVisibility()==View.GONE){
                    promptSpeechInput(0);
                }

                break;
            case R.id.bt_voice_translate:
                if(imgVoiceTranslate.getVisibility()==View.GONE){
                    promptSpeechInput(1);
                }

                break;
            case R.id.img_change:
                SharedPreferences preferencesDetect = v.getContext().getSharedPreferences(Constants.PRE_DETECT_VOICE, MODE_PRIVATE);
                SharedPreferences preferencesTranslate = v.getContext().getSharedPreferences(Constants.PRE_TRANSLATE_VOICE, MODE_PRIVATE);
                String detect = preferencesDetect.getString(Constants.EXTRA_DETECT_VOICE, null);
                String translate = preferencesTranslate.getString(Constants.EXTRA_TRANSLATE_VOICE, null);
                Language lanDetect = new Gson().fromJson(detect, Language.class);
                Language lanTranslate = new Gson().fromJson(translate, Language.class);
                int posDetect = preferencesDetect.getInt(Constants.EXTRA_DETECT_POSITION, -1);
                int posTranslate = preferencesTranslate.getInt(Constants.EXTRA_TRANSLATE_POSITION, -1);
                SharedPreferences.Editor editorDetect = preferencesDetect.edit();
                editorDetect.putString(Constants.EXTRA_DETECT_VOICE, new Gson().toJson(lanTranslate));
                editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, posTranslate);
                editorDetect.commit();
                SharedPreferences.Editor editorTranslate = preferencesTranslate.edit();
                editorTranslate.putString(Constants.EXTRA_TRANSLATE_VOICE, new Gson().toJson(lanDetect));
                editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, posDetect);
                editorTranslate.commit();
                languageDetect.setText(lanTranslate.getName());
                languageTranslate.setText(lanDetect.getName());
                Picasso.with(v.getContext()).load(lanTranslate.getImage()).into(imgCountryDetect);
                Picasso.with(v.getContext()).load(lanDetect.getImage()).into(imgCountryTranslate);
                break;
            case R.id.img_choose_detect:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate, imgCountryDetect, imgCountryTranslate, 2).showDialogVoice();
                break;
            case R.id.img_choose_translate:
                new DialogLanguage(v.getContext(), languageDetect, languageTranslate, imgCountryDetect, imgCountryTranslate, 2).showDialogVoice();
                break;
        }
    }

    @Override
    public void speakText(Translate translate) {
        mToSpeechManager = new TextToSpeechManager();
        mToSpeechManager.init(v.getContext(), translate.getCode(), translate.getText());
    }

    private void promptSpeechInput(int type) {

        SharedPreferences sharedPreferencesDetect = v.getContext().getSharedPreferences(Constants.PRE_DETECT_VOICE,
                MODE_PRIVATE);
        String detect = sharedPreferencesDetect.getString(Constants.EXTRA_DETECT_VOICE, "");
        final Language languageDetect = new Gson().fromJson(detect, Language.class);
        SharedPreferences sharedPreferencesTranslate = v.getContext().getSharedPreferences(Constants
                        .PRE_TRANSLATE_VOICE,
                MODE_PRIVATE);
        String translate = sharedPreferencesTranslate.getString(Constants.EXTRA_TRANSLATE_VOICE, "");
        final Language languageTranslate = new Gson().fromJson(translate, Language.class);
        switch (type) {
            case 0:
                if (isGrantedPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    imgVOiceDetect.setVisibility(View.VISIBLE);
                    final int postision = mTranslates.size();
                    Translate translate1 = new Translate("",languageDetect.getImage(),languageDetect
                            .getCode(), languageDetect.getName(),2);
                    mTranslates.add(postision,translate1);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(mTranslates.size());
                    speechAPI = new SpeechAPI(v.getContext(),languageDetect.getCode());
                    startVoiceRecorder();
                    mSpeechServiceListener = new SpeechAPI.Listener() {
                        @Override
                        public void onSpeechRecognized(final String text, final boolean isFinal) {
                            if (isFinal) {
                                mVoiceRecorder.dismiss();
                            }

                            ((Activity)v.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    if (isFinal) {
                                        imgVOiceDetect.setVisibility(View.GONE);
                                        Translate translate = new Translate(text,languageDetect.getImage(),languageDetect
                                                .getCode(), languageDetect.getName(),0);
                                        mTranslates.set(postision,translate);
                                        mAdapter.notifyDataSetChanged();
                                        mRecyclerView.smoothScrollToPosition(mTranslates.size());

                                        mTranslateAPI = new TranslateAPI(v.getContext(),languageDetect.getCode(),
                                                languageTranslate.getCode(),mTranslates,mAdapter,mRecyclerView,2);

                                        mTranslateAPI.execute(text);
                                        mDataHelper.insert(translate,2);
                                        speechAPI.removeListener(mSpeechServiceListener);
                                        stopVoiceRecorder();

                                    }else {

                                        Translate translate = new Translate(text,languageDetect.getImage(),languageDetect
                                                .getCode(), languageDetect.getName(),2);
                                        mTranslates.set(postision,translate);
                                        mAdapter.notifyDataSetChanged();
                                        mRecyclerView.smoothScrollToPosition(mTranslates.size());
                                    }
                                }
                            });

                        }
                    };
                } else {
                    makeRequest(Manifest.permission.RECORD_AUDIO);
                }

                speechAPI.addListener(mSpeechServiceListener);

                break;
            case 1:
                if (isGrantedPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    imgVoiceTranslate.setVisibility(View.VISIBLE);
                    final int postision = mTranslates.size();
                    Translate translate1 = new Translate("",languageTranslate.getImage(),
                            languageTranslate.getCode(), languageTranslate.getName(),2);
                    mTranslates.add(postision,translate1);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(mTranslates.size());
                    speechAPI = new SpeechAPI(v.getContext(),languageTranslate.getCode());
                    startVoiceRecorder();
                    mSpeechServiceListener = new SpeechAPI.Listener() {
                        @Override
                        public void onSpeechRecognized(final String text, final boolean isFinal) {
                            if (isFinal) {
                                mVoiceRecorder.dismiss();
                            }

                            ((Activity)v.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    if (isFinal) {
                                        imgVoiceTranslate.setVisibility(View.GONE);
                                        Translate translate = new Translate(text,languageTranslate.getImage(),languageTranslate
                                                .getCode(), languageTranslate.getName(),0);
                                        mTranslates.set(postision,translate);
                                        mAdapter.notifyDataSetChanged();
                                        mRecyclerView.smoothScrollToPosition(mTranslates.size());

                                        mTranslateAPI = new TranslateAPI(v.getContext(),languageTranslate.getCode(),
                                                languageDetect.getCode(),mTranslates,mAdapter,mRecyclerView,2);
                                        mTranslateAPI.execute(text);
                                        mDataHelper.insert(translate,2);
                                        speechAPI.removeListener(mSpeechServiceListener);
                                        stopVoiceRecorder();
                                    }else{
                                        Translate translate = new Translate(text,languageTranslate.getImage(),
                                                languageTranslate.getCode(), languageTranslate.getName(),2);
                                        mTranslates.set(postision,translate);
                                       mAdapter.notifyDataSetChanged();
                                        mRecyclerView.smoothScrollToPosition(mTranslates.size());
                                    }
                                }
                            });

                        }
                    };
                } else {
                    makeRequest(Manifest.permission.RECORD_AUDIO);
                }

                speechAPI.addListener(mSpeechServiceListener);
                break;
            default:
                break;
        }

    }


    private int isGrantedPermission(String permission) {
        return ContextCompat.checkSelfPermission(v.getContext(), permission);
    }

    private void makeRequest(String permission) {
        ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{permission}, RECORD_REQUEST_CODE);
    }
    private void startVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();

    }

    private void stopVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
            mVoiceRecorder = null;
        }
    }

}
