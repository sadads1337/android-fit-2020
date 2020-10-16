package ru.nsu.fit.lesson6;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> {

    private static final String LOG_TAG = AppListViewHolder.class.getSimpleName();

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private Context context;

    public static class AppListViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        public AppListViewHolder(View view) {
            super(view);
            rootView = view;
            final TextView textView = rootView.findViewById(R.id.app_package);
            if (textView != null) {
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i(LOG_TAG, "Clicked " + textView.getText());
                        final Intent intent = rootView.getContext().getPackageManager()
                                .getLaunchIntentForPackage(String.valueOf(textView.getText()));
                        rootView.getContext().startActivity(intent);
                    }
                });
            }
        }

        public void clearAnimation()
        {
            rootView.clearAnimation();
        }

        public void applyChanges(PackageInfo packageInfo) {
            final TextView appPackageTextView = rootView.findViewById(R.id.app_package);
            setText(appPackageTextView, packageInfo.packageName);
            try {
                final ImageView iconImageView = rootView.findViewById(R.id.app_icon);
                setIcon(iconImageView, rootView.getContext().getPackageManager()
                        .getApplicationIcon(packageInfo.packageName));

                final TextView appLabelTextView = rootView.findViewById(R.id.app_label);
                setText(appLabelTextView, String.valueOf(rootView.getContext().getPackageManager()
                        .getApplicationLabel(packageInfo.applicationInfo)));

            } catch (PackageManager.NameNotFoundException error) {
                Log.i(LOG_TAG, "Can't find icon: " + error.getMessage());
            }
        }

        private void setText(TextView targetView, String text) {
            if (targetView != null) {
                targetView.setText(text);
            }
        }

        private void setIcon(ImageView targetView, Drawable icon) {
            if (targetView != null) {
                targetView.setImageDrawable(icon);
            }
        }
    }

    private List<PackageInfo> dataSet;

    @NonNull
    @Override
    public AppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menu, parent, false);
        return new AppListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppListViewHolder holder, int position) {
        if (dataSet.size() > position) {
            holder.applyChanges(dataSet.get(position));
        }
        setAnimation(holder.rootView, position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull AppListViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    public AppListAdapter(List<PackageInfo> data, Context parentContext) {
        dataSet = data;
        context = parentContext;
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            final Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
