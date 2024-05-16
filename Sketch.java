import processing.core.PApplet;

/**
 * Creates a sketch that simulates snow falling, when the down arrow is pressed
 * on the keyboard, the snow falls faster, when the up arrow is pressed the snow
 * falls slower
 * 
 * @author NJudd
 */
public class Sketch extends PApplet {
  // initializes variables and arrays
  int intScreenW = 800;
  int intScreenH = 600;
  int intSnowCount = 80;
  float fltSnowSpeed;
  boolean blnSnowSlow = false;
  boolean blnSnowFast = false;
  float[] fltSnowX = new float[intSnowCount];
  float[] fltSnowY = new float[intSnowCount];

  /**
   * initializes background size
   * 
   * @author NJudd
   */
  public void settings() {
    // background size
    size(800, 600);
  }

  /**
   * initializes background colour, snow colour, and snow position
   * 
   * @author NJudd
   */
  public void setup() {
    // baclground colour
    background(0, 10, 60);
    // sets colour of snow
    fill(255);
    stroke(255);
    // sets snow position
    for (int i = 0; i < intSnowCount; i++) {
      fltSnowX[i] = random(0, intScreenW);
      fltSnowY[i] = random(0, intScreenH);
    }
  }

  /**
   * top level method to execute the program
   * 
   * @author NJudd
   */
  public void draw() {
    // recalls background
    background(0, 10, 60);

    // changes speed of cloud
    if (blnSnowSlow) {
      fltSnowSpeed = 3;
    } else if (blnSnowFast) {
      fltSnowSpeed = 9;
    } else {
      fltSnowSpeed = 6;
    }

    // prints snow falling
    for (int i = 0; i < intSnowCount; i++) {
      fltSnowY[i] += fltSnowSpeed;
      // checks if snow is at the bottom of the screen
      if (fltSnowY[i] > intScreenH) {
        // resets snow y position
        fltSnowY[i] = 0;
      }
      // prints snow
      ellipse(fltSnowX[i], fltSnowY[i], 25, 25);
    }
  }

  /**
   * allows the snow to fall faster or slower
   * 
   * @author NJudd
   */
  public void keyPressed() {
    if (keyCode == UP) {
      blnSnowSlow = true;
    } else if (keyCode == DOWN) {
      blnSnowFast = true;
    }
  }

  /**
   * resets the snow speed
   * 
   * @author NJudd
   */
  public void keyReleased() {
    if (keyCode == UP) {
      blnSnowSlow = false;
    } else if (keyCode == DOWN) {
      blnSnowFast = false;
    }
  }
}
