package com.tuse.dxball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Objects {
    float top,bottom,left,right;
    Canvas canvas = new Canvas();
    Paint paint;
    Point point;
    int x,y;
    int color;

    Objects(float left,float top,float right,float bottom,int color){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        paint = new Paint();
        paint.setColor(color);
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public void setTop(float top) {
        this.top = top;
    }
    
    public void setColor(int c) {
        this.color = c;
    }
    
    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getTop() {
        return top;
    }
    
    public int getColor() {
        return color;
    }

}
