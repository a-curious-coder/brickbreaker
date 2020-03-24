package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;

public class Paddle extends Sprite  {

    public Paddle(Bitmap image) {
        super(image);
    }

    // Moves the paddle toward the x coordinates of the touch
    public void moveTo (float x)   {
        setSpeed(x - getX(), 0);
    }
    public void stopAt(float x) {
        setSpeed(0, 0);
        setPosition(x, getY());
    }
}
