package com.example.tictactoe.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoe.R;
import com.example.tictactoe.playersManager.Player;
import com.example.tictactoe.playersManager.PlayersListener;
import com.example.tictactoe.playersManager.PlayersManager;
import com.example.tictactoe.resourceManager.FilesManager;
import com.example.tictactoe.resourceManager.ResourceManager;
import com.example.tictactoe.utils.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayersListAdapter extends RecyclerView.Adapter<PlayersListAdapter.PlayersListViewHolder> implements PlayersListener {

    private List<Player> playersList = new ArrayList<>();
    private Activity currentActivity;
    public Player editPlayer;

    public void setItems(Collection<Player> players){
        playersList.clear();
        playersList.addAll(players);
        notifyDataSetChanged();
    }

    public void addItem(Player player){
        playersList.add(player);
        notifyDataSetChanged();
    }

    public void clearItems(){
        playersList.clear();
        notifyDataSetChanged();
    }

    public PlayersListAdapter(Activity currentActivity){
        super();
        this.currentActivity = currentActivity;
        PlayersManager.getInstance().addPlayerListener(this);
    }

    @NonNull
    @Override
    public PlayersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player, parent, false);
        return new PlayersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersListViewHolder holder, int position) {
        holder.bind(playersList.get(position));
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    @Override
    public void notifyPlayersChanged(ArrayList<Player> players) {
        setItems(players);
    }

    class PlayersListViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatarImageView;
        private TextView nameTextView;
        private TextView scoreTextView;
        private ImageButton deleteButton;
        public PlayersListViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.playerAvatarImageView);
            nameTextView = itemView.findViewById(R.id.playerNameTextView);
            scoreTextView = itemView.findViewById(R.id.playerScoreTextView);
            deleteButton = itemView.findViewById(R.id.deletePlayerButton);
        }
        public void bind(Player player){
            Bitmap avatar = ResourceManager.getInstance().getDefaultPlayerAvatar();
            avatarImageView.setPadding(0, 0, 0, 0);
            if(player.avatarPath != null) {
                Bitmap avatarTmp = FilesManager.getInstance().load(player.avatarPath);
                if(avatarTmp != null) {
                    avatar = avatarTmp;
                    avatarImageView.setPadding(40, 40, 0, 40);
                }
            }
            avatarImageView.setImageBitmap(avatar);
            avatarImageView.setScaleType(ImageView.ScaleType.MATRIX);
            avatarImageView.setMaxWidth(150);
            avatarImageView.setMaxHeight(150);

            nameTextView.setText(player.name);
            scoreTextView.setText(String.valueOf(player.score));

            avatarImageView.setOnClickListener(v -> {
                editPlayer = player;
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                currentActivity.startActivityForResult(photoPickerIntent, Constants.PICK_IMAGE);
            });

            nameTextView.setOnClickListener(v -> {
                AlertDialog.Builder dialog = new AlertDialog.Builder(currentActivity)
                        .setMessage(currentActivity.getString(R.string.EditingWithSpace) + player.name + currentActivity.getString(R.string.SpaceWithName))
                        .setCancelable(true);
                final EditText input = new EditText(dialog.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                dialog.setView(input).setPositiveButton(R.string.ok, (dialog1, which) -> {
                    player.name = input.getText().toString();
                    nameTextView.setText(player.name);
                    PlayersManager.getInstance().setPlayer(player);
                }).create().show();
            });

            deleteButton.setOnClickListener(v -> {
                AlertDialog dialog = new AlertDialog.Builder(currentActivity)
                        .setMessage(currentActivity.getString(R.string.deleteWarning) + player.name + "?")
                        .setCancelable(true)
                        .setPositiveButton(R.string.ok, (dialog1, which) -> {
                            PlayersManager.getInstance().deletePlayer(player);
                        })
                        .create();
                dialog.show();
            });
        }
    }
}
