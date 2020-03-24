package uk.ac.reading.sis05kol.mooc;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {

    // Image used to represent Sprite in the game
    private Bitmap pImage;

    // X and Y coordinates for Sprite's position
    private float pY;
    private float pX;

    // How fast the Sprite will move (pixels per second)
    private float pSpeedX = 0;
    private float pSpeedY = 0;

    // How big the Sprite is
    private float pWidth = 0;
    private float pHeight = 0;
    private float pHalfWidth = 0;
    private float pHalfHeight = 0;

    /**
     * Sprite class, default properties of every object of type Sprite
     * @param image image of the object for game
     */
    public Sprite (Bitmap image){

        pImage = image;
        pWidth= image.getWidth();
        pHeight = image.getHeight();
        pHalfWidth = pWidth / 2;
        pHalfHeight = pHeight / 2;

    }

    public float getWidth() {
        return this.pWidth;
    }

    public float getHeight()    {
        return this.pHeight;
    }

    public float getX() {
        return this.pX;
    }

    public float getY() {
        return this.pY;
    }

    public float getLeft() { return pX-pHalfWidth; }
    public float getRight() { return pX+pHalfWidth; }
    public float getTop() { return pY-pHalfHeight; }
    public float getBottom() { return pY+pHalfHeight; }

    public boolean contains(float x, float y) {
        return getLeft() <= x && x <= getRight() && getTop() <= y && y <= getBottom();
    }


    public void setPosition(float x, float y) {
        pX = x;
        pY = y;
    }

    /**
     * Draw the image on the canvas, centred at the current position
     * @param canvas    Canvas that the game is made on
     */
    public void draw(Canvas canvas) {
        // drawBitmap uses top left corner as reference,
        // null means that we will use the image without any extra features (called Paint)
        canvas.drawBitmap( pImage, getLeft(), getTop(), null);
    }
    
    // Takes object and checks if objects overlap during runtime
    public boolean topOverlap( Sprite s ) {
        return getTop() <= s.getBottom() && getTop() >= s.getTop();
    }
    public boolean bottomOverlap( Sprite s ) {
        return getBottom() >= s.getTop() && getBottom() <= s.getBottom();
    }
    public boolean leftOverlap( Sprite s ) {
        return getLeft() >= s.getLeft() && getLeft() <= s.getRight();
    }
    public boolean rightOverlap( Sprite s ) {
        return getRight() <= s.getRight() && getRight() >= s.getLeft();
    }
    public boolean widthOverlap( Sprite s ) {
        return s.getLeft() <= getRight() && s.getRight() >= getLeft();
    }
    public boolean heightOverlap( Sprite s ) {
        return s.getTop() <= getBottom() && s.getBottom() >= getTop();
    }

    /**
     * Returns boolean using all functions above to whether objects are overlapping during runtime
     * @param s Sprite - Object (Paddle, Brick, Ball...)
     * @return
     */
    public boolean isOverlapping( Sprite s ) {
        return ((topOverlap(s) || bottomOverlap(s)) && widthOverlap(s)) ||
                ((leftOverlap(s) || rightOverlap(s)) && heightOverlap(s));
    }

    // Get overlap sizes


    /**
     * set and get speed/direction
     * @param dx    new speed x coord value
     * @param dy    new speed y coord value
     */
    public void setSpeed( float dx, float dy ) {
        pSpeedX = dx;
        pSpeedY = dy;
    }
    public float speedX() {
        return pSpeedX;
    }
    public float speedY() {
        return pSpeedY;
    }
    public boolean isMovingLeft() {
        return pSpeedX < 0;
    }
    public boolean isMovingRight() {
        return pSpeedX > 0;
    }
    public boolean isMovingDown() {
        return pSpeedY > 0;
    }
    public boolean isMovingUp() {
        return pSpeedY < 0;
    }

    // Move object opposite direction it was going toward
    public void moveX() {
        pSpeedX = -pSpeedX;
    }
    public void moveY() {
        pSpeedY = -pSpeedY;
    }

    /**
     * Updates position of bject accroding to current speed and time elapsed
     * @param secondsElapsed    seconds elapsed during runtime
     */
    public void move(float secondsElapsed) {
        pX = pX + secondsElapsed * pSpeedX;
        pY = pY + secondsElapsed * pSpeedY;
    }


}
