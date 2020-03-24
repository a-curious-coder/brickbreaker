package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;

public class Ball extends Sprite    {
    private float bRadius = 0;

    public Ball(Bitmap image)   {
        super(image);
        bRadius = image.getWidth()/2;
    }

}
