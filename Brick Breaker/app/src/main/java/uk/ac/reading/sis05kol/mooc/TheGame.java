package uk.ac.reading.sis05kol.mooc;

//Other parts of the android libraries that we use
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class TheGame extends GameThread{

    // Stores the image of a ball
    private Ball mBall;
    private Ball mBallGlow;
    // Stores the image of the smiley ball (score ball)
    private Ball mSmileyBall;
    // Stores the image of the sad ball (no-score ball)
    private Ball mSadBall;
    // Stores the image of the Paddle used to hit the ball
    private Paddle mPaddle;
    private Paddle mPaddleGlow;
    // Arrays of Bricks
    private Brick[] mBricks;
    private Brick[] mActiveBricks;

    private float x;

    //This is run before anything else, so we can prepare things here
    public TheGame(GameView gameView) {

        //House keeping
        super(gameView);

        //Prepare the image so we can draw it on the screen (using a canvas)
        mBall = new Ball (BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.ball));

        mBallGlow = new Ball (BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.ball_glow));

        //Prepare the image of the paddle so we can draw it on the screen (using a canvas)
        mPaddle = new Paddle (BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.platform));

        mPaddleGlow = new  Paddle (BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.platform_glow));

        // Idea is to create a row of bricks - Unknown screen size.
        // Set up a number of bricks and decide how many of them to use in setupBeginning()

        int numberOfBricks = 48;
        Bitmap brickImage = BitmapFactory.decodeResource(
                gameView.getContext().getResources(),
                    R.drawable.pink_block);

        mBricks = new Brick[numberOfBricks];
        for (int i = 0; i < mBricks.length; ++i)
            mBricks[i] = new Brick(brickImage);


        //Prepare the image of the SmileyBall so we can draw it on the screen (using a canvas)
        mSmileyBall =  new Ball (BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.smiley_ball));

        //Prepare the image of the SadBall(s) so we can draw it on the screen (using a canvas)
        mSadBall =  new Ball (BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.sad_ball));
    }

    //This is run before a new game (also after an old game)
    @Override
    public void setupBeginning() {

        // Ball positioned in middle of screen, speed dictates the ball moves straight down toward paddle.
        mBall.setPosition(mCanvasWidth / 2, mCanvasHeight / 2);
        mBall.setSpeed(mCanvasWidth / 20, mCanvasHeight / 3);
        mBallGlow.setPosition(mBall.getX(), mBall.getY());
        mBallGlow.setSpeed(mBall.getSpeedX(), mBall.getSpeedY());

        // Paddle at the bottom of the screen in the middle
        mPaddle.setPosition(mCanvasWidth / 2, mCanvasHeight - ( mPaddle.getHeight() + mPaddleGlow.getHeight()) / 2);
        mPaddleGlow.setPosition(mPaddle.getX(), mPaddle.getY());
        mPaddle.setSpeed(0, 0); // Set to 0, 0 just in case it's a new game
        mPaddleGlow.setSpeed(mPaddle.getSpeedX(), mPaddle.getSpeedY());

        // If there are bricks and the first brick element is not null then
        if ( mBricks != null && mBricks[0] != null) {

            // Space between bricks
            int gapBetweenBricks = 15;
            float brickWidth = mBricks[0].getWidth() + gapBetweenBricks;
            // we want to use enough bricks to fill a row
            int bricksPerRow = (int) (mCanvasWidth / brickWidth);

            // but we can only use up to the number of bricks that we already have available
            if (bricksPerRow > mBricks.length) {
                bricksPerRow = mBricks.length;
            }

            // initialise the array to hold the bricks we want to use
            mActiveBricks = new Brick[mBricks.length];

            // set up position of first brick
            float margin = mCanvasWidth - (brickWidth * bricksPerRow);
            float nextBrickX = (margin / 2 + brickWidth / 2);
            float nextBrickY = mCanvasHeight / 3;

            // reference the required number of available bricks in the active brick array
            // Set bricks positions
            for (int i = 0; i < mActiveBricks.length; ++i) {
                mActiveBricks[i] = mBricks[i];

                // If x coordinate of the next brick is over the value of mCanvasWidth then
                // move to a new row above current row
                if (nextBrickX > mCanvasWidth) {
                    nextBrickY += -(mBricks[i].getHeight() + gapBetweenBricks);
                    nextBrickX = margin / 2 + brickWidth / 2;
                }

                mActiveBricks[i].setPosition(nextBrickX, nextBrickY);
                nextBrickX += brickWidth;
            }
        }
    }

    @Override
    protected void doDraw(Canvas canvas) {
        //If there isn't a canvas to do nothing
        //It is ok not understanding what is happening herep
        if(canvas == null) return;

        //House keeping
        super.doDraw(canvas);
        // Draw ball & paddle
        mBall.draw(canvas);
        mBallGlow.draw(canvas);
        mPaddle.draw(canvas);
        mPaddleGlow.draw(canvas);

        // Checks if bricks that are active have been eliminated
        // if ActiveBrick element i has not been eliminated, draw brick, else, do not draw
        if  (mActiveBricks != null) {
            for(int i = 0; i < mActiveBricks.length; i++) {
                if  (mActiveBricks[i] != null)  {
                    mActiveBricks[i].draw(canvas);
                }
            }
        }

        //Draw SmileyBall
        mSmileyBall.draw(canvas);

        //Loop through all SadBall
        for(int i = 0; i < 1; i++) {
            //Draw SadBall in position i
            mSadBall.draw(canvas);
        }
    }


    //This is run whenever the phone is touched by the user
    @Override
    protected void actionOnTouch(float x, float y) {

        // Moves the ball to the x position of the touch instantly
        //mPaddle.stopAt(x);

        // Moves the paddle towards the x coordinate being touched
        mPaddle.moveTowards(x);
        mPaddleGlow.moveTowards(x);
        this.x = x;
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

    private void moveOffSides(Sprite s, float secondsElapsed)    {

        // Moves the ball during the game runtime
        s.move(secondsElapsed);

        // Checks to see if ball is colliding with top of screen, if so, move away from when collision occurs.
        if (s.isMovingUp() && s.getTop() <= 0)  {
            s.moveY();
        } else if (s.isMovingDown() && s.getBottom() >= mCanvasHeight) {
            // You lose the game if the ball hits the bottom
            setupBeginning();
            setState(STATE_LOSE);
        }
        // ball moves to left/right side depending which side of screen it hits. E.g. hits right side, move left.
        if ((s.isMovingLeft() && s.getLeft() <= 0) || (s.isMovingRight() && s.getRight() >= mCanvasWidth))  {
            s.moveX();
        }



    }

    //This is run just before the game "scenario" is printed on the screen
    @Override
    protected void updateGame(float secondsElapsed) {
        if  (mBallGlow.getX() != mBall.getX() || mBallGlow.getY() != mBall.getY())  {
            mBallGlow.setSpeed(mBall.getSpeedX(), mBall.getSpeedY());
            mBallGlow.setPosition(mBall.getX(), mBall.getY());
        }

        mBallGlow.move(secondsElapsed);

        if  (mBall.isMovingDown() && mBall.isOverlapping(mPaddle))    {
            // Bounce the ball off of the paddle, not the paddle from the ball.
            mBall.reboundOff(mPaddle);
        }

        if (mBall.reboundOff(mSmileyBall) && mSmileyBall.reboundOff(mBall)) {
            updateScore(1);
        }


        if  (mActiveBricks != null) {
            for (int i = 0; i < mActiveBricks.length; i++)  {
                if  (mActiveBricks[i] != null && mBall.isOverlapping( mActiveBricks[i] ))    {
                    mBall.reboundOff( mActiveBricks[i] );
                    // When brick is hit, the brick disappears from array holding that brick.
                    mActiveBricks[i] = null;

                    // Adds to score if brick is hit.
                    updateScore(1);
                }
            }

            // check if there are remaining bricks.
            boolean remainingBricks = false;
            for (int i = 0; i < mActiveBricks.length; i++)  {
                if  (mActiveBricks[i] != null)  {
                    remainingBricks = true;
                }
            }

            // if all bricks are gone from mActiveBricks, repopulate the array.
            if  (!remainingBricks)  {
                for (int i = 0; i < mActiveBricks.length; i++)  {
                    mActiveBricks[i] = mBricks[i];
                }
            }
        }

        // Keeps ball moving during runtime - deals with sides of screen
        moveOffSides(mBall, secondsElapsed);

        // Move paddle toward touch on screen over period of time
        mPaddle.move(secondsElapsed);
        mPaddleGlow.move(secondsElapsed);

        if  (mPaddle.isPaddleAtDestination(mPaddle.getX(), x))    {
            mPaddle.stopPaddle(mPaddle.getX());
            mPaddleGlow.stopPaddle(mPaddle.getX());
        }

        if  ( mPaddle.isMovingLeft() && mPaddle.getX() <= 0)    {
            mPaddle.stopAt(0);
            mPaddleGlow.stopAt(mPaddle.getX());
        }
        else if (mPaddle.isMovingRight() && mPaddle.getX() >= mCanvasWidth)  {
            mPaddle.stopAt(mCanvasWidth);
            mPaddleGlow.stopAt(mPaddle.getX());
        }

        //Check if the ball hits either the left side or the right side of the screen
        //But only do something if the ball is moving towards that side of the screen
        //If it does that => change the direction of the ball in the X direction


        /*//Loop through all SadBalls
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
        }*/

    }



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
