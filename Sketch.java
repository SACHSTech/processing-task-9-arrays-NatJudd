import processing.core.PApplet;

/**
 * Creates a sketch that simulates snow falling, the character's goal is to avoid
 * the snowflakes as they fall.
 * 
 * @author NJudd
 */
public class Sketch extends PApplet {
    // Initializes background variables
    int intScreenW = 800;
    int intScreenH = 600;

    // Initializes snow variables
    int intSnowCount = 60;
    float fltSnowSize = 20;
    float fltSnowSpeed;
    boolean blnSnowSlow = false;
    boolean blnSnowFast = false;
    Snowflake[] snow; // an array of snowflake objects

    // Initializes variables for lives
    float fltLifeSize = 50;
    int intLifeCount = 3;
    boolean[] blnLives = new boolean[intLifeCount];

    /**
     * Initializes background size.
     */
    public void settings() {
        // Background size
        size(800, 600);
    }

    /**
     * Sets up the initial environment.
     */
    public void setup() {
        // Background color
        background(0, 10, 60);

        // Sets color of snow
        fill(255);
        stroke(255);

        // Creates snowflakes
        snow = new Snowflake[intSnowCount];
        for (int i = 0; i < intSnowCount; i++) {
            snow[i] = new Snowflake(this, fltSnowSize);
        }

        // Sets lives to full
        for (int i = 0; i < intLifeCount; i++) {
            blnLives[i] = true;
        }
    }

    /**
     * Top level method to execute the program.
     */
    public void draw() {
        // Recalls background
        background(0, 10, 60);

        // Changes speed of snow
        if (blnSnowSlow) {
            fltSnowSpeed = 3;
        } else if (blnSnowFast) {
            fltSnowSpeed = 9;
        } else {
            fltSnowSpeed = 6;
        }

        // Prints snow falling
        for (int i = 0; i < intSnowCount; i++) {
            snow[i].fall(fltSnowSpeed);
            snow[i].draw();
        }
    }

    /**
     * Allows the snow to fall faster or slower.
     */
    public void keyPressed() {
        if (keyCode == UP) {
            blnSnowSlow = true;
        } else if (keyCode == DOWN) {
            blnSnowFast = true;
        }
    }

    /**
     * Resets the snow speed.
     */
    public void keyReleased() {
        if (keyCode == UP) {
            blnSnowSlow = false;
        } else if (keyCode == DOWN) {
            blnSnowFast = false;
        }
    }
}