package com.example.tictactoe.resourceManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class FilesManager {
    private static volatile FilesManager instance;
    private Context context;
    private File internalStorageDir;
    public static FilesManager getInstance() {
        if (instance == null) {
            synchronized (FilesManager .class) {
                if (instance == null) {
                    instance = new FilesManager();
                }
            }
        }
        return instance;
    }
    public void init(Context context){
        this.context = context;
        internalStorageDir = context.getFilesDir();
    }
    private String makeUniqueFileName(String fileName){
        return fileName + "_" + UUID.randomUUID().toString();
    }

    public String save(Bitmap bitmap, String fileName){
        FileOutputStream outputStream = null;
        fileName = makeUniqueFileName(fileName) + ".png";
        File file = null;
        try {
            file = new File(internalStorageDir, fileName);
            outputStream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream != null){
                    outputStream.flush();
                    outputStream.close();
                    MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        assert file != null;
        return file.getAbsolutePath();
    }
    public Bitmap load(String filePath){
        return BitmapFactory.decodeFile(filePath);
    }
}
