package com.catviet.android.translation.screen.text;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Translate;
import com.catviet.android.translation.utils.OnClickSpeak;
import com.catviet.android.translation.utils.customview.TextViewLight;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 26/04/2018.
 */

public class TranslateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Translate> mTranslates;
    private OnClickSpeak<Translate> mOnClickSpeak;

    public TranslateAdapter(List<Translate> translates, OnClickSpeak<Translate> onClickSpeak) {
        mTranslates = translates;
        mOnClickSpeak = onClickSpeak;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detect, parent, false);
                return new ViewHolderDetect(v);
            case 1:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_translate, parent, false);
                return new ViewHolderTranslate(view);
            case 2:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detecting,parent,false);
                return new ViewHolderDetecting(view1);
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
            case 1:
                ViewHolderTranslate viewHolderTranslate = (ViewHolderTranslate)holder;
                viewHolderTranslate.bind(mTranslates.get(position));
                break;
            case 2:
                ViewHolderDetecting holderDetecting = (ViewHolderDetecting)holder;
                holderDetecting.bind(mTranslates.get(position));
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

    class ViewHolderDetect extends RecyclerView.ViewHolder {
        @BindView(R.id.img_country)
        ImageView imgCountry;
        @BindView(R.id.tvText)
        TextViewLight tvText;
        @BindView(R.id.bt_speak)
        ImageView btSpeak;
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
        }
    }
    class ViewHolderDetecting extends RecyclerView.ViewHolder {
        @BindView(R.id.img_country)
        ImageView imgCountry;
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
