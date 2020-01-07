package com.victoriaserect.greed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //numbers to fill up the map
    final int min = 1;
    final int max = 9;

    //map size
    final int columns = 44; //max 44
    final int rows = 10;

    //the map
    private int[][] map;

    //character's position
    private int meX;
    private int meY;

    //scoring
    private int blocksEaten;
    private double result;

    //available directions to move from the current position
    int leftSteps = -1;
    int rightSteps = -1;
    int upSteps = -1;
    int downSteps = -1;
    boolean leftDirectionIsAvailable;
    boolean rightDirectionIsAvailable;
    boolean upDirectionIsAvailable;
    boolean downDirectionIsAvailable;

    //some android sh!ts
    final String background = "#998BC3";
    List<TextView> textViews = new ArrayList<>();
    TextView gameover, score;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newMap = (Button) findViewById(R.id.btn_newmap);
        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        score = (TextView) findViewById(R.id.result);
        gameover = (TextView) findViewById(R.id.gameover);
        Button up = (Button) findViewById(R.id.button_up_arrow);
        Button down = (Button) findViewById(R.id.button_down_arrow);
        Button left = (Button) findViewById(R.id.button_left_arrow);
        Button right = (Button) findViewById(R.id.button_right_arrow);
        newMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUp();
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDown();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLeft();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRight();
            }
        });

        TextView line_01 = (TextView) findViewById(R.id.text_line_01);
        TextView line_02 = (TextView) findViewById(R.id.text_line_02);
        TextView line_03 = (TextView) findViewById(R.id.text_line_03);
        TextView line_04 = (TextView) findViewById(R.id.text_line_04);
        TextView line_05 = (TextView) findViewById(R.id.text_line_05);
        TextView line_06 = (TextView) findViewById(R.id.text_line_06);
        TextView line_07 = (TextView) findViewById(R.id.text_line_07);
        TextView line_08 = (TextView) findViewById(R.id.text_line_08);
        TextView line_09 = (TextView) findViewById(R.id.text_line_09);
        TextView line_10 = (TextView) findViewById(R.id.text_line_10);

        textViews.add(line_01);
        textViews.add(line_02);
        textViews.add(line_03);
        textViews.add(line_04);
        textViews.add(line_05);
        textViews.add(line_06);
        textViews.add(line_07);
        textViews.add(line_08);
        textViews.add(line_09);
        textViews.add(line_10);

        newGame();
    }


    private void newGame() {
        blocksEaten = 0;
        createNewRandomMatrixMap();
        randomMe();
        newPosition(1);
    }

    private void newPosition(int steps) {
        blocksEaten += steps;
        checkAvailableMoves();
        generateAndroidView();
        progressBar.setProgress((int)(Math.round(result)));

    }

    private void goUp(){
        if (upDirectionIsAvailable) {
            for (int i = 0; i < upSteps; i++) {
                map[meY-i][meX]=-1;
            }
            meY = meY-upSteps;
            newPosition(upSteps);
        }
    }

    private void goDown(){
        if (downDirectionIsAvailable) {
            for (int i = 0; i < downSteps; i++) {
                map[meY+i][meX]=-1;
            }
            meY = meY+downSteps;
            newPosition(downSteps);
        }
    }

    private void goLeft(){
        if (leftDirectionIsAvailable) {
            for (int i = 0; i < leftSteps; i++) {
                map[meY][meX-i]=-1;
            }
            meX= meX-leftSteps;
            newPosition(leftSteps);
        }
    }

    private void goRight(){
        if (rightDirectionIsAvailable) {
            for (int i = 0; i < rightSteps; i++) {
                map[meY][meX+i]=-1;
            }
            meX = meX+rightSteps;
            newPosition(rightSteps);
        }
    }

    private void createNewRandomMatrixMap() {
        gameover.setVisibility(View.INVISIBLE);
        map = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                map[i][j] = new Random().nextInt((max - min) + 1) + min;
            }
        }
    }

    private void randomMe() {
        meX = new Random().nextInt((columns-1) + 1);
        meY = new Random().nextInt((rows-1) + 1);
        map[meY][meX] = 0;
    }

    private void generateAndroidView() {
        updateTexts();
        for (int i = 0; i < map.length; i++) {
            String line = "";
            for (int j = 0; j < map[i].length; j++) {
                if (meY == i && meX == j) {
                    line += "<span style=\"background-color:#000000;\"><font color=" + background + ">".concat("0").concat("</font></span>");
                } else if (map[i][j] == -1) {
                    line += "<span style=\"background-color:#000000;\"><font color=#000000>".concat("0").concat("</font></span>");
                }
                else {
                    line += String.valueOf(map[i][j]);
                }
            }
            textViews.get(i).setText(Html.fromHtml(line));
        }
    }

    private void checkAvailableMoves() {
        // me: [rows][columns];
        // me:  map[meY][meX];
        leftDirectionIsAvailable = false;
        rightDirectionIsAvailable = false;
        upDirectionIsAvailable = false;
        downDirectionIsAvailable = false;

        leftSteps = -1; // character is in the first column, has no number on the left side
        if (meX > 0) { // character is NOT in the first column, has number on the left side
            leftSteps = map[meY][meX-1]; // the number on the left side of the character will be the number of the steps
            if (meX >= leftSteps && leftSteps != -1) {  // we must stay on the map after doing the steps,
                                                        // and let the direction disabled, if the cell next to the character was already visited
                leftDirectionIsAvailable = true;
                for (int i = 1; i <= leftSteps; i++) {  // collision-check:
                    if (map[meY][meX-i] == -1) {        // if we do all the steps,
                        leftDirectionIsAvailable = false;   // every step must be on an unvisited cell!
                    }
                }
            }
        }

        rightSteps = -1; // character is in the last column, has no number on the right side
        if (meX < columns-1) {
            rightSteps = map[meY][meX+1];
            if (meX + rightSteps < columns && rightSteps != -1) {
                rightDirectionIsAvailable = true;
                for (int i = 1; i <= rightSteps; i++) {
                    if (map[meY][meX+i] == -1) {
                        rightDirectionIsAvailable = false;
                    }
                }
            }
        }

        upSteps = -1; // character is in the first row, has no number on the upper side
        if (meY > 0) {
            upSteps = map[meY-1][meX];
            if (meY - upSteps >= 0 && upSteps != -1) {
                upDirectionIsAvailable = true;
                for (int i = 1; i <= upSteps; i++) {
                    if (map[meY-i][meX] == -1) {
                        upDirectionIsAvailable = false;
                    }
                }
            }
        }

        downSteps = -1; // character is in the last row, has no number on the down side
        if (meY < rows-1) {
            downSteps = map[meY+1][meX];
            if (meY + downSteps < rows && downSteps != -1) {
                downDirectionIsAvailable = true;
                for (int i = 1; i <= downSteps; i++) {
                    if (map[meY+i][meX] == -1) {
                        downDirectionIsAvailable = false;
                    }
                }
            }
        }

        if (!leftDirectionIsAvailable && !rightDirectionIsAvailable && !upDirectionIsAvailable && !downDirectionIsAvailable) {
            gameover.setVisibility(View.VISIBLE);
        }
    }

    private void updateTexts() {

        //me.setText("My position: " + meX + "; " + meY);


        String directionsSummary = "leftDirectionIsAvailable: " + leftDirectionIsAvailable + ", steps: " + leftSteps + "\n" +
                "rightDirectionIsAvailable: " + rightDirectionIsAvailable + ", steps: " + rightSteps + "\n" +
                "upDirectionIsAvailable: " + upDirectionIsAvailable + ", steps: " + upSteps + "\n" +
                "downDirectionIsAvailable: " + downDirectionIsAvailable + ", steps: " + downSteps + "\n";
        //up_available.setText(directionsSummary);

        result = ((double)blocksEaten * 100) / ((double)rows * (double)columns);
        String scoreFormatted = new DecimalFormat("#.##").format(result);
        String scoreSummary = "Result: " + scoreFormatted + "% | " + blocksEaten + "/" + rows*columns + " eaten";
        score.setText(scoreSummary);

    }

}
