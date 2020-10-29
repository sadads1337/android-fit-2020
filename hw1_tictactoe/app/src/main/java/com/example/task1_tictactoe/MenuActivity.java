package com.example.task1_tictactoe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MenuActivity extends AppCompatActivity {
    private ImageView mImg1;
    private Bitmap mAvatar1;
    private ImageView mImg2;
    private Bitmap mAvatar2;
    private String mName1, mName2;
    private static final int REQUEST_IMAGE_GET_AVATAR1 = 1;
    private static final int REQUEST_IMAGE_GET_AVATAR2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mImg1 = findViewById(R.id.imageView9);
        mImg2 = findViewById(R.id.imageView11);
        if (savedInstanceState != null) {
            mName1 = savedInstanceState.getString("name1");
            mName2 = savedInstanceState.getString("name2");
            mAvatar1 = savedInstanceState.getParcelable("avatar1");
            if (mAvatar1 == null)
                mImg1.setImageResource(R.mipmap.ic_launcher_round);
            else
                mImg1.setImageBitmap(mAvatar1);
            mAvatar2 = savedInstanceState.getParcelable("avatar2");
            if (mAvatar2 == null)
                mImg2.setImageResource(R.mipmap.ic_launcher_round);
            else
                mImg2.setImageBitmap(mAvatar2);
        }
        else {
            mImg1.setImageResource(R.mipmap.ic_launcher_round);
            mImg2.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    public void startGame(View view) {
        EditText name1 = findViewById(R.id.editTextTextPersonName3);
        EditText name2 = findViewById(R.id.editTextTextPersonName4);
        mName1 = name1.getText().toString();
        mName2 = name2.getText().toString();

        String msg = "";
        if (mName1.trim().isEmpty() || mName2.trim().isEmpty()) {
            msg = getString(R.string.fill_names_error);
        } else if (mName1.length() > 30) {
            msg = getString(R.string.name1_long_error);
        } else if (mName2.length() > 30) {
            msg = getString(R.string.name2_long_error);
        } else if (!msg.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("name1", mName1);
        intent.putExtra("name2", mName2);
        intent.putExtra("avatar1", mAvatar1);
        intent.putExtra("avatar2", mAvatar2);
        StatisticActivity.clearStatistic();
        startActivity(intent);
    }

    public void getStatistics(View view) {
        if (StatisticActivity.isSetStatistic()) {
            Intent intent = new Intent(this, StatisticActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_statistic_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void getImageFromFile(View img) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (img instanceof ImageView) {
            ImageView imageView = (ImageView) img;
            if (imageView.getId() == R.id.imageView9) {
                mImg1 = imageView;
                intent.putExtra("player", 1);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET_AVATAR1);
                }
            } else {
                mImg2 = imageView;
                intent.putExtra("player", 2);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET_AVATAR2);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(fullPhotoUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                if (requestCode == REQUEST_IMAGE_GET_AVATAR1) {
                    mAvatar1 = Bitmap.createScaledBitmap(selectedImage, 48, 48, false);
                    mImg1.setImageBitmap(mAvatar1);
                } else {
                    mAvatar2 = Bitmap.createScaledBitmap(selectedImage, 48, 48, false);
                    mImg2.setImageBitmap(mAvatar2);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("avatar1", mAvatar1);
        outState.putParcelable("avatar2", mAvatar2);
        outState.putString("name1", mName1);
        outState.putString("name2", mName2);

    }
}