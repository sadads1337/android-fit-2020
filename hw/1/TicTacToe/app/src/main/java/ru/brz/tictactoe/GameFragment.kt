package ru.brz.tictactoe

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ru.brz.tictactoe.databinding.FragmentGameBinding
import ru.brz.tictactoe.model.Player


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater)

        val gameModel: GameViewModel by activityViewModels()

        gameModel.fieldLiveData.observe(viewLifecycleOwner){
            when (it.position){
                0 -> binding.button0.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                1 -> binding.button1.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                2 -> binding.button2.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                3 -> binding.button3.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                4 -> binding.button4.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                5 -> binding.button5.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                6 -> binding.button6.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                7 -> binding.button7.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
                8 -> binding.button8.foreground = it.player?.signId?.let { id ->
                    ResourcesCompat.getDrawable(resources, id, activity?.theme)}
            }
        }

        gameModel.isEvenMove.observe(viewLifecycleOwner){
            if(it){
                binding.gameTurn1?.visibility = View.INVISIBLE
                binding.gameTurn2?.visibility = View.VISIBLE
            }else{
                binding.gameTurn1?.visibility = View.VISIBLE
                binding.gameTurn2?.visibility = View.INVISIBLE
            }
        }

        gameModel.isGameOver.observe(viewLifecycleOwner){
            if(it) {
                showGameOverDialog(gameModel.winner, gameModel, context)
            }
        }

        gameModel.player1.observe(viewLifecycleOwner){
            if(it!=null) {
                binding.gamePlayerWins1?.text = it.score.toString()
                binding.gameScore?.text = it.score.toString()+" : "+gameModel.player2.value?.score.toString()
            }
        }
        gameModel.player2.observe(viewLifecycleOwner){
            if(it!=null) {
                binding.gamePlayerWins2?.text = it.score.toString()
                binding.gameScore?.text = gameModel.player1.value?.score.toString()+" : "+it.score.toString()
            }
        }

        binding.gamePlayerIcon1?.setImageDrawable(gameModel.player1.value?.iconId)
        binding.gamePlayerIcon2?.setImageDrawable(gameModel.player2.value?.iconId)
        binding.gamePlayerName1?.text = gameModel.player1.value?.name
        binding.gamePlayerName2?.text = gameModel.player2.value?.name
        gameModel.player1.value?.signId?.let { binding.gamePlayerSign1?.setImageResource(it) }
        gameModel.player2.value?.signId?.let { binding.gamePlayerSign2?.setImageResource(it) }


        binding.button0.let {
            it.setOnClickListener {
                gameModel.squarePressed(0)
            }
            it.foreground = gameModel.getSquareForeground(0)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }
        binding.button1.let {
            it.setOnClickListener {
                gameModel.squarePressed(1)
            }
            it.foreground = gameModel.getSquareForeground(1)?.let { id ->
                ResourcesCompat.getDrawable(
                    resources,
                    id, activity?.theme
                )
            }
        }
        binding.button2.let {
            it.setOnClickListener {
                gameModel.squarePressed(2)
            }
            it.foreground = gameModel.getSquareForeground(2)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }
        binding.button3.let {
            it.setOnClickListener {
                gameModel.squarePressed(3)
            }
            it.foreground = gameModel.getSquareForeground(3)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }
        binding.button4.let {
            it.setOnClickListener {
                gameModel.squarePressed(4)
            }
            it.foreground = gameModel.getSquareForeground(4)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }
        binding.button5.let {
            it.setOnClickListener {
                gameModel.squarePressed(5)
            }
            it.foreground = gameModel.getSquareForeground(5)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }
        binding.button6.let {
            it.setOnClickListener {
                gameModel.squarePressed(6)
            }
            it.foreground = gameModel.getSquareForeground(6)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }
        binding.button7.let {
            it.setOnClickListener {
                gameModel.squarePressed(7)
            }
            it.foreground = gameModel.getSquareForeground(7)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }
        binding.button8.let {
            it.setOnClickListener {
                gameModel.squarePressed(8)
            }
            it.foreground = gameModel.getSquareForeground(8)?.let {id ->
                ResourcesCompat.getDrawable(resources,
                    id, activity?.theme)
            }
        }

        return binding.root
    }

    private fun showGameOverDialog(winner: Player?, gameViewModel: GameViewModel, context: Context?) {
        val builder = AlertDialog.Builder(context)
        if (winner == null) {
            builder.setTitle(getString(R.string.draw))
            //.setMessage("Покормите кота!")
        } else {
            builder.setTitle(winner.name + " " + getString(R.string.player_win))
                //.setMessage("Покормите кота!")
                .setIcon(winner.iconId)
        }
        builder.setPositiveButton(getString(R.string.restart)) { dialog, id ->
            run {
                dialog.cancel()
                gameViewModel.clearField()
            }
        }
        builder.create().show()
    }

}