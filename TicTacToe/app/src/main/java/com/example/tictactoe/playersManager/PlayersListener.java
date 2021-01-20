package com.example.tictactoe.playersManager;

import java.util.ArrayList;

public interface PlayersListener {
    void notifyPlayersChanged(ArrayList<Player> players);
}
