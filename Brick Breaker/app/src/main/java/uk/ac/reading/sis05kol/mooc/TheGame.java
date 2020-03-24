package uk.ac.reading.sis05kol.mooc;

//Other parts of the android libraries that we use
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Display;
import android.view.MotionEvent;


public class TheGame extends GameThread{

    //Will store the image of a ball
    private Ball mBall;
    //The X and Y position of the ball on the screen
    //The point is the top left corner, not middle, of the ball
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float mBallX = mCanvasWidth;//mCanvasWidth/2;
    private float mBallY = 50;//mCanvasHeight/2;
    //The speed (pixel/second) of the ball in direction X and Y
    private float mBallSpeedX = 0;
    private float mBallSpeedY = 0;


    //Will store the image of the Paddle used to hit the ball
    private Bitmap mPaddle;
    //Paddle's x position. Y will always be the bottom of the screen
    private float mPaddleX = mCanvasWidth / 2;
    private float mPaddleY = 0;
    //The speed (pixel/second) of the paddle in direction X and Y
    private float mPaddleSpeedX = 0;

    // Bricks
    private Brick[] mBricks;

    //Will store the image of the smiley ball (score ball)
    private Bitmap mSmileyBall;
    //The X and Y position of the ball on the screen
    //The point is the top left corner, not middle, of the ball
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float mSmileyBallX = -100;
    private float mSmileyBallY = -100;

    //Will store the image of the smiley ball (score ball)
    private Bitmap mSadBall;
    //The X and Y position of the SadBalls on the screen
    //The point is the top left corner, not middle, of the balls
    //Initially at -100 to avoid them being drawn in 0,0 of the screen
    private float[] mSadBallX = {-100,-100,-100};
    private float[] mSadBallY = new float[3];

    //This will store the min distance allowed between a big ball and the small ball
    //This is used to check collisions
    private float mMinDistanceBetweenBallAndPaddle = 0;


    //This is run before anything else, so we can prepare things here
    public TheGame(GameView gameView) {

        //House keeping
        super(gameView);

        //Prepare the image so we can draw it on the screen (using a canvas)
        mBall = new Ball (BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.ball));

        //Prepare the image of the paddle so we can draw it on the screen (using a canvas)
        mPaddle = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.platform);

        // Idea is to create a row of bricks - Unknown screen size.
        // Set up a number of bricks and decide how many of them to use in setupBeginning()

        int numberOfBricks = 15;
        Bitmap brickImage = BitmapFactory.decodeResource(
                gameView.getContext().getResources(), R.drawable.pink_block);

        mBricks = new Brick[numberOfBricks];

        for (int i = 0; i < mBricks.length; ++i)
            mBricks[i] = new Brick(brickImage);


        //Prepare the image of the SmileyBall so we can draw it on the screen (using a canvas)
        mSmileyBall =  BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.smiley_ball);

        //Prepare the image of the SadBall(s) so we can draw it on the screen (using a canvas)
        mSadBall =  BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.sad_ball);
    }

    //This is run before a new game (also after an old game)
    @Override
    public void setupBeginning() {
        //Initialise speeds
        //mCanvasWidth and mCanvasHeight are declared and managed elsewhere

        mBallSpeedX = mCanvasWidth / 4;
        mBallSpeedY = -(mCanvasHeight / 4);

        //Place Paddle in the middle of the screen
        mPaddleX = mCanvasWidth / 2;
        mPaddleY = mPaddle.getHeight() + mPaddle.getHeight()/4;

        //mBall.Width() and mBall.getHeight() gives us the height and width of the image of the ball
        mBallX = mCanvasWidth / 2;
        //mBall is placed just above the paddle
        mBallY = mCanvasHeight - mPaddle.getHeight() - mBall.getHeight()/2;


        //Place SmileyBall in the top middle of the screen
        mSmileyBallX = mCanvasWidth / 2;
        mSmileyBallY = mSmileyBall.getHeight()/2;

        //Place all SadBalls forming a pyramid underneath the SmileyBall
        mSadBallX[0] = mCanvasWidth / 3;
        mSadBallY[0] = mCanvasHeight / 3;

        mSadBallX[1] = mCanvasWidth - mCanvasWidth / 3;
        mSadBallY[1] = mCanvasHeight / 3;

        mSadBallX[2] = mCanvasWidth / 2;
        mSadBallY[2] = mCanvasHeight / 5;

        //Get the minimum distance between a small ball and a bigball
        //We leave out the square root to limit the calculations of the program
        //Remember to do that when testing the distance as well
        mMinDistanceBetweenBallAndPaddle = (mPaddle.getWidth() / 2 + mBall.getWidth() / 2) * (mPaddle.getWidth() / 2 + mBall.getWidth() / 2);
    }

    @Override
    protected void doDraw(Canvas canvas) {
        //If there isn't a canvas to do nothing
        //It is ok not understanding what is happening here
        if(canvas == null) return;

        //House keeping
        super.doDraw(canvas);

        //canvas.drawBitmap(bitmap, x, y, paint) uses top/left corner of bitmap as 0,0
        //we use 0,0 in the middle of the bitmap, so negate half of the width and height of the ball to draw the ball as expected
        //A paint of null means that we will use the image without any extra features (called Paint)

        //draw the image of the ball using the X and Y of the ball
        //canvas.drawBitmap(mBall, mBallX - mBall.getWidth() / 2, mBallY - mBall.getHeight() / 2, null);

        //Draw Paddle using X of paddle and the bottom of the screen (top/left is 0,0)
        canvas.drawBitmap(mPaddle, mPaddleX - mPaddle.getWidth() / 2, mCanvasHeight - mPaddleY, null);

        //Draw SmileyBall
        canvas.drawBitmap(mSmileyBall, mSmileyBallX - mSmileyBall.getWidth() / 2, mSmileyBallY - mSmileyBall.getHeight() / 2, null);

        //Loop through all SadBall
        for(int i = 0; i < mSadBallX.length; i++) {
            //Draw SadBall in position i
            canvas.drawBitmap(mSadBall, mSadBallX[i] - mSadBall.getWidth() / 2, mSadBallY[i] - mSadBall.getHeight() / 2, null);
        }
    }

    public boolean hitCheck (float x, float y)  {
        if(mPaddleX < x && x < (mPaddleX + mPaddle.getWidth()) && mPaddleY < y && y < (mPaddleY + mPaddle.getHeight()))  {
                return true;
        }
        return false;
    }


    //This is run whenever the phone is touched by the user
    @Override
    protected void actionOnTouch(float x, float y) {
        //Move the ball to the x position of the touch
        if(mPaddleX != x)   {
            mPaddleSpeedX = x - mPaddleX;
        } else {
            mPaddleSpeedX = 0;
        }



    }

    //This is run whenever the phone moves around its axises
    @Override
    protected void actionWhenPhoneMoved(float xDirection, float yDirection, float zDirection) {
        //Change the paddle speed
        /*mPaddleSpeedX = mPaddleSpeedX + 70f * xDirection;

        //If paddle is outside the screen and moving further away
        //Move it into the screen and set its speed to 0
        if((mPaddleX - mPaddle.getWidth()/2) <= 0 && mPaddleSpeedX <= 0) {
            mPaddleSpeedX = 0;
            mPaddleX = 0 + (mPaddle.getWidth()/2);
        }
        if((mPaddleX + mPaddle.getWidth()/2) >= mCanvasWidth && mPaddleSpeedX >= 0) {
            mPaddleSpeedX = 0;
            mPaddleX = mCanvasWidth - (mPaddle.getWidth()/2);
        }*/

    }


    //This is run just before the game "scenario" is printed on the screen
    @Override
    protected void updateGame(float secondsElapsed) {
        //If the ball moves down on the screen
        if(mBallSpeedY > 0) {
            //Check for a paddle collision
            updateBallCollision(mPaddleX, mPaddleY);
        }

        //Move the ball's X and Y using the speed (pixel/sec)
        mBallX = mBallX + secondsElapsed * mBallSpeedX;
        mBallY = mBallY + secondsElapsed * mBallSpeedY;

        //Move the paddle's X and Y using the speed (pixel/sec)
        mPaddleX = mPaddleX + secondsElapsed * mPaddleSpeedX;

        //Check if the ball hits either the left side or the right side of the screen
        //But only do something if the ball is moving towards that side of the screen
        //If it does that => change the direction of the ball in the X direction
        if((mBallX <= mBall.getWidth() / 2 && mBallSpeedX < 0) || (mBallX >= mCanvasWidth - mBall.getWidth() / 2 && mBallSpeedX > 0) ) {
            mBallSpeedX = -mBallSpeedX;
        }

        //Check for SmileyBall collision
        /*if(updateBallCollision(mSmileyBallX, mSmileyBallY)) {
            //Increase score
            updateScore(1);
        }*/

        if(hitCheck(mBallX, mBallY))    {
            mBallSpeedX = mBallSpeedX - (mBallSpeedX* 2);
            mBallSpeedY = mBallSpeedY - (mBallSpeedY* 2);
        }

        //Loop through all SadBalls
        for(int i = 0; i < mSadBallX.length; i++) {
            //Perform collisions (if necessary) between SadBall in position i and the red ball
            updateBallCollision(mSadBallX[i], mSadBallY[i]);
        }

        //If the ball goes out of the top of the screen and moves towards the top of the screen =>
        //change the direction of the ball in the Y direction
        if(mBallY <= mBall.getWidth() / 2 && mBallSpeedY < 0) {
            mBallSpeedY = -mBallSpeedY;
        }

        //If the ball goes out of the bottom of the screen => lose the game
        if(mBallY >= mCanvasHeight) {
            setState(GameThread.STATE_LOSE);
        }

    }

    //Collision control between mBall and another big ball
    private boolean updateBallCollision(float x, float y) {
        //Get actual distance (without square root - remember?) between the mBall and the ball being checked
        float distanceBetweenBallAndPaddle = (x - mBallX) * (x - mBallX) + (y - mBallY) *(y - mBallY);

        //Check if the actual distance is lower than the allowed => collision
        if(mMinDistanceBetweenBallAndPaddle >= distanceBetweenBallAndPaddle) {
            //Get the present speed (this should also be the speed going away after the collision)
            float speedOfBall = (float) Math.sqrt(mBallSpeedX*mBallSpeedX + mBallSpeedY*mBallSpeedY);

            //Change the direction of the ball
            mBallSpeedX = mBallX - x;
            mBallSpeedY = mBallY - y;

            //Get the speed after the collision
            float newSpeedOfBall = (float) Math.sqrt(mBallSpeedX*mBallSpeedX + mBallSpeedY*mBallSpeedY);

            //using the fraction between the original speed and present speed to calculate the needed
            //velocities in X and Y to get the original speed but with the new angle.
            mBallSpeedX = mBallSpeedX * speedOfBall / newSpeedOfBall;
            mBallSpeedY = mBallSpeedY * speedOfBall / newSpeedOfBall;

            return true;
        }

        return false;
    }

    // My classes


}

// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
//
// You should have received   copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.
