package com.example.tic_tac_toe_game.Game;

public class Field {
    private Player player = null;

    public void fill(Player player)
    {
        this.player = player;
    }

    public boolean isFilled()
    {
        return player != null;
    }

    public Player getPlayer()
    {
        return player;
    }
}
