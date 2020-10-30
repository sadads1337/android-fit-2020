package ru.brz.tictactoe

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import ru.brz.tictactoe.databinding.FragmentLobbyBinding
import ru.brz.tictactoe.model.Player
import java.io.IOException


class LobbyFragment : Fragment() {

    private lateinit var binding: FragmentLobbyBinding
    private val IMAGE_REQUEST1 = 1
    private val IMAGE_REQUEST2 = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLobbyBinding.inflate(inflater)

        val model: GameViewModel by activityViewModels()

        binding.lobbyPlayerImage1.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, IMAGE_REQUEST1)
        }
        binding.lobbyPlayerImage2.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, IMAGE_REQUEST2)
        }

        binding.buttonStart.setOnClickListener {
            model.player1.value = Player(
                binding.lobbyPlayerName1.text.toString(),
                binding.lobbyPlayerImage1.drawable,
                R.drawable.ic_cross,0
            )
            model.player2.value = Player(
                binding.lobbyPlayerName2.text.toString(),
                binding.lobbyPlayerImage2.drawable,
                R.drawable.ic_round, 0
            )
            Navigation.findNavController(it).navigate(LobbyFragmentDirections.actionLobbyFragmentToGameFragment())
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            IMAGE_REQUEST1 -> if (resultCode == RESULT_OK) {
                binding.lobbyPlayerImage1.setImageURI(data?.data)
            }
            IMAGE_REQUEST2 -> if (resultCode == RESULT_OK) {
                binding.lobbyPlayerImage2.setImageURI(data?.data)
            }
        }
    }

}