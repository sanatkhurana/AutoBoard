package com.android.sanat.autoboard;

/**
 * Created by Sanat on 31/07/17.
 */

public interface AutoBoardInterface {

    public void setDimensions(int n);
    public void setBitmap(int resourceID, int x, int y);
    public void setLineColor(int color);
    public void setBoardBackgroundColor(int color);
    public void drawRect(int color, int x, int y);
    public void drawOverwritableRect(int color, int x, int y);
    public void setCellText(String text, int x, int y);
    public String getCellText(int x, int y);
    public void setCellTextFormat(int textColor, float textSize);
    public void clearCell(int x, int y);
    public Boolean isCellEmpty(int x, int y);

}
