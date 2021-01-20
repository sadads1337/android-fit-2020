package com.example.tictactoe.playersManager;

import com.example.tictactoe.database.DatabaseManager;

import java.util.ArrayList;

public class PlayersManager {
    private static volatile PlayersManager instance;
    private ArrayList<PlayersListener> playersListeners = new ArrayList<>();
    private PlayersManager() {
        syncWithDatabase();
    }
    private ArrayList<Player> players = new ArrayList<>();
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean playersFull = false;
    private int cursor = 0;
    public static PlayersManager getInstance() {
        if (instance == null) {
            synchronized (PlayersManager.class) {
                if (instance == null) {
                    instance = new PlayersManager();
                }
            }
        }
        return instance;
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setSecondPlayer(Player player) {
        secondPlayer = player;
    }

    public void setFirstPlayer(Player player) {
        firstPlayer = player;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void dropFirstSecondPlayers(){
        firstPlayer = null;
        secondPlayer = null;
    }

    public boolean hasPlayers() {
        return firstPlayer != null && secondPlayer != null;
    }

    public void addPlayerListener(PlayersListener listener){
        playersListeners.add(listener);
    }

    public void notifyPlayerListeners(){
        for (int i = 0; i < playersListeners.size(); i++) {
            if(playersListeners.get(i) != null)
                playersListeners.get(i).notifyPlayersChanged(players);
        }
    }

    public void swapPlayers() {
        Player tmp = secondPlayer;
        secondPlayer = firstPlayer;
        firstPlayer = tmp;
    }

    private void syncWithDatabase(){
        players = DatabaseManager.getInstance().getAllPlayers();
        notifyPlayerListeners();
    }

    public void setPlayer(Player player){
        for(int i = 0; i < players.size(); ++i){
            if(players.get(i).id == player.id){
                players.set(i, player);
                break;
            }
        }
        DatabaseManager.getInstance().updatePlayer(player);
        notifyPlayerListeners();
    }

    public void deletePlayer(Player player) {
        players.removeIf(p -> p.id == player.id);
        DatabaseManager.getInstance().deletePlayer(player);
        notifyPlayerListeners();
    }

    public void addPlayer(String name, String avatarPath) {
        Player player = new Player(-1, name, 0, avatarPath);
        player.id = DatabaseManager.getInstance().insertPlayer(player);
        players.add(player);
        notifyPlayerListeners();
    }
}
