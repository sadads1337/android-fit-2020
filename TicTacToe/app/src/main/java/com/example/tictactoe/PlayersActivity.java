package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.tictactoe.adapters.PlayersListAdapter;
import com.example.tictactoe.playersManager.PlayersManager;
import com.example.tictactoe.resourceManager.FilesManager;
import com.example.tictactoe.utils.Constants;

import java.io.InputStream;

public class PlayersActivity extends AppCompatActivity {

    private PlayersListAdapter playersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        initRecyclerView();
        ImageButton addPlayerButton = findViewById(R.id.addPlayerButton);
        addPlayerButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayersActivity.this, AddPlayerActivity.class);
            startActivity(intent);
        });
    }

    private void initRecyclerView() {
        RecyclerView playersView = findViewById(R.id.playersView);
        playersView.setLayoutManager(new LinearLayoutManager(this));
        playersListAdapter = new PlayersListAdapter(this);
        playersView.setAdapter(playersListAdapter);
        playersListAdapter.setItems(PlayersManager.getInstance().getPlayers());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case Constants.PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        float ratio = (float) selectedImage.getWidth() / selectedImage.getHeight();
                        int width = 250;
                        Bitmap playerImage = Bitmap.createScaledBitmap(selectedImage, width, (int)((float)width/ratio), false);
                        playersListAdapter.editPlayer.avatarPath = FilesManager.getInstance().save(playerImage, playersListAdapter.editPlayer.name);
                        PlayersManager.getInstance().setPlayer(playersListAdapter.editPlayer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}