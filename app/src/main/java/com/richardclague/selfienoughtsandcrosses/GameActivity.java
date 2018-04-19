package com.richardclague.selfienoughtsandcrosses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.richardclague.selfienoughtsandcrosses.R.layout.activity_game;

public class GameActivity extends AppCompatActivity {

    int activePlayer = 0;

    boolean gameIsActive = true;

    int playerOneScore;
    int playerTwoScore;

    ImageView playerOneMatchScoreImageView;
    ImageView playerTwoMatchScoreImageView;

    ImageView playAgainButton;

    TextView matchScoreTextView;

    // 2 means unplayed

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn (View view){
        ImageView counter = (ImageView) view;

        System.out.println(counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter]==2 && gameIsActive){

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if(activePlayer == 0) {

                if(MainActivity.playerOneBitmap == null){
                    counter.setImageResource(R.drawable.huggingface);
                } else {

                    counter.setImageBitmap(MainActivity.playerOneBitmap);

                }


                activePlayer = 1;
                playerTwoMatchScoreImageView.setAlpha(255);
                playerOneMatchScoreImageView.setAlpha(75);


            } else {

                if(MainActivity.playerTwoBitmap == null){

                    counter.setImageResource(R.drawable.nerdface);

                } else {

                    counter.setImageBitmap(MainActivity.playerTwoBitmap);

                }


                activePlayer = 0;
                playerOneMatchScoreImageView.setAlpha(255);
                playerTwoMatchScoreImageView.setAlpha(75);

            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(500);

            for (int[] winningPosition: winningPositions){
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    // Someone has won

                    gameIsActive = false;

                    String winner = MainActivity.playerTwoName;

                    playerTwoScore++;

                    if (gameState[winningPosition[0]] == 0) {

                        winner = MainActivity.playerOneName;
                        playerTwoScore--;
                        playerOneScore++;

                    }

                    matchScoreTextView.setText((Integer.toString(playerOneScore)) + " - " + (Integer.toString(playerTwoScore)));

                    Toast.makeText(GameActivity.this, winner + " has won!",
                            Toast.LENGTH_SHORT).show();

                    playAgainButton = findViewById(R.id.playAgainButton);
                    playAgainButton.setVisibility(view.VISIBLE);

                } else {

                    boolean gameIsOver = true;

                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver) {

                        playAgainButton = findViewById(R.id.playAgainButton);
                        playAgainButton.setVisibility(view.VISIBLE);

//                        Toast.makeText(GameActivity.this, "It's a draw!",
//                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }

    }

    public void playAgain(View view) {

        gameIsActive = true;

        playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(view.INVISIBLE);

        int activePlayer = 0;

        for (int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i=0; i< gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playerOneMatchScoreImageView = findViewById(R.id.playerOneMatchScoreImageView);

        if(MainActivity.playerOneBitmap == null){

            playerOneMatchScoreImageView.setImageResource(R.drawable.huggingface);

        } else {

            playerOneMatchScoreImageView.setImageBitmap(MainActivity.playerOneBitmap);

        }

        playerTwoMatchScoreImageView = findViewById(R.id.playerTwoMatchScoreImageView);

        if(MainActivity.playerTwoBitmap == null){

            playerTwoMatchScoreImageView.setImageResource(R.drawable.nerdface);

        } else {

            playerTwoMatchScoreImageView.setImageBitmap(MainActivity.playerTwoBitmap);
        }

        playerTwoMatchScoreImageView.setAlpha(75);


        playerOneScore = 0;
        playerTwoScore = 0;

        matchScoreTextView = (TextView) findViewById(R.id.matchScoreTextView);
        matchScoreTextView.setText((Integer.toString(playerOneScore)) + " - " + (Integer.toString(playerTwoScore)));

    }
}
