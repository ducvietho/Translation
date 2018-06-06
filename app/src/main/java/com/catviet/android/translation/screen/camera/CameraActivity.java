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
    Camera camera;
    SurfaceHolder mHolder;
    List<Language> mLanguageList;
    String pathFileOut;
    LanguageAdapterCamera adapterDetect;
    LanguageAdapterCamera adapterTranslate;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        //imgDone.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(CameraActivity.this, 1);
        GridLayoutManager managerDetect = new GridLayoutManager(CameraActivity.this, 1);
        mLanguageDetect.setLayoutManager(managerDetect);
        mLanguageTranslate.setLayoutManager(manager);
        mLanguageList = new ArrayList<>();
        mLanguageList.add(new Language("Vietnamese", "vi", R.drawable.ic_vietnam));
        mLanguageList.add(new Language("English", "en", R.drawable.ic_uk));
        mLanguageList.add(new Language("French", "fr", R.drawable.ic_france));
        mLanguageList.add(new Language("German", "de", R.drawable.ic_germany));
        mLanguageList.add(new Language("Italian", "it", R.drawable.ic_italy));
        mLanguageList.add(new Language("Japanese", "ja", R.drawable.ic_japan));
        mLanguageList.add(new Language("Korean", "ko", R.drawable.ic_korea));
        mLanguageList.add(new Language("Russian", "ru", R.drawable.ic_russia));
        mLanguageList.add(new Language("Spanish", "es", R.drawable.ic_spain));
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
            case R.id.img_choose_detect:
                imgCloseDetect.setVisibility(View.VISIBLE);
                imgCloseTranslate.setVisibility(View.VISIBLE);
                imgChooseDetect.setVisibility(View.GONE);
                imgChooseTranslate.setVisibility(View.GONE);
                mLayoutLanguage.setVisibility(View.VISIBLE);

                break;
            case R.id.img_choose_translate:
                imgCloseDetect.setVisibility(View.VISIBLE);
                imgCloseTranslate.setVisibility(View.VISIBLE);
                imgChooseDetect.setVisibility(View.GONE);
                imgChooseTranslate.setVisibility(View.GONE);
                mLayoutLanguage.setVisibility(View.VISIBLE);
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
