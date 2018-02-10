package com.example.android.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0: yellow, 1: red
    int activePlayer = 0;
    //gameState represents the game board //2 means empty spot
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8},{0,3,6}, {1,4,7}, {2,5,8},{0,4,8}, {2,4,6}};
    boolean gameIsActive = true;

    public void dropIn(View view){
        ImageView counterImageView = (ImageView) view;

        int tappedCounter = Integer.parseInt(counterImageView.getTag().toString());

        //exit early if the tapped square is not empty or if game is un active
        if (gameState[tappedCounter] != 2 || !gameIsActive) return;

        counterImageView.setTranslationY(-1500);

        gameState[tappedCounter] = activePlayer;

        if(activePlayer == 0) {
            counterImageView.setImageResource(R.drawable.yellow);
            activePlayer = 1;
        } else {
            counterImageView.setImageResource(R.drawable.red);
            activePlayer = 0;
        }

        counterImageView.setRotation(0);
        counterImageView.animate().translationYBy(1500).rotation(180).setDuration(500);

        for(int[] winningPosition : winningPositions) {
            if (isWinningPosition(winningPosition[0],winningPosition[1], winningPosition[2])) {
                // some has won make game unactive
                gameIsActive = false;

                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                if(activePlayer == 1) {
                    winnerTextView.setText("Yellow won");
                } else {
                    winnerTextView.setText("Red won");
                }

                playAgainButton.setVisibility(View.VISIBLE);
                winnerTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void playAgain(View view){

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout boardGridLayout = findViewById(R.id.gridLayout);

        for(int i = 0; i < boardGridLayout.getChildCount(); i++) {
            ImageView counterImage = (ImageView) boardGridLayout.getChildAt(i);
            counterImage.setImageDrawable(null);
        }

        for (int i = 0 ; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        //set color back to yellow (not important)
        activePlayer = 0;
        //game becomes active again
        gameIsActive = true;
    }

    private boolean isWinningPosition(int x, int y, int z){
        return gameState[x] == gameState[y] && gameState[y] == gameState[z] && gameState[x] != 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
