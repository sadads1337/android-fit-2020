package ru.brz.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.brz.tictactoe.databinding.FragmentScoresBinding
import ru.brz.tictactoe.model.Player

class ScoresFragment : Fragment() {

    private lateinit var binding: FragmentScoresBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoresBinding.inflate(inflater)

        val gameModel: GameViewModel by activityViewModels()

        val player1 = gameModel.player1.value
        val player2 = gameModel.player2.value

        if(player1 != null || player2 != null){
            binding.scoreName1.text = player1?.name
            binding.scoreName2.text = player2?.name
            binding.score1.text = player1?.score.toString()
            binding.score2.text = player2?.score.toString()
        }else{
            binding.scoreName1.text = "-"
            binding.scoreName2.text = "-"
            binding.score1.text = "0"
            binding.score2.text = "0"
        }

        return binding.root
    }
}