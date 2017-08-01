package com.android.sanat.autoboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Sanat on 31/07/17.
 */


public class AutoBoardView extends View implements AutoBoardInterface{

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintText;
    private Paint paintRect;
    private Bitmap bitmap = null;
    private Canvas canvas;
    private String[][] model;
    private Integer[][] modelBitmap;
    private Integer[][] modelRect;
    int n = 5;
    AutoBoardTouchInterface autoBoardTouchInterface;

    public AutoBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintRect = new Paint();
        paintRect.setColor(Color.GRAY);
        paintRect.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRect.setStrokeWidth(3);

        paintText = new Paint();
        paintText.setColor(Color.GREEN);
        paintText.setTextSize(50);

        model = new String[n][n];
        modelBitmap = new Integer[n][n];
        modelRect = new Integer[n][n];

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.canvas = canvas;
        drawBackground(canvas);
        drawGameBoard(canvas);
        drawBoardContents(canvas);

    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);
    }

    private void drawGameBoard(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        for (int i = 1; i < n; i++) {
            canvas.drawLine(0, i * getHeight() / n, getWidth(), i * getHeight() / n, paintLine);
        }
        for (int i = 1; i < n; i++) {
            canvas.drawLine(i * getWidth() / n, 0, i * getWidth() / n, getHeight(), paintLine);
        }
    }

    private void drawBoardContents(Canvas canvas) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(modelRect[i][j] != null && modelRect[i][j]  == 1){
                    canvas.drawRect(4 + (i * getWidth() / n), 4 + (j * getHeight() / n), ((i + 1) * getWidth() / n) - 4, ((j + 1) * (getHeight() / n)) - 4, paintRect);
                    if(modelBitmap[i][j] != null){
                        canvas.drawText("", ((2 * i) + 1) * getWidth() / (2 * n) - 15, ((2 * j) + 1) * getWidth() / (2 * n) + 15, paintText);
                        model[i][j] = null;
                        this.bitmap = BitmapFactory.decodeResource(getResources(), modelBitmap[i][j]);
                        float X = i * getWidth() / n;
                        float Y = j * getHeight() / n;
                        bitmap = Bitmap.createScaledBitmap(bitmap, getWidth()/n - 6, getHeight()/n - 6, false);
                        canvas.drawBitmap(bitmap, X, Y, null);
                    } else if(model[i][j] != null) {
                        canvas.drawText(model[i][j], ((2 * i) + 1) * getWidth() / (2 * n) - 15, ((2 * j) + 1) * getWidth() / (2 * n) + 15, paintText);
                    }
                } else if (modelRect[i][j] != null && modelRect[i][j] == 0){
                    canvas.drawText("", ((2 * i) + 1) * getWidth() / (2 * n) - 15, ((2 * j) + 1) * getWidth() / (2 * n) + 15, paintText);
                    canvas.drawRect(4 + (i * getWidth() / n), 4 + (j * getHeight() / n), ((i + 1) * getWidth() / n) - 4, ((j + 1) * (getHeight() / n)) - 4, paintRect);
                } else {
                    if(modelBitmap[i][j] != null){
                        canvas.drawText("", ((2 * i) + 1) * getWidth() / (2 * n) - 15, ((2 * j) + 1) * getWidth() / (2 * n) + 15, paintText);
                        model[i][j] = null;
                        this.bitmap = BitmapFactory.decodeResource(getResources(), modelBitmap[i][j]);
                        float X = i * getWidth() / n;
                        float Y = j * getHeight() / n;
                        bitmap = Bitmap.createScaledBitmap(bitmap, getWidth()/n - 6, getHeight()/n - 6, false);
                        canvas.drawBitmap(bitmap, X, Y, null);
                    } else if(model[i][j] != null) {
                        canvas.drawText(model[i][j], ((2 * i) + 1) * getWidth() / (2 * n) - 15, ((2 * j) + 1) * getWidth() / (2 * n) + 15, paintText);
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int tX = ((int) event.getX()) / (getWidth() / n);
        int tY = ((int) event.getY()) / (getHeight() / n);

        if(autoBoardTouchInterface != null) {
            autoBoardTouchInterface.onAutoBoardTouch(tX, tY);
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = (w == 0 ? h : (h == 0 ? w : (w < h ? w : h)));
        setMeasuredDimension(d, d);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(bitmap != null){
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    getWidth()/n - 6, getHeight()/n - 6, false);
        }
    }


    /*
     * Auto Board interface Methods
     */

    @Override
    public void setDimensions(int n) {
        this.n = n;
        model = new String[n][n];
        modelBitmap = new Integer[n][n];
        modelRect = new Integer[n][n];
    }

    @Override
    public void setBitmap(int resourceID, int x, int y) {
        modelBitmap[x][y] = resourceID;
    }

    @Override
    public void setLineColor(int color) {
        paintLine = new Paint();
        paintLine.setColor(color);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
    }

    @Override
    public void setBoardBackgroundColor(int color) {
        paintBg = new Paint();
        paintBg.setColor(color);
        paintBg.setStyle(Paint.Style.FILL);
    }

    @Override
    public void drawRect(int color, int x, int y){
        paintRect = new Paint();
        paintRect.setColor(color);
        paintRect.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRect.setStrokeWidth(3);
        modelRect[x][y] = 0;
    }

    @Override
    public void drawOverwritableRect(int color, int x, int y){
        paintRect = new Paint();
        paintRect.setColor(color);
        paintRect.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRect.setStrokeWidth(3);
        modelRect[x][y] = 1;
    }

    @Override
    public void setCellText(String text, int x, int y) {
        this.model[x][y] = text;
    }

    @Override
    public String getCellText(int x, int y) {
        return model[x][y];
    }

    @Override
    public void setCellTextFormat(int textColor, float textSize) {
        paintText = new Paint();
        paintText.setColor(textColor);
        paintText.setTextSize(textSize);
    }

    @Override
    public void clearCell(int x, int y) {
        modelBitmap[x][y]=null;
        model[x][y]=null;
        if(modelRect[x][y]!=null && modelRect[x][y]==0) modelRect[x][y]=null;
    }

    @Override
    public Boolean isCellEmpty(int x, int y) {
        if(model[x][y] == null && modelBitmap[x][y] == null && (modelRect[x][y] == null || modelRect[x][y] == 1)){
            return true;
        }
        return false;
    }

    public void initializeAutoBoardTouch(AutoBoardTouchInterface autoBoardTouchInterface){
        this.autoBoardTouchInterface = autoBoardTouchInterface;
    }
}
