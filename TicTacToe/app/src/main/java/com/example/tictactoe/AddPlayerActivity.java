package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tictactoe.playersManager.PlayersManager;
import com.example.tictactoe.resourceManager.FilesManager;
import com.example.tictactoe.resourceManager.ResourceManager;
import com.example.tictactoe.utils.Constants;

import java.io.InputStream;

public class AddPlayerActivity extends AppCompatActivity {

    private ImageView playerImageView;
    private EditText playerNameEditText;
    private Button addPlayerButton;
    private Bitmap playerImage = ResourceManager.getInstance().getDefaultPlayerAvatar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        playerImageView = findViewById(R.id.addPlayerImageView);
        playerImageView.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Constants.PICK_IMAGE);
        });

        playerNameEditText = findViewById(R.id.addPlayerNameEditText);

        addPlayerButton = findViewById(R.id.addPlayerCreateButton);
        addPlayerButton.setOnClickListener(v -> {
            String filePath = FilesManager.getInstance().save(playerImage, playerNameEditText.getText().toString());
            PlayersManager.getInstance().addPlayer(playerNameEditText.getText().toString(), filePath);
            Intent intent = new Intent(this, PlayersActivity.class);
            startActivity(intent);
        });
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
                        playerImage = Bitmap.createScaledBitmap(selectedImage, width, (int)((float)width/ratio), false);
                        playerImageView.setImageBitmap(playerImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}