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
    final int width = 3;
    final int height = 3;

    // STATE VARIABLES
    boolean gameOver;

    boolean moleDrawn1;
    boolean moleDrawn2;
    boolean moleDrawn3;

    int secondsLeft;

    int moleInterval1;
    int moleInterval2;
    int moleInterval3;

    int moleTimer1;
    int moleTimer2;
    int moleTimer3;

    int moleCount1;
    int moleCount2;
    int moleCount3;

    // GRID
    ImageView[][] imageGrid;
    int[][] molePosList;

    // UI ELEMENTS
    TextView timerText;
    ImageView mole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameOver = false;

        moleDrawn1 = false;
        moleDrawn2 = false;
        moleDrawn3 = false;

        imageGrid = new ImageView[width][height];
        molePosList = new int[3][2];

        moleInterval1 = (int)(Math.random() * 6) + 3;
        moleInterval2 = (int)(Math.random() * 6) + 3;
        moleInterval3 = (int)(Math.random() * 6) + 3;

        moleTimer1 = moleInterval1;
        moleTimer2 = moleInterval2;
        moleTimer2 = moleInterval2;

        moleCount1 = 0;
        moleCount2 = 0;
        moleCount3 = 0;

        for(int i = 0; i < imageGrid.length; i++) {
            for(int j = 0; j < imageGrid[i].length; j++) {
                String formattedId = "id_image_mole_" + i + "_" + j;
                int imageId = getBaseContext().getResources().getIdentifier(formattedId, "id", getPackageName());

                imageGrid[i][j] = findViewById(imageId);
                imageGrid[i][j].setAlpha(0f);
            }
        }

        molePosList = generatePosArr(width, height);

        timerText = (TextView) findViewById(R.id.id_text_timer);
        mole = (ImageView) findViewById(R.id.id_image_mole_0_0);

        new CountDownTimer(seconds * 1000, timerInterval * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int mole1x = molePosList[0][0];
                int mole1y = molePosList[0][1];

                int mole2x = molePosList[1][0];
                int mole2y = molePosList[1][1];

                int mole3x = molePosList[2][0];
                int mole3y = molePosList[2][1];

                secondsLeft = (int) (millisUntilFinished / 1000);
                updateTimerText(secondsLeft);

                moleTimer1--;
                moleTimer2--;
                moleTimer3--;

                if(moleTimer1 == 0) {
                    if(moleDrawn1)
                        imageGrid[mole1x][mole1y].animate().alpha(0).setDuration(500);
                    else
                        imageGrid[mole1x][mole1y].animate().alpha(1).setDuration(500);

                    moleDrawn1 = !moleDrawn1;
                    moleTimer1 = moleInterval1;
                    moleCount1++;
                }

                if(moleTimer2 == 0) {
                    if(moleDrawn2)
                        imageGrid[mole2x][mole2y].animate().alpha(0).setDuration(500);
                    else
                        imageGrid[mole2x][mole2y].animate().alpha(1).setDuration(500);

                    moleDrawn2 = !moleDrawn2;
                    moleTimer2 = moleInterval2;
                    moleCount2++;
                }

                if(moleTimer3 == 0) {
                    if(moleDrawn3)
                        imageGrid[mole3x][mole3y].animate().alpha(0).setDuration(500);
                    else
                        imageGrid[mole3x][mole3y].animate().alpha(1).setDuration(500);

                    moleDrawn3 = !moleDrawn3;
                    moleTimer3 = moleInterval3;
                    moleCount3++;
                }

                if(moleTimer1 == 0 && moleTimer2 == 0 && moleTimer3 == 0 && !moleDrawn1 && !moleDrawn2 && !moleDrawn3 && moleCount1 >= 2 && moleCount2 >= 2 && moleCount3 >= 2) {
                    molePosList = generatePosArr(width, height);

                    moleInterval1 = (int)(Math.random() * 6) + 3;
                    moleInterval2 = (int)(Math.random() * 6) + 3;
                    moleInterval3 = (int)(Math.random() * 6) + 3;

                    moleTimer1 = moleInterval1;
                    moleTimer2 = moleInterval2;
                    moleTimer2 = moleInterval2;
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

    private int[][] generatePosArr(int width, int height) {
        int[][] molePosList = new int[width][height];

        for(int i = 0; i < molePosList.length; i++) {
            for(int j = 0; j < molePosList[i].length; j++) {
                molePosList[i][j] = (int)(Math.random() * width);
            }
        }

        for(int i = 1; i < molePosList.length; i++) {
            if(molePosList[i-1][0] == molePosList[i][0] && molePosList[i-1][1] == molePosList[i][1]) {
                while (molePosList[i-1][1] == molePosList[i][1]) {
                    molePosList[i][1] = (int)(Math.random() * width);
                }
            }
        }

        return molePosList;
    }
}