package com.example.android.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0: yellow token, 1: red token
    int activePlayer = 0;

    //gameState represents the game board .2 means empty spot
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    //all possible winning combinations (8 ways to win in tic-tac-toe)
    int [][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8},{0,3,6}, {1,4,7}, {2,5,8},{0,4,8}, {2,4,6}};
    boolean gameIsActive = true;

    //called when a square in the board is touched. This function animates the red or
    // yellow counter drop in from the top of the screen
    public void dropIn(View view){
        ImageView counterImageView = (ImageView) view;

        //identify the square that was tapped on by getting the tag of the square
        int tappedCounter = Integer.parseInt(counterImageView.getTag().toString());

        //exit early if the tapped square is not empty(2 represents empty square) or if game is un active
        if (gameState[tappedCounter] != 2 || !gameIsActive) return;

        //set the image above the screen
        counterImageView.setTranslationY(-1500);

        //change the state of the game
        gameState[tappedCounter] = activePlayer;

        //if yellow
        if(activePlayer == 0) {
            counterImageView.setImageResource(R.drawable.yellow);
            //change to //reds turn
            activePlayer = 1;
        } else {
            counterImageView.setImageResource(R.drawable.red);
            //change to yellow turn
            activePlayer = 0;
        }

        counterImageView.setRotation(0);
        //animate the token to rotate and drop in
        counterImageView.animate().translationYBy(1500).rotation(180).setDuration(500);

        for(int[] position : winningPositions) {
            if (isWinningPosition(position[0],position[1], position[2])) {
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

    //returns true if the three params passed in are a winning position;
    private boolean isWinningPosition(int x, int y, int z){
        return gameState[x] == gameState[y] && gameState[y] == gameState[z] && gameState[x] != 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
