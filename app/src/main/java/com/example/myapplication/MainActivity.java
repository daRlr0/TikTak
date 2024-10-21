package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();
    }

    private void initializeButtons() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this::onButtonClick);
            }
        }
    }

    private void onButtonClick(View view) {
        Button clickedButton = (Button) view;

        if (!clickedButton.getText().toString().equals("")) {
            return; // Игнорируем, если кнопка уже нажата
        }

        clickedButton.setText(playerXTurn ? "X" : "O");
        roundCount++;

        if (checkForWin()) {
            String winner = playerXTurn ? "X" : "O";
            Toast.makeText(this, winner + " " + getString(winner.equals("X") ? R.string.win_x : R.string.win_o), Toast.LENGTH_SHORT).show();
            resetGame();
        } else if (roundCount == 9) {
            Toast.makeText(this, getString(R.string.draw), Toast.LENGTH_SHORT).show();
            resetGame();
        } else {
            playerXTurn = !playerXTurn; // Меняем игрока
        }
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString()) &&
                    buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString()) &&
                    !buttons[i][0].getText().toString().equals("")) {
                return true; // Горизонтальная проверка
            }
        }

        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().toString().equals(buttons[1][j].getText().toString()) &&
                    buttons[0][j].getText().toString().equals(buttons[2][j].getText().toString()) &&
                    !buttons[0][j].getText().toString().equals("")) {
                return true; // Вертикальная проверка
            }
        }

        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString()) &&
                !buttons[0][0].getText().toString().equals("")) {
            return true; // Проверка диагонали
        }

        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString()) &&
                !buttons[0][2].getText().toString().equals("")) {
            return true; // Проверка диагонали
        }

        return false; // Победитель не найден
    }

    private void resetGame() {
        roundCount = 0;
        playerXTurn = true;
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText(""); // Сбрасываем текст кнопок
            }
        }
    }
}


