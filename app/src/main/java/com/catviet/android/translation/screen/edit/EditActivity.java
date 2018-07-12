package com.catviet.android.translation.screen.edit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.data.resource.local.TranslateDataHelper;
import com.catviet.android.translation.screen.home.HomeActivity;

import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.TranslateAPI;
import com.catviet.android.translation.utils.customview.EditTextLight;
import com.google.gson.Gson;
import com.werdpressed.partisan.rundo.RunDo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, RunDo.TextLink {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_result)
    EditTextLight edResult;
    @BindView(R.id.bt_copy)
    ImageView imgCopy;
    @BindView(R.id.bt_send)
    FloatingActionButton btSend;
    @BindView(R.id.bt_undo)
    ImageView mUnDo;
    @BindView(R.id.bt_redo)
    ImageView mRedo;
    private RunDo mRunDo;

    public static Intent getIntent(Context context, String result) {
        Intent intent = new Intent(context, EditActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.EXTRA_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        mRunDo = RunDo.Factory.getInstance(getSupportFragmentManager());
        imgBack.setOnClickListener(this);
        imgCopy.setOnClickListener(this);
        btSend.setOnClickListener(this);
        mUnDo.setOnClickListener(this);
        mRedo.setOnClickListener(this);
        String text = getIntent().getStringExtra(Constants.EXTRA_RESULT);
        edResult.setText(text);
        edResult.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    if (edResult.getText().toString().equals("")) {
                        Toast.makeText(EditActivity.this, "Please enter text", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new HomeActivity().getIntent(EditActivity.this,2,edResult.getText().toString()));
                        finish();
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.bt_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied text", edResult.getText().toString());
                Toast.makeText(EditActivity.this, "Copied text", Toast.LENGTH_LONG).show();
                clipboard.setPrimaryClip(clip);
                break;
            case R.id.bt_send:
                if (edResult.getText().toString().equals("")) {
                    Toast.makeText(EditActivity.this, "Please enter text", Toast.LENGTH_LONG).show();
                } else {


                    startActivity(new HomeActivity().getIntent(EditActivity.this,2,edResult.getText().toString()));
                    finish();
                }
                break;
            case R.id.bt_undo:
                mRunDo.undo();
                break;
            case R.id.bt_redo:
                mRunDo.redo();
                break;
            default:
                break;

        }
    }


    @Override
    public EditText getEditTextForRunDo() {
        return edResult;
    }
}
