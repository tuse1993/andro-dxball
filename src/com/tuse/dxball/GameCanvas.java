package com.tuse.dxball;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class GameCanvas extends View {
	Paint paint;
    Circle ballObject;
    Bar barObject;
    int BrickColor1, BrickColor2;
	public static boolean gameOver;
    public static boolean newLife;
    public static int life, canvasHeight, canvasWidth;
    float barWidth = 200;
    float brickX = 0, brickY = 200;
    int score = 0;
    float barLeft, barRight, barTop, barBottom;
    float clicked, unClicked;
    int level = 1, row = 0, ballSpeed = 5;
    boolean leftPos, rightPos, start = true;

	ArrayList<Objects> bricks = new ArrayList<Objects>();

    public GameCanvas(Context context) {
        super(context);
        paint = new Paint();
        life = 3;
        gameOver = false;
        newLife = false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        if(start == true){
        	
        	//choosing the brick color............................
            start = false;
            row = 4; 
            BrickColor1 = Color.rgb(0,128,0); 
            
            //adding brick to the object.................................
            for(int j = 0; j < row; j++){
            	int color;
	            for(int i = 0; i < 5 ; i++){
	                if(brickX >= canvas.getWidth()) {
	                    brickX = 0;
	                    brickY += 20;
	                }
	                
	                color = BrickColor1; 
	                bricks.add(new Objects(brickX, brickY, brickX+(canvas.getWidth()/5)-2, brickY+18,color));
	                brickX += canvas.getWidth() / 5;
	            }
            }

            
            //adding the ball....................................
            ballObject = new Circle( canvas.getWidth()/2, canvas.getHeight()-100, Color.GRAY, 22);
            ballObject.setDx(ballSpeed);
            ballObject.setDy(-ballSpeed);
            
            
            //adding the bar.....................................
            barLeft = getWidth() / 2 - (barWidth / 2);
            barTop = getHeight() - 20;
            barRight = getWidth() / 2 + (barWidth / 2);
            barBottom = getHeight();
            barObject = new Bar(barLeft, barTop, barRight, barBottom, Color.WHITE);
        }

        if(bricks.size() <= 0){
        	gameOver = true;
        	newLife = false;
        	start = false;
        	
        }
        
        if(newLife && !start) {
            newLife = false;
            
            if(life == 2 || life == 3){
            	ballObject = new Circle(canvas.getWidth()/2,canvas.getHeight()-50, Color.rgb(106, 38, 165), 20);
            }
            
            else if(life == 1){
            	ballObject = new Circle(canvas.getWidth()/2,canvas.getHeight()-50, Color.rgb(173, 10, 37), 20);
            }
            
            ballObject.setDx(ballSpeed);
            ballObject.setDy(-ballSpeed);
        }
        
        if(gameOver){
            ((MainActivity)getContext()).finish();
        }
        
        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: "+score, 10, 30, paint);

        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        paint.setColor(Color.WHITE);
        canvas.drawText("Life: "+life, 150, 30, paint);
		
		canvas.drawCircle(ballObject.getX(), ballObject.getY(), ballObject.getRadius(), ballObject.getPaint());
		
        canvas.drawRect(barObject.getLeft(), barObject.getTop(), barObject.getRight(), barObject.getBottom(), barObject.getPaint());

        for(int i=0; i < bricks.size(); i++){
            canvas.drawRect(bricks.get(i).getLeft(),bricks.get(i).getTop(),bricks.get(i).getRight(),bricks.get(i).getBottom(),bricks.get(i).getPaint());
        }
        
        this.ballBrickCollision(canvas);
        this.ballBarCollision(canvas);
        this.ballBoundaryCollision(canvas);

        ballObject.move();
        barObject.moveBar(barLeft,barRight);
        this.run();
    }
    

	public void ballBoundaryCollision(Canvas canvas) {
        if((ballObject.getY() - ballObject.getRadius()) >= canvas.getHeight()){
            life -= 1;
            newLife = true;
        }

        if(life == 0){
        	gameOver = true;
        	start = false;
        }
        else{
	        if((ballObject.getX() + ballObject.getRadius()) >= canvas.getWidth() || (ballObject.getX() - ballObject.getRadius()) <= 0){
	        	ballObject.setDx(-ballObject.getDx());
	        }
	        
	        if( (ballObject.getY() - ballObject.getRadius()) <= 0){
	        	ballObject.setDy(-ballObject.getDy());
	        }
        }
    }
    
    public void ballBarCollision(Canvas canvas){
        if(((ballObject.getY() + ballObject.getRadius()) >= barObject.getTop()) && ((ballObject.getY()+ballObject.getRadius()) <= barObject.getBottom()) && ((ballObject.getX()) >= barObject.getLeft()) && ((ballObject.getX()) <= barObject.getRight())) {
        	ballObject.setDy(-(ballObject.getDy()));
        }

    }
    
    public void ballBrickCollision(Canvas canvas){
        for(int i=0; i < bricks.size(); i++) {
            if (((ballObject.getY() - ballObject.getRadius()) <= bricks.get(i).getBottom()) && ((ballObject.getY() + ballObject.getRadius()) >= bricks.get(i).getTop()) && ((ballObject.getX()) >= bricks.get(i).getLeft()) && ((ballObject.getX()) <= bricks.get(i).getRight())) {
            	score += 1;
                bricks.remove(i);
            	ballObject.setDy(-(ballObject.getDy()));
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent e) {	
    	clicked=e.getX();
    	if(clicked>200){
    		barLeft=clicked-195;		
    		barRight = barLeft + barWidth;
    		barObject.moveBar(barLeft,barRight);	
    	}
    	else {
    		barLeft=clicked-5;		
    		barRight = barLeft + barWidth;
    		barObject.moveBar(barLeft,barRight);
		}
    		
    	return true;
    }

    public void run(){
        invalidate();
    }
}
