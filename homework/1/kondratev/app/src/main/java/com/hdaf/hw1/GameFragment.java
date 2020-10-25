package com.hdaf.hw1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment implements View.OnClickListener{
    // TODO: 22.10.2020 Удалить старые куски кода
    private Button[][] buttons = new Button[3][3];
    private MainActivity listener;

    //в них я пишу счет
    private TextView t1;
    private TextView t2;

    //картинки-аватарки
    private ImageView iView1;
    private ImageView iView2;

    //для создания интента на получение фото из системы
    private final int Pick_image = 1;
    private int curImage;

    //там спрятано состояние игры и логика
    private static Game game;

    @Override
    public void onClick(View v) {
        if(!game.isGameEnded()) {
            if (!((Button) v).getText().toString().equals("")) {
                return;
            }
            ((Button) v).setText(game.makeTurn());
            String[][] field = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    field[i][j] = buttons[i][j].getText().toString();
                }
            }
            if (game.checkForWin(field)) {
                if (game.isPlayer1Turn()) {
                    player1Wins();
                } else {
                    player2Wins();

                }
            } else if (game.getRoundCount() == 9) {
                draw();
            } else {
                game.setPlayer1Turn(!game.isPlayer1Turn());
            }
        }
        else{
            Toast toast = Toast.makeText(getContext(),
                    getResources().getString(R.string.game_hint), Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    //в случае победы первого игрока
    private void player1Wins() {
        Toast toast = Toast.makeText(getContext(),
                game.getName1()+" "+getResources().getString(R.string.win), Toast.LENGTH_SHORT);
        toast.show();
        game.setWin1(game.getWin1()+1);
        t1.setText(String.valueOf(game.getWin1()));
        game.setGameEnded(true);
        game.setRoundCount(0);
    }
    //в случае победы второго игрока
    private void player2Wins() {
        Toast toast = Toast.makeText(getContext(),
                game.getName2()+" "+getResources().getString(R.string.win), Toast.LENGTH_SHORT);
        toast.show();
        game.setWin2(game.getWin2()+1);
        t2.setText(String.valueOf(game.getWin2()));
        game.setGameEnded(true);
        game.setRoundCount(0);
    }
    //ничья
    private void draw() {
        Toast toast = Toast.makeText(getContext(),
                getResources().getString(R.string.draw), Toast.LENGTH_SHORT);
        toast.show();

    }
    //"стираем" поле
    private void endGame(){
        game.setPlayer1Turn(true);
        game.setRoundCount(0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                buttons[i][j].setText("");
            }
        }
    }



    public GameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);


        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //получаем ссылку на игру
        game = Game.getInstance();
        if(game.getName1() == null) {
            game.setName1(view.getResources().getString(R.string.player1));
        }
        if(game.getName2() == null) {
            game.setName2(view.getResources().getString(R.string.player2));
        }

        //записываю счет
        t1 = view.findViewById(R.id.tv1);
        t2 = view.findViewById(R.id.tv2);
        t1.setText(String.valueOf(game.getWin1()));
        t2.setText(String.valueOf(game.getWin2()));

        //устанавливаю картинки
        iView1 = view.findViewById(R.id.img1);
        iView2 = view.findViewById(R.id.img2);
        if(game.getBm1() != null){
            iView1.setImageBitmap(game.getBm1());
        }
        if(game.getBm2() != null){
            iView2.setImageBitmap(game.getBm2());
        }
        //лисенеры нажатий на картинки
        addImageViewListener(iView1);
        addImageViewListener(iView2);

        //устанавливаем лисенер на все кнопки игрового поля
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", "com.hdaf.hw1");
                buttons[i][j] = view.findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        //кнопка рестарт
        view.findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGame();
                game.setGameEnded(false);
            }
        });

        //это нужно для того, чтобы прятать курсор и клавиатуру после ввода текста
        final EditText et = view.findViewById(R.id.et1);
        addEditTextListeners(et);

        //аналогично со вторым текстовым полем
        final EditText editText1 = view.findViewById(R.id.et2);
        addEditTextListeners(editText1);

        //восстанавливаю игровое поле
        if(game.getField()!=null){
            String[][] field = game.getField();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText(field[i][j]);
                }
            }
        }

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String[][] field = new String[3][3];
        if(buttons!=null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j] != null) {
                        //outState.putString(i+""+j,buttons[i][j].getText().toString());
                        field[i][j] = buttons[i][j].getText().toString();
                        //game.setBlock(i, j, buttons[i][j].getText().toString());
                    }
                    else
                    {
                        return;
                    }
                }
            }
        }
        game.setField(field);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        /*if(savedInstanceState != null) {
            t1.setText(String.valueOf(game.getWin1()));
            t2.setText(String.valueOf(game.getWin2()));
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText(savedInstanceState.getString(String.valueOf(i)+String.valueOf(j)));
                }
            }
            iView1.setImageBitmap(game.getBm1());
            iView2.setImageBitmap(game.getBm2());
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(game.getRoundCount() != 0){
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //outState.putString(i+""+j,buttons[i][j].getText().toString());
                field[i][j] = buttons[i][j].getText().toString();
                //game.setBlock(i, j, buttons[i][j].getText().toString());
            }
        }

        game.setField(field);}

    }

    //слушатели кликов на текстовые поля
    private void addEditTextListeners(final EditText editText){
        editText.setOnKeyListener(new View.OnKeyListener()
                                  {
                                      public boolean onKey(View v, int keyCode, KeyEvent event)
                                      {
                                          if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER))
                                          {
                                              editText.setCursorVisible(false);
                                              editText.clearFocus();
                                              InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                              imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                              if(v.getId() == R.id.et1) {
                                                  game.setName1(editText.getText().toString());
                                              }
                                              if(v.getId() == R.id.et2){
                                                  game.setName2(editText.getText().toString());
                                              }
                                              return true;
                                          }
                                          return false;
                                      }
                                  }
        );
        //по клику на поле возвращаем туда курсор
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setCursorVisible(true);
            }
        });
    }

    //слушатель кликов на картинку
    private void addImageViewListener(final ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                if(view.getId() == R.id.img1){
                    curImage = 1;
                }
                else if(view.getId() == R.id.img2){
                    curImage = 2;
                }
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        if(curImage == 1){
                            iView1.setImageBitmap(selectedImage);
                            game.setBm1(selectedImage);
                        }
                        else if(curImage == 2){
                            iView2.setImageBitmap(selectedImage);
                            game.setBm2(selectedImage);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

}