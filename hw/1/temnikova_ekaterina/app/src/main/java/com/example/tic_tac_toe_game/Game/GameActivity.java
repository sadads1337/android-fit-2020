package com.example.tic_tac_toe_game.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.tic_tac_toe_game.Menu.MenuActivity;
import com.example.tic_tac_toe_game.R;

public class GameActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private Button[][] buttons = new Button[3][3];
    private StateGame stateGame;

    public GameActivity() {
        stateGame = new StateGame(); // состояние игры
        stateGame.start();  //страрт игры
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tableLayout = findViewById(R.id.tableLayout);
        buildGameField(); // создание игрового поля
    }

    //TODO:динамически добавляет строки и колонки в таблицу (игровое поле)
    private void buildGameField()
    {
        //массив, кот задает размеры таблицы
        Field[][] field = stateGame.getField();

        //строим столько кнопок и строк, каков размер массива field
        for (int i = 0, lenI = field.length; i < lenI; i++ ) {
            TableRow row = new TableRow(this); // создание строки таблицы
            for (int j = 0, lenJ = field[i].length; j < lenJ; j++)
            {
                Button button = new Button(this);
                buttons[i][j] = button;
                button.setOnClickListener(new Listener(i, j)); // установка слушателя, реагирующего на клик по кнопке
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.FILL_PARENT)); // добавление кнопки в строку таблицы
                button.setWidth(160);
                button.setHeight(160);
            }
            tableLayout.addView(row,
                    new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                            TableLayout.LayoutParams.WRAP_CONTENT)); // добавление строки в таблицу
        }
    }

    public class Listener implements View.OnClickListener {
        private int x;
        private int y;

        Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            Button button = (Button) view;
            StateGame g = stateGame;
            Player player = g.getCurrentActivePlayer();
            if (makeTurn(x, y)) {
                button.setText(player.getName());
            }
            Player winner = g.checkWinner();
            if (winner != null) {
                gameOver(winner);
            }
            if (g.isFieldFilled()) {  // в случае, если поле заполнено
                gameOver();
            }
        }
    }

    private void gameOver(Player player) {
        CharSequence text = "Player \"" + player.getName() + "\" won!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        stateGame.reset();
        refresh();
    }

    private void gameOver() {
        CharSequence text = "Draw";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        stateGame.reset();
        refresh();
    }

    private boolean makeTurn(int x, int y) {
        return stateGame.makeTurn(x, y);
    }

    private void refresh() {
        Field[][] field = stateGame.getField();

        for (int i = 0, len = field.length; i < len; i++) {
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                if (field[i][j].getPlayer() == null) {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(field[i][j].getPlayer().getName());
                }
            }
        }
    }

    public void onMenuClick(View view) {
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}