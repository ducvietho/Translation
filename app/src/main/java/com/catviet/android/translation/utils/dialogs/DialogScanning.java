package com.catviet.android.translation.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.catviet.android.translation.R;
import com.catviet.android.translation.screen.edit.EditActivity;
import com.catviet.android.translation.utils.Constants;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.util.Arrays;

import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

/**
 * Created by ducvietho on 03/05/2018.
 */

public class DialogScanning {
    private Context mContext;
    private Dialog mDialog;
    private Vision vision;

    public DialogScanning(Context context) {
        mContext = context;
        mDialog = new Dialog(context);

    }

    public void showDialog(Bitmap bitmap) {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_scanning);

        ButterKnife.bind(this, mDialog);
        final Window window = mDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        detectText(bitmap);
        mDialog.show();
    }

    private void detectText(final Bitmap bitmap) {
        Vision.Builder visionBuilder = new Vision.Builder(new NetHttpTransport(), new AndroidJsonFactory(), null);
        visionBuilder.setVisionRequestInitializer(new VisionRequestInitializer(Constants.GOOGLE_VISION_API_KEY));
        vision = visionBuilder.build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] photoData = stream.toByteArray();
                    Image inputImage = new Image();
                    inputImage.encodeContent(photoData);

                    Feature desiredFeature = new Feature();
                    desiredFeature.setType("TEXT_DETECTION");

                    AnnotateImageRequest request = new AnnotateImageRequest();
                    request.setImage(inputImage);
                    request.setFeatures(Arrays.asList(desiredFeature));

                    BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
                    batchRequest.setRequests(Arrays.asList(request));

                    BatchAnnotateImagesResponse batchResponse = vision.images().annotate(batchRequest).execute();

                    final TextAnnotation text = batchResponse.getResponses().get(0).getFullTextAnnotation();

                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mDialog.isShowing()){
                                mContext.startActivity(new EditActivity().getIntent(mContext, text.getText()));
                                ((Activity) mContext).finish();
                            }


                        }
                    });

                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ((Activity)mContext).finish();
            }
        });
    }
    public void cancelDialog(){
        mDialog.dismiss();

    }
}
