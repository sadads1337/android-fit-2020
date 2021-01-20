package com.example.tictactoe.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tictactoe.GameActivity;
import com.example.tictactoe.LoginActivity;
import com.example.tictactoe.R;
import com.example.tictactoe.game.GameLoop;
import com.example.tictactoe.playersManager.Player;
import com.example.tictactoe.playersManager.PlayersListener;
import com.example.tictactoe.playersManager.PlayersManager;
import com.example.tictactoe.resourceManager.FilesManager;
import com.example.tictactoe.resourceManager.ResourceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.tictactoe.utils.Constants.PLAYER_NUMBER;

public class LoginPlayersAdapter extends RecyclerView.Adapter<LoginPlayersAdapter.LoginPlayersViewHolder> implements PlayersListener {

    private List<Player> playersList = new ArrayList<>();
    private Activity currentActivity;
    private boolean firstPick;

    public void setItems(Collection<Player> players){
        playersList.clear();
        playersList.addAll(players);
        if(!firstPick)
            playersList.removeIf(p-> p.id == PlayersManager.getInstance().getFirstPlayer().id);
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

    public LoginPlayersAdapter(Activity currentActivity, boolean firstPick){
        super();
        this.currentActivity = currentActivity;
        this.firstPick = firstPick;
        PlayersManager.getInstance().addPlayerListener(this);
    }

    @NonNull
    @Override
    public LoginPlayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_login, parent, false);
        return new LoginPlayersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoginPlayersViewHolder holder, int position) {
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

    class LoginPlayersViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatarImageView;
        private TextView nameTextView;
        private TextView scoreTextView;
        public LoginPlayersViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.playerAvatarImageView);
            nameTextView = itemView.findViewById(R.id.playerNameTextView);
            scoreTextView = itemView.findViewById(R.id.playerScoreTextView);
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

            itemView.setOnClickListener(v -> {

                Intent intent;
                if (firstPick) {
                    PlayersManager.getInstance().setFirstPlayer(player);
                    intent = new Intent(currentActivity, LoginActivity.class);
                    intent.putExtra(PLAYER_NUMBER, 2);
                } else {
                    PlayersManager.getInstance().setSecondPlayer(player);
                    GameLoop.getInstance().setNeedsRestart(true);
                    intent = new Intent(currentActivity, GameActivity.class);
                }
                currentActivity.startActivity(intent);
            });
        }
    }
}
