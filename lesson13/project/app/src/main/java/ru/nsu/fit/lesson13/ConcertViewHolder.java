package ru.nsu.fit.lesson13;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ru.nsu.fit.lesson13.db.Concert;

public class ConcertViewHolder extends ViewHolder {
    private final TextView concertId;
    private final TextView concertName;

    public ConcertViewHolder(@NonNull View itemView) {
        super(itemView);
        concertId = itemView.findViewById(R.id.concert_id);
        concertName = itemView.findViewById(R.id.concert_name);
    }

    public void bindTo(Concert concert) {
        concertId.setText(String.valueOf(concert.uid));
        concertName.setText(concert.name);
    }

    public void clear() {
        concertId.setText("");
        concertName.setText("");
    }
}
