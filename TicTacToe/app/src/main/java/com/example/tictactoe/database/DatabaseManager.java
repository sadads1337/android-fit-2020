package com.example.tictactoe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tictactoe.playersManager.Player;

import java.util.ArrayList;

public class DatabaseManager {
    private static volatile DatabaseManager instance;

    private DbHelper dbHelper;
    private final String dbName = "TicTacToeDb";
    private final String playersTableName = "players";
    private final String idColumnName = "id";
    private final String nameColumnName = "name";
    private final String scoreColumnName = "score";
    private final String avatarPathColumnName = "avatarPath";

    private DatabaseManager(){ }

    private class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context){
            super(context, dbName, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + playersTableName +" ("
                    + idColumnName + " integer primary key autoincrement,"
                    + nameColumnName +" text,"
                    + scoreColumnName + " integer,"
                    + avatarPathColumnName + " text"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager .class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }
    public void initDatabase(Context context){
        dbHelper = new DbHelper(context);
    }

    public int insertPlayer(Player player){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        cv.put(nameColumnName, player.name);
        cv.put(scoreColumnName, player.score);
        cv.put(avatarPathColumnName, player.avatarPath);

        long id = db.insert(playersTableName, null , cv);

        dbHelper.close();

        return (int)id;
    }

    public void updatePlayer(Player player){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        cv.put(nameColumnName, player.name);
        cv.put(scoreColumnName, player.score);
        cv.put(avatarPathColumnName, player.avatarPath);

        int updCount = db.update(playersTableName, cv, idColumnName + " = ?",
                new String[] { String.valueOf(player.id) });

        dbHelper.close();
    }

    public void deletePlayer(Player player){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(playersTableName, idColumnName + " = " +  String.valueOf(player.id), null);

        dbHelper.close();
    }

    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> players = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(playersTableName, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            int idColumnIdx = cursor.getColumnIndex(idColumnName);
            int nameColumnIdx = cursor.getColumnIndex(nameColumnName);
            int scoreColumnIdx = cursor.getColumnIndex(scoreColumnName);
            int avatarPathColumnIdx = cursor.getColumnIndex(avatarPathColumnName);

            do{
                Player player = new Player(cursor.getInt(idColumnIdx),
                        cursor.getString(nameColumnIdx),
                        cursor.getInt(scoreColumnIdx),
                        cursor.getString(avatarPathColumnIdx));
                players.add(player);
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        return players;
    }
}
