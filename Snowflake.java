import processing.core.PApplet;

/**
 * Creates a Snowflake class
 * 
 * @author NJudd
 */
public class Snowflake {
    // Instance variables
    private float fltPosX;
    private float fltPosY;
    private float fltSize;
    private PApplet p; // Reference to the PApplet instance

    /**
     * Constructor: Initializes the snowflake’s position and size.
     * 
     * @param P    reference to the PApplet instance
     * @param size width and height of the snowflakes
     */
    public Snowflake(PApplet P, float size) {
        p = P;
        fltSize = size;
        fltPosX = p.random(p.width);
        fltPosY = p.random(p.height - 45 - fltSize / 2); // 45 is the size of safe zone
    }

    /**
     * Getter method for fltPosX
     * 
     * @return the x coordinate of the snowflake
     */
    public float getSnowflakePositionX() {
        return fltPosX;
    }

    /**
     * Getter method for fltPosY
     * 
     * @return the y coordinate of the snowflake
     */
    public float getSnowflakePositionY() {
        return fltPosY;
    }

    /**
     * Setter method for snowflake x and y position
     * 
     * @param posX x position
     * @param posY y position
     */
    public void setSnowflakePosition(float posX, float posY) {
        fltPosX = posX;
        fltPosY = posY;
    }

    /**
     * Updates the y-position of the snowflake based on the inputted speed and
     * resets it if it falls off the bottom of the screen.
     * 
     * @param speed speed that the snowflakes fall at
     */
    public void fall(float speed) {
        fltPosY += speed;
        if (fltPosY > p.height - 45 - fltSize / 2) { // 45 is the size of safe zone
            fltPosY = 0;
            fltPosX = p.random(p.width);
        }
    }

    /**
     * Prints the snowflakes to the screen when called
     */
    public void draw() {
        p.strokeWeight(1);
        p.fill(255);
        p.stroke(255);
        p.ellipse(fltPosX, fltPosY, fltSize, fltSize);
    }
}