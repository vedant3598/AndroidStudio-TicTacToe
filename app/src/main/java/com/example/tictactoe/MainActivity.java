package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Developer: Vedant Shah

    private Button[][] buttons = new Button[3][3];
    private boolean turn_Player1 = true;

    private int squaresUsed;

    private int points_Player1;
    private int points_Player2;

    private TextView textView_Player1;
    private TextView textView_Player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_Player1 = findViewById(R.id.p1);
        textView_Player2 = findViewById(R.id.p2);

        //Assigns Button Array with reference to buttons created in activity_main.xml
        for(int counter1 = 0; counter1 < 3; counter1++){
            for(int counter2 = 0; counter2 < 3; counter2++){
                String button_ID = "button_" + counter1 + counter2;
                int result_ID = getResources().getIdentifier(button_ID, "id", getPackageName());
                buttons[counter1][counter2] = findViewById(result_ID);
                buttons[counter1][counter2].setOnClickListener(this);
            }
        }

        //Resets the game
        Button resetButton = findViewById(R.id.reset_Button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    //Puts a "X" or "O", depending on player's turn; if "X" or "O" is placed and a player clicks on that square, nothing will happen. This method also checks to see if either player has won.

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")){
            return;
        }

        if(turn_Player1){
            ((Button) view).setText("X");
        }else{
            ((Button) view).setText("O");
        }
        squaresUsed++;

        if(checkWin()){
            if(turn_Player1){
                player1Win();
            }
            else
                player2Win();
        }
        else if(squaresUsed == 9){
            drawMatch();
        }
        else{
            turn_Player1 = !turn_Player1;
        }

    }

    //Checks the squares horizontally, vertically, and diagonally to see if either player has won
    private Boolean checkWin(){
        String[][] field = new String[3][3];

        for(int counter1 = 0; counter1 < 3; counter1++){
            for(int counter2 = 0; counter2 < 3; counter2++){
                field[counter1][counter2] = buttons[counter1][counter2].getText().toString();
            }
        }

        for(int counter1 = 0; counter1 < 3; counter1++){
            if(field[counter1][0].equals(field[counter1][1]) && field[counter1][0].equals(field[counter1][2]) && !field[counter1][0].equals("")){
                return true;
            }
        }

        for(int counter1 = 0; counter1 < 3; counter1++){
            if(field[0][counter1].equals(field[1][counter1]) && field[0][counter1].equals(field[2][counter1]) && !field[0][counter1].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    //Updates points, resets board, and lets player 1 know that they have won
    private void player1Win(){
        points_Player1++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    //Updates points, resets board, and lets player 2 know that they have won
    private void player2Win(){
        points_Player2++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    //Resets board and lets both players know that it is a draw
    private void drawMatch(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    //Updates points after a player's win
    private void updatePoints(){
        textView_Player1.setText("Player 1: " + points_Player1);
        textView_Player2.setText("Player 2: " + points_Player2);
    }

    //Resets the board
    private void resetBoard(){
        for(int counter1 = 0; counter1 < 3; counter1++){
            for(int counter2 = 0; counter2 < 3; counter2++){
                buttons[counter1][counter2].setText("");
            }
        }
        squaresUsed = 0;
        turn_Player1 = true;
    }

    //Resets the game
    private void resetGame(){
        points_Player1 = 0;
        points_Player2 = 0;
        updatePoints();
        resetBoard();
    }

    //Saves values when device is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("squaresUsed", squaresUsed);
        outState.putInt("points_Player1", points_Player1);
        outState.putInt("points_Player2", points_Player2);
        outState.putBoolean("turn_Player1", turn_Player1);
    }

    //In order to get the values back into the application, this method acts
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        squaresUsed = savedInstanceState.getInt("squaresUsed");
        points_Player1 = savedInstanceState.getInt("points_Player1");
        points_Player2 = savedInstanceState.getInt("points_Player2");
        turn_Player1 = savedInstanceState.getBoolean("turn_Player1");
    }
}
