package com.example.game;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class PlayersActivity extends AppCompatActivity{
    Button startGame, upload_1, upload_2;
    EditText editText_player1, editText_player2;
    public static final String PLAYER1_TEXT = "com.example.application.game.PLAYER1_TEXT";
    public static final String PLAYER2_TEXT = "com.example.application.game.PLAYER2_TEXT";
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int IMAGE_PERMISSION_CODE = 1001;
    private ImageView image_player1, image_player2;
    private int userPicToChange;
    private static final int GALLERY_REQUEST = 1;
    Uri imageuri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        editText_player1 = findViewById(R.id.editTextPlayer1);
        editText_player2 = findViewById(R.id.editTextPlayer2);
        image_player1 = (ImageView)findViewById(R.id.imageView_player1);
        image_player2 = (ImageView)findViewById(R.id.imageView_player2);

        upload_1 = findViewById(R.id.button_upload1);
        upload_2 = findViewById(R.id.button_upload2);
        startGame = findViewById(R.id.button_startgame);

        addImageViewListener(upload_1, image_player1);
        addImageViewListener(upload_2, image_player2);



        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });


    }

    @Override public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);
        toSave.putParcelable("bitmap", bitmap);
    }


    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case IMAGE_PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void addImageViewListener (Button button, final ImageView imageView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                userPicToChange = imageView.getId();
                startActivityForResult(intent, GALLERY_REQUEST);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri image = data.getData();
            switch (userPicToChange) {
                case (R.id.imageView_player1):
                    image_player1.setImageURI(image);
                    break;
                case (R.id.imageView_player2):
                    image_player2.setImageURI(image);
                    break;
                default:
                    break;
            }
        }
    }


    public void startGame(){
        String player1 = editText_player1.getText().toString();
        String player2 = editText_player2.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(PLAYER1_TEXT, player1);
        intent.putExtra(PLAYER2_TEXT, player2);

        image_player1.buildDrawingCache();
        Bitmap image_1= image_player1.getDrawingCache();

        image_player2.buildDrawingCache();
        Bitmap image_2= image_player2.getDrawingCache();

        Bundle extras = new Bundle();
        extras.putParcelable("image_1", image_1);
        //extras.putParcelable("image_2", image_2);
        intent.putExtras(extras);

        startActivity(intent);
    }



}
