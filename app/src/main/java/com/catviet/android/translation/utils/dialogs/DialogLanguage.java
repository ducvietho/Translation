package com.catviet.android.translation.utils.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.screen.text.LanguageAdapter;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.OnClickItem;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 27/04/2018.
 */

public class DialogLanguage implements OnClickItem<Language> {
    @BindView(R.id.rec_language_detect)
    RecyclerView mLanguageDetect;
    @BindView(R.id.rec_language_translate)
    RecyclerView mLanguageTranslate;
    private Context mContext;
    private Dialog mDialog;
    List<Language> mLanguageList;
    List<Language> mLanguageListTranslate;
    private TextViewRegular languageDetect;
    private TextViewRegular languageTranslate;
    private LanguageAdapter adapterDetect;
    private LanguageAdapter adapterTranslate;
    private int mType;
    private CircleImageView mImgDetect;
    private CircleImageView mImgTranslate;

    public DialogLanguage(Context context, TextViewRegular languageDetect, TextViewRegular languageTranslate, int type) {
        mContext = context;
        this.languageDetect = languageDetect;
        this.languageTranslate = languageTranslate;
        mType = type;
        mDialog = new Dialog(context);
    }

    public DialogLanguage(Context context, TextViewRegular languageDetect, TextViewRegular languageTranslate, CircleImageView imgDetect, CircleImageView imgTranslate, int type) {
        mContext = context;
        this.languageDetect = languageDetect;
        this.languageTranslate = languageTranslate;
        mType = type;
        mImgDetect = imgDetect;
        mImgTranslate = imgTranslate;
        mDialog = new Dialog(context);
    }

    @Override
    public void onClick(Language language, int type, int position) {
        switch (mType) {
            case 0:
                switch (type) {
                    case 0:
                        SharedPreferences.Editor editorDetect = mContext.getSharedPreferences(Constants.PRE_DETECT, MODE_PRIVATE).edit();
                        editorDetect.putString(Constants.EXTRA_DETECT, new Gson().toJson(language));
                        editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, position);
                        editorDetect.commit();
                        languageDetect.setText(language.getName());
                        break;
                    case 1:
                        SharedPreferences.Editor editorTranslate = mContext.getSharedPreferences(Constants.PRE_TRANSLATE, MODE_PRIVATE).edit();
                        editorTranslate.putString(Constants.EXTRA_TRANSLATE, new Gson().toJson(language));
                        editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, position);
                        editorTranslate.commit();
                        languageTranslate.setText(language.getName());
                        break;

                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        SharedPreferences.Editor editorDetect = mContext.getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE).edit();
                        editorDetect.putString(Constants.EXTRA_DETECT_CAMERA, new Gson().toJson(language));
                        editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, position);
                        editorDetect.commit();
                        languageDetect.setText(language.getName());
                        break;
                    case 1:
                        SharedPreferences.Editor editorTranslate = mContext.getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE).edit();
                        editorTranslate.putString(Constants.EXTRA_TRANSLATE_CAMERA, new Gson().toJson(language));
                        editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, position);
                        editorTranslate.commit();
                        languageTranslate.setText(language.getName());
                        break;

                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        SharedPreferences.Editor editorDetect = mContext.getSharedPreferences(Constants.PRE_DETECT_VOICE, MODE_PRIVATE).edit();
                        editorDetect.putString(Constants.EXTRA_DETECT_VOICE, new Gson().toJson(language));
                        editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, position);
                        editorDetect.commit();
                        languageDetect.setText(language.getName());
                        Picasso.with(mContext).load(language.getImage()).into(mImgDetect);
                        break;
                    case 1:
                        SharedPreferences.Editor editorTranslate = mContext.getSharedPreferences(Constants.PRE_TRANSLATE_VOICE, MODE_PRIVATE).edit();
                        editorTranslate.putString(Constants.EXTRA_TRANSLATE_VOICE, new Gson().toJson(language));
                        editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, position);
                        editorTranslate.commit();
                        languageTranslate.setText(language.getName());
                        Picasso.with(mContext).load(language.getImage()).into(mImgTranslate);
                        break;

                }
                break;

        }

    }

    public void showDialogText() {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_language);
        ButterKnife.bind(this, mDialog);
        mLanguageDetect.setHasFixedSize(true);
        mLanguageTranslate.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        GridLayoutManager managerDetect = new GridLayoutManager(mContext, 1);
        mLanguageDetect.setLayoutManager(managerDetect);
        mLanguageTranslate.setLayoutManager(manager);
        mLanguageListTranslate = new ArrayList<>();
        mLanguageListTranslate.add(new Language("Vietnamese", "vi", R.drawable.ic_vietnam));
        mLanguageListTranslate.add(new Language("English", "en", R.drawable.ic_uk));
        mLanguageListTranslate.add(new Language("French", "fr", R.drawable.ic_france));
        mLanguageListTranslate.add(new Language("German", "de", R.drawable.ic_germany));
        mLanguageListTranslate.add(new Language("Italian", "it", R.drawable.ic_italy));
        mLanguageListTranslate.add(new Language("Japanese", "ja", R.drawable.ic_japan));
        mLanguageListTranslate.add(new Language("Korean", "ko", R.drawable.ic_korea));
        mLanguageListTranslate.add(new Language("Russian", "ru", R.drawable.ic_russia));
        mLanguageListTranslate.add(new Language("Spanish", "es", R.drawable.ic_spain));

        mLanguageListTranslate.add(new Language("Indonesia","id-ID",R.drawable.ic_indonesia));
        mLanguageListTranslate.add(new Language("China","zh-CN",R.drawable.ic_china));
        mLanguageListTranslate.add(new Language("Malaysia","ms-MY",R.drawable.ic_malaysia));
        mLanguageListTranslate.add(new Language("India","hi-IN",R.drawable.ic_india));
        mLanguageListTranslate.add(new Language("Czech Republic","cs-CZ",R.drawable.ic_czech));
        mLanguageListTranslate.add(new Language("Denmark","da-DK",R.drawable.ic_denmark));
        mLanguageListTranslate.add(new Language("Australia","en-AU",R.drawable.ic_australia));
        mLanguageListTranslate.add(new Language("Ghana","en-GH",R.drawable.ic_ghana));
        mLanguageListTranslate.add(new Language("Canada","en-CA",R.drawable.ic_canada));
        mLanguageListTranslate.add(new Language("Ireland","en-IE",R.drawable.ic_ireland));
        mLanguageListTranslate.add(new Language("New Zealand","en-NZ",R.drawable.ic_new_zealand));
        mLanguageListTranslate.add(new Language("Nigeria","en-NG",R.drawable.ic_nigeria));
        mLanguageListTranslate.add(new Language("Philippines","fil-PH",R.drawable.ic_philippines));
        mLanguageListTranslate.add(new Language("South Africa","en-ZA",R.drawable.ic_south_africa));
        mLanguageListTranslate.add(new Language("Argentina","es-AR",R.drawable.ic_argentina));
        mLanguageListTranslate.add(new Language("Bolivia","es-BO",R.drawable.ic_bolivia));
        mLanguageListTranslate.add(new Language("Chile","es-CL",R.drawable.ic_chile));
        mLanguageListTranslate.add(new Language("Colombia","km-KH",R.drawable.ic_colombia));
        mLanguageListTranslate.add(new Language("Costa Rica","es-CR",R.drawable.ic_costa_rica));
        mLanguageListTranslate.add(new Language("Ecuador","es-EC",R.drawable.ic_ecuador));
        mLanguageListTranslate.add(new Language("Honduras","es-HN",R.drawable.ic_honduras));
        mLanguageListTranslate.add(new Language("Mexico","es-MX",R.drawable.ic_mexico));
        mLanguageListTranslate.add(new Language("Panama","es-PA",R.drawable.ic_panama));
        mLanguageListTranslate.add(new Language("Paraguay","es-PY",R.drawable.ic_paraguay));
        mLanguageListTranslate.add(new Language("Peru","es-PE",R.drawable.ic_peru));
        mLanguageListTranslate.add(new Language("Uruguay","es-UY",R.drawable.ic_uruguay));
        mLanguageListTranslate.add(new Language("Venezuela","es-VE",R.drawable.ic_venezuela));
        mLanguageListTranslate.add(new Language("Croatia","hr-HR",R.drawable.ic_croatia));
        mLanguageListTranslate.add(new Language("Iceland","is-IS",R.drawable.ic_iceland));
        mLanguageListTranslate.add(new Language("Laos","lo-LA",R.drawable.ic_laos));
        mLanguageListTranslate.add(new Language("Hungary","hu-HU",R.drawable.ic_hungary));
        mLanguageListTranslate.add(new Language("Netherlands","nl-NL",R.drawable.ic_netherlands));
        mLanguageListTranslate.add(new Language("Nepal","ne-NP",R.drawable.ic_nepal));
        mLanguageListTranslate.add(new Language("Norway","nb-NO",R.drawable.ic_norway));
        mLanguageListTranslate.add(new Language("Poland","pl-PL",R.drawable.ic_poland));
        mLanguageListTranslate.add(new Language("Brazil","pt-BR",R.drawable.ic_brazil));
        mLanguageListTranslate.add(new Language("Portugal","pt-PT",R.drawable.ic_portugal));
        mLanguageListTranslate.add(new Language("Romania","ro-RO",R.drawable.ic_romania));
        mLanguageListTranslate.add(new Language("Slovakia","sk-SK",R.drawable.ic_slovakia));
        mLanguageListTranslate.add(new Language("Finland","fi-FI",R.drawable.ic_finland));
        mLanguageListTranslate.add(new Language("Sweden","sv-SE",R.drawable.ic_sweden));
        mLanguageListTranslate.add(new Language("Singapore","ta-SG",R.drawable.ic_singapore));
        mLanguageListTranslate.add(new Language("Turkey","tr-TR",R.drawable.ic_turkey));
        mLanguageListTranslate.add(new Language("Pakistan","ur-PK",R.drawable.ic_pakistan));
        mLanguageListTranslate.add(new Language("Greece","el-GR",R.drawable.ic_greece));
        mLanguageListTranslate.add(new Language("Bulgaria","bg-BG",R.drawable.ic_bulgaria));
        mLanguageListTranslate.add(new Language("Serbia","sr-RS",R.drawable.ic_serbia));
        mLanguageListTranslate.add(new Language("Ukraine","uk-UA",R.drawable.ic_ukraine));
        mLanguageListTranslate.add(new Language("Israel","he-IL",R.drawable.ic_israel));
        mLanguageListTranslate.add(new Language("Jordan","ar-JO",R.drawable.ic_jordan));
        mLanguageListTranslate.add(new Language("Algeria","ar-DZ",R.drawable.ic_algeria));
        mLanguageListTranslate.add(new Language("Saudi Arabia","ar-SA",R.drawable.ic_saudi_arabia));
        mLanguageListTranslate.add(new Language("Iraq","ar-IQ",R.drawable.ic_iraq));
        mLanguageListTranslate.add(new Language("Morocco","ar-MA",R.drawable.ic_morocco));
        mLanguageListTranslate.add(new Language("Oman","ar-OM",R.drawable.ic_oman));
        mLanguageListTranslate.add(new Language("Egypt","ar-EG",R.drawable.ic_egypt));
        mLanguageListTranslate.add(new Language("Iran","fa-IR",R.drawable.ic_iran));
        mLanguageListTranslate.add(new Language("Thailand","th-TH",R.drawable.ic_thailand));

        SharedPreferences preferencesDetect = mContext.getSharedPreferences(Constants.PRE_DETECT, MODE_PRIVATE);
        SharedPreferences preferencesTranslate = mContext.getSharedPreferences(Constants.PRE_TRANSLATE, MODE_PRIVATE);
        int positionDetect = preferencesDetect.getInt(Constants.EXTRA_DETECT_POSITION, -1);
        int positionTranslate = preferencesTranslate.getInt(Constants.EXTRA_TRANSLATE_POSITION, -1);
        if (positionDetect == -1) {
            adapterDetect = new LanguageAdapter(mLanguageListTranslate, this, 0, 0);
        } else {
            adapterDetect = new LanguageAdapter(mLanguageListTranslate, this, 0, positionDetect);
        }
        if (positionTranslate == -1) {
            adapterTranslate = new LanguageAdapter(mLanguageListTranslate, this, 1, 0);
        } else {
            adapterTranslate = new LanguageAdapter(mLanguageListTranslate, this, 1, positionTranslate);
        }
        mLanguageDetect.setAdapter(adapterDetect);
        mLanguageTranslate.setAdapter(adapterTranslate);

        mLanguageDetect.smoothScrollToPosition(positionDetect);
        mLanguageTranslate.smoothScrollToPosition(positionTranslate);
        final Window window = mDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    public void showDialogCamera() {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_language);
        ButterKnife.bind(this, mDialog);
        mLanguageDetect.setHasFixedSize(true);
        mLanguageTranslate.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        GridLayoutManager managerDetect = new GridLayoutManager(mContext, 1);
        mLanguageDetect.setLayoutManager(managerDetect);
        mLanguageTranslate.setLayoutManager(manager);
        mLanguageList = new ArrayList<>();
        mLanguageListTranslate = new ArrayList<>();
        mLanguageList.add(new Language("Vietnamese", "vi", R.drawable.ic_vietnam));
        mLanguageList.add(new Language("English", "en", R.drawable.ic_uk));
        mLanguageList.add(new Language("French", "fr", R.drawable.ic_france));
        mLanguageList.add(new Language("German", "de", R.drawable.ic_germany));
        mLanguageList.add(new Language("Italy", "it", R.drawable.ic_italy));
        mLanguageList.add(new Language("Japanese", "ja", R.drawable.ic_japan));
        mLanguageList.add(new Language("Korean", "ko", R.drawable.ic_korea));
        mLanguageList.add(new Language("Russian", "ru", R.drawable.ic_russia));
        mLanguageList.add(new Language("Spanish", "es", R.drawable.ic_spain));
        mLanguageList.add(new Language("Egypt","ar",R.drawable.ic_egypt));
        mLanguageList.add(new Language("Belarus","be",R.drawable.ic_belarus));
        mLanguageList.add(new Language("Bulgaria","bg",R.drawable.ic_bulgaria));
        mLanguageList.add(new Language("Chinese","zh*",R.drawable.ic_china));
        mLanguageList.add(new Language("Croatia","hr",R.drawable.ic_croatia));
        mLanguageList.add(new Language("Czech","cs",R.drawable.ic_czech));
        mLanguageList.add(new Language("Danish","da",R.drawable.ic_denmark));
        mLanguageList.add(new Language("Estonia","et",R.drawable.ic_estonia));
        mLanguageList.add(new Language("Hungary","hu",R.drawable.ic_hungary));
        mLanguageList.add(new Language("Iceland","is",R.drawable.ic_iceland));
        mLanguageList.add(new Language("Indonesia","id",R.drawable.ic_indonesia));
        mLanguageList.add(new Language("Nepal","ne",R.drawable.ic_nepal));
        mLanguageList.add(new Language("Norway","no",R.drawable.ic_norway));
        mLanguageList.add(new Language("Iran","fa",R.drawable.ic_iran));
        mLanguageList.add(new Language("Poland","pl",R.drawable.ic_poland));
        mLanguageList.add(new Language("Portugal","pt",R.drawable.ic_portugal));
        mLanguageList.add(new Language("Romania","ro",R.drawable.ic_romania));
        mLanguageList.add(new Language("India(Sanskrit)","sa",R.drawable.ic_india));
        mLanguageList.add(new Language("Serbi","sr",R.drawable.ic_serbia));
        mLanguageList.add(new Language("Slovakia","sk",R.drawable.ic_slovakia));
        mLanguageList.add(new Language("Slovenia","sl",R.drawable.ic_slovenia));
        mLanguageList.add(new Language("Sweden","sv",R.drawable.ic_sweden));
        mLanguageList.add(new Language("India(Tamil)","ta",R.drawable.ic_india));
        mLanguageList.add(new Language("Thailand","th",R.drawable.ic_thailand));
        mLanguageList.add(new Language("Turkey","tr",R.drawable.ic_turkey));
        mLanguageList.add(new Language("Ukraina","uk",R.drawable.ic_ukraine));
        mLanguageList.add(new Language("Uzbekistan","uz",R.drawable.ic_uzbekistan));
        mLanguageListTranslate.add(new Language("Vietnamese", "vi", R.drawable.ic_vietnam));
        mLanguageListTranslate.add(new Language("English", "en", R.drawable.ic_uk));
        mLanguageListTranslate.add(new Language("French", "fr", R.drawable.ic_france));
        mLanguageListTranslate.add(new Language("German", "de", R.drawable.ic_germany));
        mLanguageListTranslate.add(new Language("Italian", "it", R.drawable.ic_italy));
        mLanguageListTranslate.add(new Language("Japanese", "ja", R.drawable.ic_japan));
        mLanguageListTranslate.add(new Language("Korean", "ko", R.drawable.ic_korea));
        mLanguageListTranslate.add(new Language("Russian", "ru", R.drawable.ic_russia));
        mLanguageListTranslate.add(new Language("Spanish", "es", R.drawable.ic_spain));
        mLanguageListTranslate.add(new Language("Indonesia","id-ID",R.drawable.ic_indonesia));
        mLanguageListTranslate.add(new Language("China","zh-CN",R.drawable.ic_china));
        mLanguageListTranslate.add(new Language("Malaysia","ms-MY",R.drawable.ic_malaysia));
        mLanguageListTranslate.add(new Language("India","hi-IN",R.drawable.ic_india));
        mLanguageListTranslate.add(new Language("Czech Republic","cs-CZ",R.drawable.ic_czech));
        mLanguageListTranslate.add(new Language("Denmark","da-DK",R.drawable.ic_denmark));
        mLanguageListTranslate.add(new Language("Australia","en-AU",R.drawable.ic_australia));
        mLanguageListTranslate.add(new Language("Ghana","en-GH",R.drawable.ic_ghana));
        mLanguageListTranslate.add(new Language("Canada","en-CA",R.drawable.ic_canada));
        mLanguageListTranslate.add(new Language("Ireland","en-IE",R.drawable.ic_ireland));
        mLanguageListTranslate.add(new Language("New Zealand","en-NZ",R.drawable.ic_new_zealand));
        mLanguageListTranslate.add(new Language("Nigeria","en-NG",R.drawable.ic_nigeria));
        mLanguageListTranslate.add(new Language("Philippines","fil-PH",R.drawable.ic_philippines));
        mLanguageListTranslate.add(new Language("South Africa","en-ZA",R.drawable.ic_south_africa));
        mLanguageListTranslate.add(new Language("Argentina","es-AR",R.drawable.ic_argentina));
        mLanguageListTranslate.add(new Language("Bolivia","es-BO",R.drawable.ic_bolivia));
        mLanguageListTranslate.add(new Language("Chile","es-CL",R.drawable.ic_chile));
        mLanguageListTranslate.add(new Language("Colombia","km-KH",R.drawable.ic_colombia));
        mLanguageListTranslate.add(new Language("Costa Rica","es-CR",R.drawable.ic_costa_rica));
        mLanguageListTranslate.add(new Language("Ecuador","es-EC",R.drawable.ic_ecuador));
        mLanguageListTranslate.add(new Language("Honduras","es-HN",R.drawable.ic_honduras));
        mLanguageListTranslate.add(new Language("Mexico","es-MX",R.drawable.ic_mexico));
        mLanguageListTranslate.add(new Language("Panama","es-PA",R.drawable.ic_panama));
        mLanguageListTranslate.add(new Language("Paraguay","es-PY",R.drawable.ic_paraguay));
        mLanguageListTranslate.add(new Language("Peru","es-PE",R.drawable.ic_peru));
        mLanguageListTranslate.add(new Language("Uruguay","es-UY",R.drawable.ic_uruguay));
        mLanguageListTranslate.add(new Language("Venezuela","es-VE",R.drawable.ic_venezuela));
        mLanguageListTranslate.add(new Language("Croatia","hr-HR",R.drawable.ic_croatia));
        mLanguageListTranslate.add(new Language("Iceland","is-IS",R.drawable.ic_iceland));
        mLanguageListTranslate.add(new Language("Laos","lo-LA",R.drawable.ic_laos));
        mLanguageListTranslate.add(new Language("Hungary","hu-HU",R.drawable.ic_hungary));
        mLanguageListTranslate.add(new Language("Netherlands","nl-NL",R.drawable.ic_netherlands));
        mLanguageListTranslate.add(new Language("Nepal","ne-NP",R.drawable.ic_nepal));
        mLanguageListTranslate.add(new Language("Norway","nb-NO",R.drawable.ic_norway));
        mLanguageListTranslate.add(new Language("Poland","pl-PL",R.drawable.ic_poland));
        mLanguageListTranslate.add(new Language("Brazil","pt-BR",R.drawable.ic_brazil));
        mLanguageListTranslate.add(new Language("Portugal","pt-PT",R.drawable.ic_portugal));
        mLanguageListTranslate.add(new Language("Romania","ro-RO",R.drawable.ic_romania));
        mLanguageListTranslate.add(new Language("Slovakia","sk-SK",R.drawable.ic_slovakia));
        mLanguageListTranslate.add(new Language("Finland","fi-FI",R.drawable.ic_finland));
        mLanguageListTranslate.add(new Language("Sweden","sv-SE",R.drawable.ic_sweden));
        mLanguageListTranslate.add(new Language("Singapore","ta-SG",R.drawable.ic_singapore));
        mLanguageListTranslate.add(new Language("Turkey","tr-TR",R.drawable.ic_turkey));
        mLanguageListTranslate.add(new Language("Pakistan","ur-PK",R.drawable.ic_pakistan));
        mLanguageListTranslate.add(new Language("Greece","el-GR",R.drawable.ic_greece));
        mLanguageListTranslate.add(new Language("Bulgaria","bg-BG",R.drawable.ic_bulgaria));
        mLanguageListTranslate.add(new Language("Serbia","sr-RS",R.drawable.ic_serbia));
        mLanguageListTranslate.add(new Language("Ukraine","uk-UA",R.drawable.ic_ukraine));
        mLanguageListTranslate.add(new Language("Israel","he-IL",R.drawable.ic_israel));
        mLanguageListTranslate.add(new Language("Jordan","ar-JO",R.drawable.ic_jordan));
        mLanguageListTranslate.add(new Language("Algeria","ar-DZ",R.drawable.ic_algeria));
        mLanguageListTranslate.add(new Language("Saudi Arabia","ar-SA",R.drawable.ic_saudi_arabia));
        mLanguageListTranslate.add(new Language("Iraq","ar-IQ",R.drawable.ic_iraq));
        mLanguageListTranslate.add(new Language("Morocco","ar-MA",R.drawable.ic_morocco));
        mLanguageListTranslate.add(new Language("Oman","ar-OM",R.drawable.ic_oman));
        mLanguageListTranslate.add(new Language("Egypt","ar-EG",R.drawable.ic_egypt));
        mLanguageListTranslate.add(new Language("Iran","fa-IR",R.drawable.ic_iran));
        mLanguageListTranslate.add(new Language("Thailand","th-TH",R.drawable.ic_thailand));

        SharedPreferences preferencesDetect = mContext.getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
        SharedPreferences preferencesTranslate = mContext.getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
        int positionDetect = preferencesDetect.getInt(Constants.EXTRA_DETECT_POSITION, -1);
        int positionTranslate = preferencesTranslate.getInt(Constants.EXTRA_TRANSLATE_POSITION, -1);
        if (positionDetect == -1) {
            adapterDetect = new LanguageAdapter(mLanguageList, this, 0, 1);
        } else {
            adapterDetect = new LanguageAdapter(mLanguageList, this, 0, positionDetect);
        }
        if (positionTranslate == -1) {
            adapterTranslate = new LanguageAdapter(mLanguageListTranslate, this, 1, 0);
        } else {
            adapterTranslate = new LanguageAdapter(mLanguageListTranslate, this, 1, positionTranslate);
        }
        mLanguageDetect.setAdapter(adapterDetect);
        mLanguageTranslate.setAdapter(adapterTranslate);
        mLanguageDetect.smoothScrollToPosition(positionDetect);
        mLanguageTranslate.smoothScrollToPosition(positionTranslate);
        final Window window = mDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    public void showDialogVoice() {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_language);
        ButterKnife.bind(this, mDialog);
        mLanguageDetect.setHasFixedSize(true);
        mLanguageTranslate.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        GridLayoutManager managerDetect = new GridLayoutManager(mContext, 1);
        mLanguageDetect.setLayoutManager(managerDetect);
        mLanguageTranslate.setLayoutManager(manager);
        mLanguageList = new ArrayList<>();
        mLanguageList.add(new Language("Vietnamese", "vi-VN", R.drawable.ic_vietnam));
        mLanguageList.add(new Language("English", "en-US", R.drawable.ic_uk));
        mLanguageList.add(new Language("French", "fr-FR", R.drawable.ic_france));
        mLanguageList.add(new Language("German", "de-DE", R.drawable.ic_germany));
        mLanguageList.add(new Language("Italian", "it-IT", R.drawable.ic_italy));
        mLanguageList.add(new Language("Japanese", "ja-JP", R.drawable.ic_japan));
        mLanguageList.add(new Language("Korean", "ko-KR", R.drawable.ic_korea));
        mLanguageList.add(new Language("Russian", "ru-RU", R.drawable.ic_russia));
        mLanguageList.add(new Language("Spanish", "es-ES", R.drawable.ic_spain));
        mLanguageList.add(new Language("Indonesia","id-ID",R.drawable.ic_indonesia));
        mLanguageList.add(new Language("China","cmn-Hans-CN",R.drawable.ic_china));
        mLanguageList.add(new Language("Malaysia","ms-MY",R.drawable.ic_malaysia));
        mLanguageList.add(new Language("India","hi-IN",R.drawable.ic_india));
        mLanguageList.add(new Language("Czech Republic","cs-CZ",R.drawable.ic_czech));
        mLanguageList.add(new Language("Denmark","da-DK",R.drawable.ic_denmark));
        mLanguageList.add(new Language("Australia","en-AU",R.drawable.ic_australia));
        mLanguageList.add(new Language("Ghana","en-GH",R.drawable.ic_ghana));
        mLanguageList.add(new Language("Canada","en-CA",R.drawable.ic_canada));
        mLanguageList.add(new Language("Ireland","en-IE",R.drawable.ic_ireland));
        mLanguageList.add(new Language("New Zealand","en-NZ",R.drawable.ic_new_zealand));
        mLanguageList.add(new Language("Nigeria","en-NG",R.drawable.ic_nigeria));
        mLanguageList.add(new Language("Philippines","fil-PH",R.drawable.ic_philippines));
        mLanguageList.add(new Language("South Africa","en-ZA",R.drawable.ic_south_africa));
        mLanguageList.add(new Language("Argentina","es-AR",R.drawable.ic_argentina));
        mLanguageList.add(new Language("Bolivia","es-BO",R.drawable.ic_bolivia));
        mLanguageList.add(new Language("Chile","es-CL",R.drawable.ic_chile));
        mLanguageList.add(new Language("Colombia","km-KH",R.drawable.ic_colombia));
        mLanguageList.add(new Language("Costa Rica","es-CR",R.drawable.ic_costa_rica));
        mLanguageList.add(new Language("Ecuador","es-EC",R.drawable.ic_ecuador));
        mLanguageList.add(new Language("Honduras","es-HN",R.drawable.ic_honduras));
        mLanguageList.add(new Language("Mexico","es-MX",R.drawable.ic_mexico));
        mLanguageList.add(new Language("Panama","es-PA",R.drawable.ic_panama));
        mLanguageList.add(new Language("Paraguay","es-PY",R.drawable.ic_paraguay));
        mLanguageList.add(new Language("Peru","es-PE",R.drawable.ic_peru));
        mLanguageList.add(new Language("Uruguay","es-UY",R.drawable.ic_uruguay));
        mLanguageList.add(new Language("Venezuela","es-VE",R.drawable.ic_venezuela));
        mLanguageList.add(new Language("Croatia","hr-HR",R.drawable.ic_croatia));
        mLanguageList.add(new Language("Iceland","is-IS",R.drawable.ic_iceland));
        mLanguageList.add(new Language("Laos","lo-LA",R.drawable.ic_laos));
        mLanguageList.add(new Language("Hungary","hu-HU",R.drawable.ic_hungary));
        mLanguageList.add(new Language("Netherlands","nl-NL",R.drawable.ic_netherlands));
        mLanguageList.add(new Language("Nepal","ne-NP",R.drawable.ic_nepal));
        mLanguageList.add(new Language("Norway","nb-NO",R.drawable.ic_norway));
        mLanguageList.add(new Language("Poland","pl-PL",R.drawable.ic_poland));
        mLanguageList.add(new Language("Brazil","pt-BR",R.drawable.ic_brazil));
        mLanguageList.add(new Language("Portugal","pt-PT",R.drawable.ic_portugal));
        mLanguageList.add(new Language("Romania","ro-RO",R.drawable.ic_romania));
        mLanguageList.add(new Language("Slovakia","sk-SK",R.drawable.ic_slovakia));
        mLanguageList.add(new Language("Finland","fi-FI",R.drawable.ic_finland));
        mLanguageList.add(new Language("Sweden","sv-SE",R.drawable.ic_sweden));
        mLanguageList.add(new Language("Singapore","ta-SG",R.drawable.ic_singapore));
        mLanguageList.add(new Language("Turkey","tr-TR",R.drawable.ic_turkey));
        mLanguageList.add(new Language("Pakistan","ur-PK",R.drawable.ic_pakistan));
        mLanguageList.add(new Language("Greece","el-GR",R.drawable.ic_greece));
        mLanguageList.add(new Language("Bulgaria","bg-BG",R.drawable.ic_bulgaria));
        mLanguageList.add(new Language("Serbia","sr-RS",R.drawable.ic_serbia));
        mLanguageList.add(new Language("Ukraine","uk-UA",R.drawable.ic_ukraine));
        mLanguageList.add(new Language("Israel","he-IL",R.drawable.ic_israel));
        mLanguageList.add(new Language("Jordan","ar-JO",R.drawable.ic_jordan));
        mLanguageList.add(new Language("Algeria","ar-DZ",R.drawable.ic_algeria));
        mLanguageList.add(new Language("Saudi Arabia","ar-SA",R.drawable.ic_saudi_arabia));
        mLanguageList.add(new Language("Iraq","ar-IQ",R.drawable.ic_iraq));
        mLanguageList.add(new Language("Morocco","ar-MA",R.drawable.ic_morocco));
        mLanguageList.add(new Language("Oman","ar-OM",R.drawable.ic_oman));
        mLanguageList.add(new Language("Egypt","ar-EG",R.drawable.ic_egypt));
        mLanguageList.add(new Language("Iran","fa-IR",R.drawable.ic_iran));
        mLanguageList.add(new Language("Thailand","th-TH",R.drawable.ic_thailand));
        SharedPreferences preferencesDetect = mContext.getSharedPreferences(Constants.PRE_DETECT_VOICE, MODE_PRIVATE);
        SharedPreferences preferencesTranslate = mContext.getSharedPreferences(Constants.PRE_TRANSLATE_VOICE, MODE_PRIVATE);
        int positionDetect = preferencesDetect.getInt(Constants.EXTRA_DETECT_POSITION, -1);
        int positionTranslate = preferencesTranslate.getInt(Constants.EXTRA_TRANSLATE_POSITION, -1);
        if (positionDetect == -1) {
            adapterDetect = new LanguageAdapter(mLanguageList, this, 0, 1);
        } else {
            adapterDetect = new LanguageAdapter(mLanguageList, this, 0, positionDetect);
        }
        if (positionTranslate == -1) {
            adapterTranslate = new LanguageAdapter(mLanguageList, this, 1, 0);
        } else {
            adapterTranslate = new LanguageAdapter(mLanguageList, this, 1, positionTranslate);
        }
        mLanguageDetect.setAdapter(adapterDetect);
        mLanguageTranslate.setAdapter(adapterTranslate);
        mLanguageDetect.smoothScrollToPosition(positionDetect);
        mLanguageTranslate.smoothScrollToPosition(positionTranslate);
        final Window window = mDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    public void cancel() {
        mDialog.dismiss();
    }
}
