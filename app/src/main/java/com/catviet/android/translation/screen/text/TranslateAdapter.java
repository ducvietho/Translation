package com.catviet.android.translation.screen.text;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.screen.edit.EditActivity;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.OnClickSpeak;
import com.catviet.android.translation.utils.TextToSpeechManager;
import com.catviet.android.translation.utils.customview.TextViewLight;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ducvietho on 26/04/2018.
 */

public class TranslateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Translate> mTranslates;
    private OnClickSpeak<Translate> mOnClickSpeak;
    private int mType;

    public TranslateAdapter(List<Translate> translates, OnClickSpeak<Translate> onClickSpeak,int type) {
        mTranslates = translates;
        mOnClickSpeak = onClickSpeak;
        mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detect, parent, false);
                return new ViewHolderDetect(v);

            case 2:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detecting,parent,false);
                return new ViewHolderDetecting(view1);
            case 3:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_translate,parent,false);
                return new ViewHolderLanguage(view2);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case 0:
                ViewHolderDetect viewHolderDetect = (ViewHolderDetect) holder;
                viewHolderDetect.bind(mTranslates.get(position));
                break;

            case 2:
                ViewHolderDetecting holderDetecting = (ViewHolderDetecting)holder;
                holderDetecting.bind(mTranslates.get(position));
                break;
            case 3:
                ViewHolderLanguage holderLanguage = (ViewHolderLanguage)holder;
                holderLanguage.bind(mTranslates.get(position));
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mTranslates == null ? 0 : mTranslates.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mTranslates.get(position).getType();
    }
    class ViewHolderLanguage extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_infor)
        TextViewLight tvInfor;
        public ViewHolderLanguage(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(Translate translate){
            tvInfor.setText(translate.getToTranslate());
        }
    }
    class ViewHolderDetect extends RecyclerView.ViewHolder {
        @BindView(R.id.img_country)
        CircleImageView imgCountry;
        @BindView(R.id.tvText)
        TextViewLight tvText;
        @BindView(R.id.bt_speak)
        ImageView btSpeak;
        @BindView(R.id.img_country_translate)
        CircleImageView imgCountryTranslate;
        @BindView(R.id.tvTextTranslate)
        TextViewLight tvTextTranslate;
        @BindView(R.id.bt_speak_translate)
        ImageView btSpeakTranslate;
        @BindView(R.id.pro_loading)
        ProgressBar mLoading;
        @BindView(R.id.layout_translate)
        LinearLayout mLayoutTranslate;
        public ViewHolderDetect(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Translate translate) {
            Picasso.with(itemView.getContext()).load(translate.getImage()).into(imgCountry);
            tvText.setText(translate.getText());
            btSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickSpeak.speakText(translate);
                }
            });
            tvText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context
                            .CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied text", translate.getText());
                    Toast.makeText(itemView.getContext(), "Copied text", Toast.LENGTH_LONG).show();
                    clipboard.setPrimaryClip(clip);
                    return true;
                }
            });
            if(translate.getCodeTranslate()==null){
                mLoading.setVisibility(View.VISIBLE);
                mLayoutTranslate.setVisibility(View.GONE);

            }else {
                mLoading.setVisibility(View.GONE);
                mLayoutTranslate.setVisibility(View.VISIBLE);
                Picasso.with(itemView.getContext()).load(translate.getImageTranslate()).into(imgCountryTranslate);
                tvTextTranslate.setText(translate.getTextTranslate());
                btSpeakTranslate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new TextToSpeechManager().init(itemView.getContext(),translate.getCodeTranslate(),translate.getTextTranslate());
                    }
                });
                tvTextTranslate.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context
                                .CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Copied text", translate.getTextTranslate());
                        Toast.makeText(itemView.getContext(), "Copied text", Toast.LENGTH_LONG).show();
                        clipboard.setPrimaryClip(clip);
                        return true;
                    }
                });
            }

        }
    }

    class ViewHolderTranslate extends RecyclerView.ViewHolder {
        @BindView(R.id.img_country)
        ImageView imgCountry;

        @BindView(R.id.tvText)
        TextViewLight tvText;
        @BindView(R.id.bt_speak)
        ImageView btSpeak;
        public ViewHolderTranslate(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Translate translate) {
            Picasso.with(itemView.getContext()).load(translate.getImage()).into(imgCountry);
            tvText.setText(translate.getText());
            btSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickSpeak.speakText(translate);
                }
            });
            tvText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context
                            .CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied text", translate.getText());
                    Toast.makeText(itemView.getContext(), "Copied text", Toast.LENGTH_LONG).show();
                    clipboard.setPrimaryClip(clip);
                    return true;
                }
            });
        }
    }
    class ViewHolderDetecting extends RecyclerView.ViewHolder {
        @BindView(R.id.img_country)
        CircleImageView imgCountry;
        @BindView(R.id.tvText)
        TextViewLight tvText;
        @BindView(R.id.img_detecting)
        ImageView mImageView;

        public ViewHolderDetecting(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Translate translate) {
            imgCountry.setImageResource(translate.getImage());
            tvText.setText(translate.getText());
//            Ion.with(mImageView)
//                    .animateGif(AnimateGifMode.ANIMATE)
//                    .load("file:///android_asset/2034376469_goldballs.gif");

        }
    }

}
