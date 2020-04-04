package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

enum BrickType   {
    BASIC, LIFE, POWERUP, POWERPOINTS
}

public class Brick extends Sprite   {
    private BrickType brickType;

    public Brick (BrickType brickType) {
        Bitmap image;
        if  (brickType == BrickType.BASIC ) {
            image = // Basic brick  -   Updates score by 1 as do most other bricks.
                    BitmapFactory.decodeResource(
                    gameView.getContext().getResources(),
                    R.drawable.pink_block);
        }
        super(image);
    }
}
