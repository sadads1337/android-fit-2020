package ru.brz.tictactoe

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.go_to_menu))
            .setMessage(getString(R.string.quit_assert))

        builder.setPositiveButton(R.string.yes) { dialog, id ->
            run {
                dialog.cancel()
                Navigation.findNavController(this, R.id.fragment).let {
                    it.navigate(it.graph.startDestination)
                }
            }
        }
        builder.create().show()
    }
}