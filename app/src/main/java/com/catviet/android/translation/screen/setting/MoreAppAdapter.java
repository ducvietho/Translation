package com.catviet.android.translation.screen.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.catviet.android.translation.R;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.example.vdconfigppclinkadsandroid.notifications.Notification;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ducvietho on 07/05/2018.
 */

public class MoreAppAdapter extends RecyclerView.Adapter<MoreAppAdapter.ViewHolder> {
    private List<Notification> mList;

    public MoreAppAdapter(List<Notification> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_app, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_app)
        CircleImageView mAvatar;
        @BindView(R.id.tv_name)
        TextViewRegular tvName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Notification notification) {
            Picasso.with(itemView.getContext()).load(notification.getAdIconMoreApps()).into(mAvatar);
            tvName.setText(notification.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        PackageManager manager = ((Activity) itemView.getContext()).getPackageManager();
                        Intent i = manager.getLaunchIntentForPackage(notification.getAppId());
                        if(i!=null){
                            i.addCategory(Intent.CATEGORY_LAUNCHER);
                            itemView.getContext().startActivity(i);
                            return;
                        }
                        try {
                            itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + notification.getAppId())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            itemView.getContext().startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=" + notification.getAppId())));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
