package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Random;

public class BricksStructure {

    Brick[] BricksStructure(Brick[] bricksStructure, Bitmap image)    {

        for (int i = 0; i < bricksStructure.length; ++i)
                bricksStructure[i] = new Brick(image);

        return bricksStructure;
    }
}
