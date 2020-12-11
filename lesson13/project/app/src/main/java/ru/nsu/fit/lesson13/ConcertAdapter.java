package ru.nsu.fit.lesson13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import ru.nsu.fit.lesson13.db.Concert;

public class ConcertAdapter extends PagedListAdapter<Concert, ConcertViewHolder> {
    public ConcertAdapter() {
        super(new DiffUtil.ItemCallback<Concert>() {
            @Override
            public boolean areItemsTheSame(@NonNull Concert oldItem, @NonNull Concert newItem) {
                return oldItem.uid == newItem.uid;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Concert oldItem, @NonNull Concert newItem) {
                return oldItem.name.equals(newItem.name);
            }
        });
    }

    @NonNull
    @Override
    public ConcertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.concert, parent, false);
        return new ConcertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertViewHolder holder, int position) {
        final Concert concert = getItem(position);
        if (concert != null) {
            holder.bindTo(concert);
        } else {
            holder.clear();
        }
    }
}
