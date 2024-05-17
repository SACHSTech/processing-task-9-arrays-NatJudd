import processing.core.PApplet;

/**
 * Creates a sketch that simulates snow falling, the character's goal is to
 * avoid the snowflakes as they fall.
 * 
 * @author NJudd
 */
public class Sketch extends PApplet {
  // Initializes background variables
  int intScreenW = 800;
  int intScreenH = 650;

  // Background colours
  int intScreenR = 0;
  int intScreenG = 10;
  int intScreenB = 60;

  // Initializes snow variables
  int intSnowCount = 15;
  float fltSnowSize = 50;
  float fltSnowSpeed;
  boolean blnSnowSlow = false;
  boolean blnSnowFast = false;
  Snowflake[] snow; // an array of snowflake objects

  // Initializes variables for lives
  float fltLifeSize = 35;
  int intLifeCount = 3;

  // Safe zone variables
  float fltSafeZone = intScreenH - 50;

  // Initializes variables for the player
  float fltPlayerX = intScreenW / 2;
  float fltPlayerY = intScreenH - 25;
  float fltPlayerSpeed = 3;
  float fltPlayerW = 25;
  float fltPlayerH = 25;

  // Player movement variables
  boolean blnPlayerUp = false;
  boolean blnPlayerDown = false;
  boolean blnPlayerLeft = false;
  boolean blnPlayerRight = false;

  /**
   * Initializes background size.
   */
  public void settings() {
    // Background size
    size(intScreenW, intScreenH);
  }

  /**
   * Sets up the initial environment.
   */
  public void setup() {
    // Background color
    background(intScreenR, intScreenG, intScreenB);

    // Creates snowflakes
    snow = new Snowflake[intSnowCount];
    for (int i = 0; i < intSnowCount; i++) {
      snow[i] = new Snowflake(this, fltSnowSize);
    }
  }

  /**
   * Top level method to execute the program.
   */
  public void draw() {
    // Recalls background
    background(intScreenR, intScreenG, intScreenB);

    // Changes speed of snow
    if (blnSnowSlow) {
      fltSnowSpeed = 1;
    } else if (blnSnowFast) {
      fltSnowSpeed = 5;
    } else {
      fltSnowSpeed = 3;
    }

    // Prints snow falling
    for (int i = 0; i < intSnowCount; i++) {
      snow[i].fall(fltSnowSpeed);
      snow[i].draw();
    }

    // Player movement
    if (blnPlayerUp) {
      fltPlayerY -= fltPlayerSpeed;
    }
    if (blnPlayerDown) {
      fltPlayerY += fltPlayerSpeed;
    }
    if (blnPlayerLeft) {
      fltPlayerX -= fltPlayerSpeed;
    }
    if (blnPlayerRight) {
      fltPlayerX += fltPlayerSpeed;
    }

    // Draws player on the screen
    strokeWeight(1);
    stroke(0, 255, 255);
    fill(0, 255, 255);
    ellipse(fltPlayerX, fltPlayerY, fltPlayerW, fltPlayerH);

    // Checks if player touches snowflakes
    for (int i = 0; i < intSnowCount; i++) {
      if (snow[i].getSnowflakePositionX() - fltSnowSize / 2 < fltPlayerX + fltPlayerW / 2
          && snow[i].getSnowflakePositionX() + fltSnowSize / 2 > fltPlayerX - fltPlayerW / 2
          && snow[i].getSnowflakePositionY() + fltSnowSize / 2 > fltPlayerY - fltPlayerH / 2
          && snow[i].getSnowflakePositionY() - fltSnowSize / 2 < fltPlayerY + fltPlayerH / 2) {
        // Substracts life
        intLifeCount -= 1;

        // Resets the snowflakes position that the player colides with
        snow[i].setSnowflakePosition(random(width), 0);
      }
    }

    // Draws safe zone border
    stroke(255);
    strokeWeight(10);
    line(0, fltSafeZone, intScreenW, fltSafeZone);

    // Draw player lives of the screen
    if (intLifeCount == 3) { // three is the maximum amount of lifes
      strokeWeight(1);
      stroke(200, 0, 0);
      fill(200, 0, 0);
      rect(755, 10, fltLifeSize, fltLifeSize);
      rect(755 - 10 - fltLifeSize, 10, fltLifeSize, fltLifeSize);
      rect(755 - 20 - 2 * fltLifeSize, 10, fltLifeSize, fltLifeSize);
    } else if (intLifeCount == 2) {
      strokeWeight(1);
      stroke(200, 0, 0);
      fill(200, 0, 0);
      rect(755, 10, fltLifeSize, fltLifeSize);
      rect(755 - 10 - fltLifeSize, 10, fltLifeSize, fltLifeSize);
    } else if (intLifeCount == 1) {
      strokeWeight(1);
      stroke(200, 0, 0);
      fill(200, 0, 0);
      rect(755, 10, fltLifeSize, fltLifeSize);
    } else {
      intScreenR = 255;
      intScreenG = 255;
      intScreenB = 255;
      background(intScreenR, intScreenG, intScreenB);
    }

  }

  /**
   * Allows the snow to fall faster or slower and allows the player to move.
   */
  public void keyPressed() {
    // Snow falling
    if (keyCode == UP) {
      blnSnowSlow = true;
    } else if (keyCode == DOWN) {
      blnSnowFast = true;
    }

    // Player movement
    if (key == 'w') {
      blnPlayerUp = true;
    } else if (key == 's') {
      blnPlayerDown = true;
    } else if (key == 'a') {
      blnPlayerLeft = true;
    } else if (key == 'd') {
      blnPlayerRight = true;
    }
  }

  /**
   * Resets the snow speed and stops player movement.
   */
  public void keyReleased() {
    // Snow falling
    if (keyCode == UP) {
      blnSnowSlow = false;
    } else if (keyCode == DOWN) {
      blnSnowFast = false;
    }

    // Player movement
    if (key == 'w') {
      blnPlayerUp = false;
    } else if (key == 's') {
      blnPlayerDown = false;
    } else if (key == 'a') {
      blnPlayerLeft = false;
    } else if (key == 'd') {
      blnPlayerRight = false;
    }
  }

  /**
   * Resets snowflake's position that were clicked on
   */
  public void mousePressed() {
    for (int i = 0; i < intSnowCount; i++) {
      if (snow[i].getSnowflakePositionX() - fltSnowSize / 2 < mouseX
          && snow[i].getSnowflakePositionX() + fltSnowSize / 2 > mouseX
          && snow[i].getSnowflakePositionY() + fltSnowSize / 2 > mouseY
          && snow[i].getSnowflakePositionY() - fltSnowSize / 2 < mouseY) {
        // Resets the snowflakes position
        snow[i].setSnowflakePosition(random(width), 0);
      }
    }
  }
}