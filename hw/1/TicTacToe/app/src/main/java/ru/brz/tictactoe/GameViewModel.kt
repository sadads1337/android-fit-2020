package ru.brz.tictactoe

import androidx.lifecycle.*
import ru.brz.tictactoe.model.Player
import ru.brz.tictactoe.model.Square

class GameViewModel() : ViewModel() {
    val player1 = MutableLiveData<Player>()
    val player2 = MutableLiveData<Player>()

    val isEvenMove = MutableLiveData(false)

    val isGameOver = MutableLiveData(false)
    //val winner = MutableLiveData<Player>(null)
    var winner: Player? = null

    private val squares = arrayOf(
        MutableLiveData(Square(0, null)),
        MutableLiveData(Square(1, null)),
        MutableLiveData(Square(2, null)),
        MutableLiveData(Square(3, null)),
        MutableLiveData(Square(4, null)),
        MutableLiveData(Square(5, null)),
        MutableLiveData(Square(6, null)),
        MutableLiveData(Square(7, null)),
        MutableLiveData(Square(8, null)),
    )

    val fieldLiveData = MediatorLiveData<Square>().also { field ->
        squares.forEach { square ->
            field.addSource(square) { field.value = it }
        }
    }

    fun getSquareForeground(position: Int): Int?{
        return squares[position].value?.player?.signId
    }

    fun squarePressed(position: Int) {
        if(isGameOver.value!!){
            return
        }
        if (squares[position].value?.player == null) {
            squares[position].value = Square(
                position,
                if (!isEvenMove.value!!) player1.value else player2.value
            )
            checkGameState()
            isEvenMove.postValue(!isEvenMove.value!!)
        }
    }

    private fun checkGameState() {
        for (i in 0..2){
            println("$i ${i+3} ${i+6}")
            if(squares[i].value?.player != null && squares[i].value?.player == squares[i+3].value?.player && squares[i].value?.player == squares[i+6].value?.player ){
                playerWin(squares[i].value?.player)
                return
            }
            val j = i+(i*2)
            println("$j ${j+1} ${j+2}")
            if(squares[j].value?.player != null && squares[j].value?.player == squares[j+1].value?.player && squares[j].value?.player == squares[j+2].value?.player ){
                playerWin(squares[j].value?.player)
                return
            }
        }
        println("0,4,8")
        if(squares[0].value?.player != null && squares[0].value?.player == squares[4].value?.player && squares[0].value?.player == squares[8].value?.player ){
            playerWin(squares[0].value?.player)
            return
        }
        println("2,4,6")
        if(squares[2].value?.player != null && squares[2].value?.player == squares[4].value?.player && squares[2].value?.player == squares[6].value?.player ){
            playerWin(squares[2].value?.player)
            return
        }
        squares.forEach {
            if(it.value?.player == null){
                return
            }
        }
        drawnGame()
    }

    private fun drawnGame() {
        isEvenMove.value = false
        isGameOver.postValue(true)
        //winner.postValue(null)
        winner = null
    }

    private fun playerWin(player: Player?) {
        isEvenMove.value = false
        isGameOver.postValue(true)
        //winner.postValue(player)

        player?.score = player?.score?.plus(1)!!
        if(player1.value?.equals(player)!!){
            player1.postValue(player)
        }else{
            player2.postValue(player)
        }

        winner = player
    }

    fun clearField() {
        isGameOver.postValue(false)
        for( i in 0..8){
            squares[i].value = Square(i, null)
        }
    }
}