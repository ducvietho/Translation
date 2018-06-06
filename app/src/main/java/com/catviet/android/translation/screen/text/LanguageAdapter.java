package com.catviet.android.translation.screen.text;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catviet.android.translation.R;
import com.catviet.android.translation.data.model.Language;
import com.catviet.android.translation.utils.OnClickItem;
import com.catviet.android.translation.utils.customview.TextViewRegular;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho on 26/04/2018.
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private List<Language> mLanguages;
    private OnClickItem<Language> mOnClickItem;
    private int mType;
    private int selectedPostision = -1;

    public LanguageAdapter(List<Language> languages, OnClickItem<Language> onClickItem, int type,int position) {
        mLanguages = languages;
        mOnClickItem = onClickItem;
        mType = type;
        selectedPostision = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mLanguages.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mLanguages == null ? 0 : mLanguages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_language)
        TextViewRegular mLanguage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Language language, final int position) {
            mLanguage.setText(language.getName());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPostision = position;
                    notifyDataSetChanged();
                    mOnClickItem.onClick(language,mType,position);
                }
            });

            if (position == selectedPostision) {
                mLanguage.setTextColor(itemView.getContext().getResources().getColor(R.color.blue_4));
            } else {
                mLanguage.setTextColor(Color.BLACK);
            }
        }
    }
}
