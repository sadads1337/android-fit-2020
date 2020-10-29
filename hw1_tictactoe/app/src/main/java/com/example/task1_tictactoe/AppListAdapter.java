package com.example.task1_tictactoe;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private int lastPosition = -1;
    private Context context;

    public static class AppListViewHolder extends RecyclerView.ViewHolder {
        private View rootView;

        public AppListViewHolder(View view) {
            super(view);
            rootView = view;
        }

        public void clearAnimation() {
            rootView.clearAnimation();
        }

        public void applyChanges(String roundInfo) {
            final TextView appPackageTextView = rootView.findViewById(R.id.round);
            setText(appPackageTextView, roundInfo);
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

    private List<String> dataSet;

    @NonNull
    @Override
    public AppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_statistic, parent, false);
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

    public AppListAdapter(List<String> data, Context parentContext) {
        dataSet = data;
        context = parentContext;
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            final Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
