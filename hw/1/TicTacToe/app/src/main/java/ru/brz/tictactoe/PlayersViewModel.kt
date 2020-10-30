package ru.brz.tictactoe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.brz.tictactoe.model.Player

class PlayersViewModel : ViewModel() {
    val player1 = MutableLiveData<Player>()
    val player2 = MutableLiveData<Player>()


}