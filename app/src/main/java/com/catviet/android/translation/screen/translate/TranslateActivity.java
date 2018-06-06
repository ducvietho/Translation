package com.catviet.android.translation.screen.translate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.screen.camera.CameraActivity;
import com.catviet.android.translation.screen.text.TranslateAdapter;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.OnClickSpeak;
import com.catviet.android.translation.utils.TextToSpeechManager;
import com.catviet.android.translation.utils.TranslateAPI;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranslateActivity extends AppCompatActivity implements OnClickSpeak<Translate> , View.OnClickListener{
    @BindView(R.id.rec_translate)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_camera)
    FloatingActionButton btCamera;
    @BindView(R.id.bt_home)
    FloatingActionButton btHome;
    TranslateAdapter mAdapter;
    List<Translate> mTranslates = new ArrayList<>();
    TextToSpeechManager mToSpeechManager = null;


    public static Intent getIntent(Context context, String result) {
        Intent intent = new Intent(context, TranslateActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.EXTRA_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        ButterKnife.bind(this);

        btCamera.setOnClickListener(this);
        btHome.setOnClickListener(this);
        String result = getIntent().getStringExtra(Constants.EXTRA_RESULT);
        SharedPreferences preferencesDetectSend = getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
        SharedPreferences preferencesTranslateSend = getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
        String detectSend = preferencesDetectSend.getString(Constants.EXTRA_DETECT_CAMERA, null);
        String translateSend = preferencesTranslateSend.getString(Constants.EXTRA_TRANSLATE_CAMERA, null);
        Language lanDetectSend = new Gson().fromJson(detectSend, Language.class);
        Language lanTranslateSend = new Gson().fromJson(translateSend, Language.class);
        Translate translate = new Translate(result, lanDetectSend.getImage(), lanDetectSend.getCode(), lanDetectSend.getName(), 0);
        mTranslates.add(translate);
        GridLayoutManager manager = new GridLayoutManager(TranslateActivity.this, 1);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new TranslateAdapter(mTranslates, this);
        mRecyclerView.setAdapter(mAdapter);
        TranslateAPI translateAPI = new TranslateAPI(TranslateActivity.this, lanDetectSend.getCode(),
                lanTranslateSend.getCode(), mTranslates, mAdapter,mRecyclerView, 1);
        translateAPI.execute(result);
    }

    @Override
    public void speakText(Translate translate) {
        mToSpeechManager = new TextToSpeechManager();
        mToSpeechManager.init(TranslateActivity.this, translate.getCode(), translate.getText());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_camera:
                startActivity(new CameraActivity().getIntent(TranslateActivity.this));
                finish();
                break;
            case R.id.bt_home:
                finish();
                break;
            default:
                break;
        }
    }
}
