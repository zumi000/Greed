package com.victoriaserect.greed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final int min = 1;
    final int max = 9;
    final int columns = 45;
    final int rows = 10;
    private int[][] map;
    private int meX;
    private int meY;
    final String background = "#998BC3";
    List<TextView> textViews = new ArrayList<>();
    TextView me, up_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        me = (TextView) findViewById(R.id.text_me);
        up_available = (TextView) findViewById(R.id.up_available);
        Button newMap = (Button) findViewById(R.id.btn_newmap);
        newMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMap();
                randomMe();
                //printMap();
                checkAvailableMoves();

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

        createMap();
        randomMe();
//printMap();
        checkAvailableMoves();
    }


    private void createMap() {
        map = new int[rows][columns];
        System.out.println("CREATING MAP AND MATRIX:");
        for (int i = 0; i < rows; i++) {
            String line = "";
            for (int j = 0; j < columns; j++) {
                map[i][j] = new Random().nextInt((max - min) + 1) + min;
                line += String.valueOf(map[i][j]);
            }
            textViews.get(i).setText(line);
        }
    }

    private void randomMe() {
        meX = new Random().nextInt((columns-1) + 1);
        meY = new Random().nextInt((rows-1) + 1);
        me.setText("My position: " + meX + "; " + meY);
        map[meY][meX] = 0;
        String origiLine = textViews.get(meY).getText().toString();
        textViews.get(meY).setText(Html.fromHtml(
                origiLine.substring(0, meX).concat("<span style=\"background-color:#000000;\"><font color=" + background + ">").concat("0").concat("</font></span>").concat(origiLine.substring(meX + 1))
        ));
    }

    private void printMap () {
        System.out.println("PRINT MAP FROM MATRIX:");
        for (int i = 0; i < map.length; i++) {
            String line = "";
            for (int j = 0; j < map[i].length; j++) {
                line += String.valueOf(map[i][j]);
            }
            System.out.println(line);
        }
    }

    private void setMeInPosition() {}
    private void eraseBehindBe() {}
    private void checkAvailableMoves() {
        // me: [rows][columns];
        // me:  map[meY][meX];
        boolean leftDirectionIsAvailable = false;
        boolean rightDirectionIsAvailable = false;
        boolean upDirectionIsAvailable = false;
        boolean downDirectionIsAvailable = false;
        int leftSteps = -1; // character is in the first column, has no number on the left side
        if (meX > 0) {
            leftSteps = map[meY][meX-1];
            if (meX >= leftSteps) {
                leftDirectionIsAvailable = true;
            }
        }
        int rightSteps = -1; // character is in the last column, has no number on the right side
        if (meX < columns-1) {
            rightSteps = map[meY][meX+1];
            if (meX + rightSteps < columns) {
                rightDirectionIsAvailable = true;
            }
        }
        int upSteps = -1; // character is in the first row, has no number on the upper side
        if (meY > 0) {
            upSteps = map[meY-1][meX];
            if (meY - upSteps >= 0) {
                upDirectionIsAvailable = true;
            }
        }
        int downSteps = -1; // character is in the last row, has no number on the down side
        if (meY < rows-1) {
            downSteps = map[meY+1][meX];
            if (meY + downSteps < rows) {
                downDirectionIsAvailable = true;
            }
        }
        String directionsSummary = "leftDirectionIsAvailable: " + leftDirectionIsAvailable + ", steps: " + leftSteps + "\n" +
                "rightDirectionIsAvailable: " + rightDirectionIsAvailable + ", steps: " + rightSteps + "\n" +
                "upDirectionIsAvailable: " + upDirectionIsAvailable + ", steps: " + upSteps + "\n" +
                "downDirectionIsAvailable: " + downDirectionIsAvailable + ", steps: " + downSteps + "\n";
        System.out.println(directionsSummary);
        up_available.setText(directionsSummary);
    }
    private void go(String destination, int distance) {}

}
