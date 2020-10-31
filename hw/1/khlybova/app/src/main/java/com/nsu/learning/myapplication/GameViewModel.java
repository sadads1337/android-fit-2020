package com.nsu.learning.myapplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.learning.myapplication.database.Gamer;

import java.util.List;

import static java.util.Arrays.asList;

public class GameViewModel extends ViewModel {
    private MutableLiveData<List<Gamer>> gamers;
    private MutableLiveData<Game> game;
    //private GamerDao dao = App.getInstance().getDatabase().gamerDao();

    @NonNull
    public LiveData<Game> getGame() {
        if (game == null) {
            game = new MutableLiveData<>();
            game.postValue(new Game());
        }
        return game;
    }

    @NonNull
    public LiveData<List<Gamer>> getGamers() {
        if (gamers == null) {
            gamers = new MutableLiveData<>();
            //gamers.postValue(new ArrayList<>());
            // loadGamers();
            //gamers.postValue(asList(new Gamer("Player1"), new Gamer("Player2")));
        }
        return gamers;
    }

    public GameFieldState setCellValue(int position) {
        Game newGame = game.getValue();
        GameFieldState state = newGame.setCellValue(position);
        game.postValue(newGame);
        return state;
    }

    public boolean canCellBeSet(int position) {
        return getGame().getValue().canCellBeSet(position);
    }


//    private void loadGamers() {
//        gamers.postValue(dao.getAll().getValue());
//    }

    public void addGamer(Gamer gamer) {
        List<Gamer> newGamers = getGamers().getValue();
        newGamers.add(gamer);
        gamers.postValue(newGamers);
    }
}
