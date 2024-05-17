import processing.core.PApplet;
import processing.core.PImage;

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

  // Background images
  PImage imgBG;
  PImage imgSnowScene;
  PImage imgGameOver;

  // Initializes snow variables
  int intSnowCount = 15;
  int intSnowSize = 50;
  float fltSnowSpeed;
  boolean blnSnowSlow = false;
  boolean blnSnowFast = false;
  Snowflake[] snow; // an array of snowflake objects

  // Initializes variables for lives
  int intLifeSize = 35;
  int intLifeCount = 3;

  // Lives image
  PImage imgLives;

  // Safe zone variables
  float fltSafeZone = intScreenH - 50;

  // Initializes variables for the player
  int intPlayerW = 40;
  int intPlayerH = 40;
  float fltPlayerX = intScreenW / 2 - intPlayerW / 2;
  float fltPlayerY = intScreenH - 44;
  float fltPlayerSpeed = 3;

  // Image for the player
  PImage imgPlayer;

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
    // Snow background image
    imgSnowScene = loadImage("SnowBackground.jpg");
    imgSnowScene.resize(intScreenW, intScreenH);

    // Game Over background image
    imgGameOver = loadImage("GameOver.jpg");
    imgGameOver.resize(intScreenW, intScreenH);

    // Sets the current background
    imgBG = imgSnowScene;

    // Player image
    imgPlayer = loadImage("santa.png");
    imgPlayer.resize(intPlayerW, intPlayerH);

    // Lives image
    imgLives = loadImage("heart.png");
    imgLives.resize(intLifeSize, intLifeSize);

    // Creates snowflakes
    snow = new Snowflake[intSnowCount];
    for (int i = 0; i < intSnowCount; i++) {
      snow[i] = new Snowflake(this, intSnowSize);
    }
  }

  /**
   * Top level method to execute the program.
   */
  public void draw() {
    // Recalls background
    image(imgBG, 0, 0);

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
    image(imgPlayer, fltPlayerX, fltPlayerY);

    // Stops player from moving off the screen
    if (fltPlayerX <= 0) {
      fltPlayerX = 0;
    } else if (fltPlayerX + intPlayerW >= intScreenW) {
      fltPlayerX = intScreenW - intPlayerW;
    }
    if (fltPlayerY <= 0) {
      fltPlayerY = 0;
    } else if (fltPlayerY + intPlayerH >= intScreenH) {
      fltPlayerY = intScreenH - intPlayerH;
    }

    // Checks if player touches snowflakes
    for (int i = 0; i < intSnowCount; i++) {
      if (snow[i].getSnowflakePositionX() < fltPlayerX + intPlayerW
          && snow[i].getSnowflakePositionX() + intSnowSize > fltPlayerX
          && snow[i].getSnowflakePositionY() + intSnowSize > fltPlayerY
          && snow[i].getSnowflakePositionY() < fltPlayerY + intPlayerH) {
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
      image(imgLives, 755, 10);
      image(imgLives, 755 - 10 - intLifeSize, 10);
      image(imgLives, 755 - 20 - 2 * intLifeSize, 10);
    } else if (intLifeCount == 2) {
      image(imgLives, 755, 10);
      image(imgLives, 755 - 10 - intLifeSize, 10);
    } else if (intLifeCount == 1) {
      image(imgLives, 755, 10);
    } else {
      imgBG = imgGameOver;
      image(imgBG, 0, 0);
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
      if (snow[i].getSnowflakePositionX() < mouseX
          && snow[i].getSnowflakePositionX() + intSnowSize > mouseX
          && snow[i].getSnowflakePositionY() + intSnowSize > mouseY
          && snow[i].getSnowflakePositionY() < mouseY) {
        // Resets the snowflakes position
        snow[i].setSnowflakePosition(random(width), 0);
      }
    }
  }
}