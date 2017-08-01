package com.android.sanat.autoboardlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.sanat.autoboard.AutoBoardTouchInterface;
import com.android.sanat.autoboard.AutoBoardView;

public class MainActivity extends AppCompatActivity implements AutoBoardTouchInterface {

    AutoBoardView autoBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoBoardView = (AutoBoardView)findViewById(R.id.gameView);
        autoBoardView.setBoardBackgroundColor(getColor(android.R.color.black));
        autoBoardView.setDimensions(10);
        autoBoardView.initializeAutoBoardTouch(this);
    }

    @Override
    public void onAutoBoardTouch(int tX, int tY) {
        if(tX % 2 == 0){
            if(!autoBoardView.isCellEmpty(tX, tY)) {
                autoBoardView.drawOverwritableRect(getColor(R.color.colorAccent), tX, tY);
                autoBoardView.clearCell(tX, tY);
            } else {
                autoBoardView.setBitmap(R.mipmap.ic_launcher, tX, tY);
                autoBoardView.setBoardBackgroundColor(getColor(android.R.color.black));
                autoBoardView.setLineColor(getColor(android.R.color.white));
            }

        } else {
            if(!autoBoardView.isCellEmpty(tX, tY)){
                autoBoardView.drawRect(getColor(R.color.colorAccent), tX, tY);
            } else {
                autoBoardView.setBoardBackgroundColor(getColor(android.R.color.white));
                autoBoardView.setLineColor(getColor(android.R.color.black));
                autoBoardView.setCellText("1", tX, tY);
                autoBoardView.setCellTextFormat(getColor(android.R.color.tertiary_text_dark), 50);
            }
        }

        autoBoardView.invalidate();
    }
}
