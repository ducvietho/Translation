package com.catviet.android.translation.screen.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.models.Image;
import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.OnClickItem;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.catviet.android.translation.utils.dialogs.DialogScanning;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, OnClickItem<Language>, SurfaceHolder.Callback, Camera.PictureCallback {
    private static final int REQUEST_CODE_CAMERA = 1001;
    private static final int REQUEST_CODE_GALLERY = 1002;
    private final String TAG = "take photo";
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_language_detect)
    TextViewRegular languageDetect;
    @BindView(R.id.tv_language_translate)
    TextViewRegular languageTranslate;
    @BindView(R.id.img_change)
    ImageView imgChange;
    @BindView(R.id.img_close_detect)
    ImageView imgCloseDetect;
    @BindView(R.id.img_close_translate)
    ImageView imgCloseTranslate;
    @BindView(R.id.img_choose_detect)
    ImageView imgChooseDetect;
    @BindView(R.id.img_choose_translate)
    ImageView imgChooseTranslate;
    @BindView(R.id.layout_language)
    LinearLayout mLayoutLanguage;
    @BindView(R.id.rec_language_detect)
    RecyclerView mLanguageDetect;
    @BindView(R.id.rec_language_translate)
    RecyclerView mLanguageTranslate;
    @BindView(R.id.view_camera)
    SurfaceView mCameraView;
    @BindView(R.id.bt_photo)
    ImageView mTakePhoto;
    @BindView(R.id.layoutImageView)
    FrameLayout layoutViewImage;
    @BindView(R.id.imageCapture)
    ImageView imageView;
    @BindView(R.id.view_language)
    RelativeLayout mViewLanguage;
    @BindView(R.id.layout_chose)
    LinearLayout mViewTakeImg;
//    @BindView(R.id.bt_done)
//    ImageView imgDone;
    @BindView(R.id.bt_gallery)
    ImageView imgGallery;
    @BindView(R.id.layout_detect)
    LinearLayout mLayoutDetect;
    @BindView(R.id.layout_translate)
    LinearLayout mLayoutTranslate;
    Camera camera;
    SurfaceHolder mHolder;
    List<Language> mLanguageList;
    List<Language> mLanguageListTranslate;
    String pathFileOut;
    LanguageAdapterCamera adapterDetect;
    LanguageAdapterCamera adapterTranslate;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        mHolder = mCameraView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        imgBack.setOnClickListener(this);
        imgChange.setOnClickListener(this);
        imgChooseDetect.setOnClickListener(this);
        imgChooseTranslate.setOnClickListener(this);
        imgCloseDetect.setOnClickListener(this);
        imgCloseTranslate.setOnClickListener(this);
        mTakePhoto.setOnClickListener(this);
        mLayoutDetect.setOnClickListener(this);
        mLayoutTranslate.setOnClickListener(this);
        //imgDone.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(CameraActivity.this, 1);
        GridLayoutManager managerDetect = new GridLayoutManager(CameraActivity.this, 1);
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
        SharedPreferences preferencesDetect = getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
        SharedPreferences preferencesTranslate = getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
        int positionDetect = preferencesDetect.getInt(Constants.EXTRA_DETECT_POSITION, -1);
        int positionTranslate = preferencesTranslate.getInt(Constants.EXTRA_TRANSLATE_POSITION, -1);
        if(positionDetect==-1){
             adapterDetect = new LanguageAdapterCamera(mLanguageList, this, 0, 0);
        }else {
             adapterDetect = new LanguageAdapterCamera(mLanguageList, this, 0, positionDetect);
        }
        if (positionTranslate==-1){
            adapterTranslate = new LanguageAdapterCamera(mLanguageList, this, 1, 0);
        }else {
            adapterTranslate = new LanguageAdapterCamera(mLanguageList, this, 1, positionTranslate);
        }
        mLanguageDetect.setAdapter(adapterDetect);
        mLanguageTranslate.setAdapter(adapterTranslate);
        mLanguageDetect.smoothScrollToPosition(positionDetect);
        mLanguageTranslate.smoothScrollToPosition(positionTranslate);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PRE_DETECT_CAMERA, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesTranslate = getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, Context
                .MODE_PRIVATE);
        String extra = sharedPreferences.getString(Constants.EXTRA_DETECT_CAMERA, null);
        String extraTranslate = sharedPreferencesTranslate.getString(Constants.EXTRA_TRANSLATE_CAMERA, null);
        if (extra != null) {
            Language lan = new Gson().fromJson(extra, Language.class);
            languageDetect.setText(lan.getName());

        }
        if (extraTranslate != null) {
            Language lan = new Gson().fromJson(extraTranslate, Language.class);
            languageTranslate.setText(lan.getName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY) {
            mViewLanguage.setVisibility(View.GONE);
            mViewTakeImg.setVisibility(View.GONE);
            ArrayList<Image> images =
                    data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
            try {
                Bitmap bitmap = new Compressor(this).compressToBitmap(new File(images.get(0).path));
                layoutViewImage.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                SharedPreferences prefeDetect = getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
                String detectLan = prefeDetect.getString(Constants.EXTRA_DETECT_CAMERA, null);
                Language languaDetect = new Gson().fromJson(detectLan, Language.class);
                new DialogScanning(CameraActivity.this).showDialog(bitmap,languaDetect.getCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }

                }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.no_change,R.anim.slide_down_info);
                break;
            case R.id.layout_detect:
                if(imgCloseDetect.getVisibility()==View.GONE){
                    imgCloseDetect.setVisibility(View.VISIBLE);
                    imgCloseTranslate.setVisibility(View.VISIBLE);
                    imgChooseDetect.setVisibility(View.GONE);
                    imgChooseTranslate.setVisibility(View.GONE);
                    mLayoutLanguage.setVisibility(View.VISIBLE);
                }else {
                    imgCloseDetect.setVisibility(View.GONE);
                    imgCloseTranslate.setVisibility(View.GONE);
                    imgChooseDetect.setVisibility(View.VISIBLE);
                    imgChooseTranslate.setVisibility(View.VISIBLE);
                    mLayoutLanguage.setVisibility(View.GONE);
                }

                break;
            case R.id.layout_translate:
                if(imgCloseTranslate.getVisibility()==View.GONE){
                    imgCloseDetect.setVisibility(View.VISIBLE);
                    imgCloseTranslate.setVisibility(View.VISIBLE);
                    imgChooseDetect.setVisibility(View.GONE);
                    imgChooseTranslate.setVisibility(View.GONE);
                    mLayoutLanguage.setVisibility(View.VISIBLE);
                }else {
                    imgCloseDetect.setVisibility(View.GONE);
                    imgCloseTranslate.setVisibility(View.GONE);
                    imgChooseDetect.setVisibility(View.VISIBLE);
                    imgChooseTranslate.setVisibility(View.VISIBLE);
                    mLayoutLanguage.setVisibility(View.GONE);
                }
                break;
            case R.id.img_close_detect:
                imgCloseDetect.setVisibility(View.GONE);
                imgCloseTranslate.setVisibility(View.GONE);
                imgChooseDetect.setVisibility(View.VISIBLE);
                imgChooseTranslate.setVisibility(View.VISIBLE);
                mLayoutLanguage.setVisibility(View.GONE);
                break;
            case R.id.img_close_translate:
                imgCloseDetect.setVisibility(View.GONE);
                imgCloseTranslate.setVisibility(View.GONE);
                imgChooseDetect.setVisibility(View.VISIBLE);
                imgChooseTranslate.setVisibility(View.VISIBLE);
                mLayoutLanguage.setVisibility(View.GONE);
                break;
            case R.id.bt_photo:
                MediaPlayer player = MediaPlayer.create(CameraActivity.this,R.raw.automatic_camera);
                player.start();
                camera.takePicture(null, null, this);
                break;
            case R.id.img_change:
                SharedPreferences preferencesDetect = getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
                SharedPreferences preferencesTranslate = getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA, MODE_PRIVATE);
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
            case R.id.bt_gallery:
                camera.stopPreview();
                Intent intent = new Intent(this, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(Language language, int type, int position) {
        switch (type) {
            case 0:
                SharedPreferences.Editor editorDetect = getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE)
                        .edit();
                editorDetect.putString(Constants.EXTRA_DETECT_CAMERA, new Gson().toJson(language));
                editorDetect.putInt(Constants.EXTRA_DETECT_POSITION, position);
                editorDetect.commit();
                languageDetect.setText(language.getName());
                break;
            case 1:
                SharedPreferences.Editor editorTranslate = getSharedPreferences(Constants.PRE_TRANSLATE_CAMERA,
                        MODE_PRIVATE).edit();
                editorTranslate.putString(Constants.EXTRA_TRANSLATE_CAMERA, new Gson().toJson(language));
                editorTranslate.putInt(Constants.EXTRA_TRANSLATE_POSITION, position);
                editorTranslate.commit();
                languageTranslate.setText(language.getName());
                break;

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera = Camera.open();
            changeOrientation();
        } catch (RuntimeException e) {
            Log.e("", "init_camera: " + e);
            return;
        }
        Camera.Parameters param;

        param = camera.getParameters();
        param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
            //camera.takePicture(shutter, raw, jpeg)
        } catch (Exception e) {
            Log.e(TAG, "init_camera: " + e);
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }


    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        File outputFile = new File(getCacheDir(), "photo_" + SystemClock.currentThreadTimeMillis() + ".jpg");
        pathFileOut = outputFile.getAbsolutePath();
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(bytes);
            fos.close();
            Bitmap bitmap = new Compressor(this).compressToBitmap(new File(pathFileOut));
            layoutViewImage.setVisibility(View.VISIBLE);
            mViewLanguage.setVisibility(View.GONE);
            mViewTakeImg.setVisibility(View.GONE);
            SharedPreferences prefeDetect = getSharedPreferences(Constants.PRE_DETECT_CAMERA, MODE_PRIVATE);
            String detectLan = prefeDetect.getString(Constants.EXTRA_DETECT_CAMERA, null);
            Language languaDetect = new Gson().fromJson(detectLan, Language.class);
            new DialogScanning(CameraActivity.this).showDialog(bitmap,languaDetect.getCode());
            //imgDone.setVisibility(View.VISIBLE);
            //imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    public void changeOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            camera.setDisplayOrientation(0);
        else camera.setDisplayOrientation(90);
    }

}
