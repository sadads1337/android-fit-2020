package com.example.tictactoe.resourceManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.tictactoe.R;
import com.example.tictactoe.game.CellStatus;

public class ResourceManager {
    private static volatile ResourceManager instance;
    private Bitmap cross;
    private Bitmap circle;
    private Bitmap defaultPlayerAvatar;
    private int defaultSize = 165;
    private ResourceManager() {}
    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }
        int width = drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : defaultSize;
        int height = drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : defaultSize;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static ResourceManager getInstance(){
        if(instance == null){
            synchronized (ResourceManager.class){
                if(instance == null){
                    instance = new ResourceManager();
                }
            }
        }
        return instance;
    }

    public void initResources(Context context){
        cross = getBitmapFromVectorDrawable(context, R.drawable.cross);
        circle = getBitmapFromVectorDrawable(context, R.drawable.circle);
        defaultPlayerAvatar = getBitmapFromVectorDrawable(context, R.drawable.ic_default_player_picture);
    }
    public Bitmap getCross(){
        return cross;
    }
    public Bitmap getCircle() {
        return circle;
    }
    public Bitmap getDefaultPlayerAvatar() {
        return defaultPlayerAvatar;
    }
    public int getBiggestDimension(){
        int maxCross = Math.max(cross.getWidth(), cross.getHeight());
        int maxCircle = Math.max(circle.getWidth(), circle.getHeight());
        return Math.max(maxCross, maxCircle);
    }
    public Bitmap getGameBitmap(CellStatus status){
        if(status.equals(CellStatus.CLEAR))
            return null;
        return status.equals(CellStatus.CROSS) ? cross : circle;
    }
}
