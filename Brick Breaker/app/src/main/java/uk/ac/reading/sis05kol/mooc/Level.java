package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Random;

public class Level  {

    private int basicBricks;
    private int powerBricks;
    private int lifeBricks;
    private Bitmap basicBrick, powerBrick, lifeBrick;
    private Brick[] mBricks;


    Level(int level, Bitmap bBrick, Bitmap pBrick, Bitmap lBrick) {
        // Giving images to represent bricks.
        this.basicBrick = bBrick;
        this.powerBrick = pBrick;
        this.lifeBrick = lBrick;

        switch(level)   {
            case 1:
                basicBricks = 5;
                powerBricks = 2;
                lifeBricks = 1;
                break;

            case 2:
                basicBricks = 10;
                powerBricks = 2;
                lifeBricks = 0;
                break;

            case 3:
                basicBricks = 20;
                powerBricks = 4;
                lifeBricks = 2;
                break;

            case 4:
                basicBricks = 31;
                powerBricks = 1;
                lifeBricks = 0;
                break;

            case 5:
                basicBricks = 40;
                powerBricks = 5;
                lifeBricks = 3;
                break;
        }

        setBricks(basicBricks, powerBricks, lifeBricks);
    }


    public Brick[] setBricks (int nBasicBrick, int nPowerBrick, int nLifeBrick)    {

        Random rnd = new Random();

        //  Add all bricks together
        int totalBricks = nBasicBrick + nLifeBrick + nPowerBrick;

        // Set this mBricks variable to the size of totalBricks
        this.mBricks = new Brick[totalBricks];

        int rndBrick = 0;
        // Sets all bricks to basic
        for (int i = 0; i < totalBricks; i++)   {
            mBricks[i] = new Brick(basicBrick);
        }

        for (int i = 0; i < nLifeBrick; i++)    {
            rndBrick = rnd.nextInt(totalBricks);
            mBricks[rndBrick] = new Brick(powerBrick);
        }

        for (int i = 0; i < nPowerBrick; i++)   {
            rndBrick = rnd.nextInt(totalBricks);
            mBricks[rndBrick] = new Brick(lifeBrick);
        }

        return mBricks;
    }

}
