package ru.brz.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import ru.brz.tictactoe.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater)

        val model: GameViewModel by activityViewModels()
        if(model.player1.value == null){
            binding.buttonResume.visibility = View.GONE
        }

        binding.buttonNewGame.setOnClickListener {
            Navigation.findNavController(it).navigate(MenuFragmentDirections.actionMenuFragmentToLobbyFragment())
        }
        binding.buttonResume.setOnClickListener {
            Navigation.findNavController(it).navigate(MenuFragmentDirections.actionMenuFragmentToGameFragment())
        }
        binding.buttonScore.setOnClickListener {
            Navigation.findNavController(it).navigate(MenuFragmentDirections.actionMenuFragmentToScoresFragment())
        }

        return binding.root
    }

}