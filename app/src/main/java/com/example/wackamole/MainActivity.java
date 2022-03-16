package com.example.wackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // CONSTANTS
    final int seconds = 60;
    final int timerInterval = 1;
    final int width = 4;
    final int height = 4;

    // STATE VARIABLES
    boolean gameOver;
    boolean moleDrawn;

    boolean moleDrawn1;
    boolean moleDrawn2;
    boolean moleDrawn3;

    int secondsLeft;
    int moleInterval1;
    int moleInterval2;
    int moleInterval3;

    // GRID
    boolean[][] grid;
    ImageView[][] imageGrid;

    // UI ELEMENTS
    TextView timerText;
    ImageView mole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameOver = false;
        moleDrawn = true;

        moleDrawn1 = true;
        moleDrawn2 = true;
        moleDrawn3 = true;

        grid = new boolean[width][height];
        imageGrid = new ImageView[width][height];

        moleInterval1 = (int)(Math.random() * 6) + 3;
        moleInterval2 = (int)(Math.random() * 6) + 3;
        moleInterval3 = (int)(Math.random() * 6) + 3;

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                grid[i][j] = false;
            }
        }

        for(int i = 0; i < imageGrid.length; i++) {
            for(int j = 0; j < imageGrid[i].length; j++) {
                String formattedId = "id_image_mole_" + i + "_" + j;
                int imageId = getBaseContext().getResources().getIdentifier(formattedId, "id", getPackageName());

                imageGrid[i][j] = findViewById(imageId);
            }
        }

        timerText = (TextView) findViewById(R.id.id_text_timer);
        mole = (ImageView) findViewById(R.id.id_image_mole_0_0);

        new CountDownTimer(seconds * 1000, timerInterval * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsLeft = (int) (millisUntilFinished / 1000);
                updateTimerText(secondsLeft);

                moleInterval1--;
                moleInterval2--;
                moleInterval3--;

                if(moleInterval1 == 0) {
                    if(moleDrawn1)
                        imageGrid[1][0].animate().alpha(0).setDuration(500);
                    else
                        imageGrid[1][0].animate().alpha(1).setDuration(500);

                    moleDrawn1 = !moleDrawn1;
                    moleInterval1 = (int)(Math.random() * 6) + 3;
                }

                if(moleInterval2 == 0) {
                    if(moleDrawn2)
                        imageGrid[1][1].animate().alpha(0).setDuration(500);
                    else
                        imageGrid[1][1].animate().alpha(1).setDuration(500);

                    moleDrawn2 = !moleDrawn2;
                    moleInterval2 = (int)(Math.random() * 6) + 3;
                }

                if(moleInterval3 == 0) {
                    if(moleDrawn3)
                        imageGrid[1][2].animate().alpha(0).setDuration(500);
                    else
                        imageGrid[1][2].animate().alpha(1).setDuration(500);

                    moleDrawn3 = !moleDrawn3;
                    moleInterval3 = (int)(Math.random() * 6) + 3;
                }

            }

            @Override
            public void onFinish() {
                secondsLeft = 0;
                updateTimerText(secondsLeft);

                gameOver = true;
            }
        }.start();
    }

    private void updateTimerText(int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timerText.setText(String.valueOf(time));
            }
        });
    }
}