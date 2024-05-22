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

  // Game start variables
  boolean blnGameStart = false;
  int intTitleSize = 50;
  int intHeadingSize = 30;
  int intBodySize = 20;
  int intTime = 0;
  int intTextColour = 255;

  // Game end variables
  int intCurrentScore = 0;
  int intScore = 10000;
  int intScoreIncreaseRate = 3;
  boolean blnHasPlayerWon = false;

  // Santa image and variables
  PImage imgSanta;
  float fltSantaX = 350;
  float fltSantaY = 450;
  int intSantaSpeed = 2;

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
  boolean blnInsideZone = true;

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

    // Santa image on the start screen
    imgSanta = loadImage("santa.png");
    imgSanta.resize(100, 100);

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
    // Runs the game
    if (blnHasPlayerWon) {
      // Win Screen
      image(imgSnowScene, 0, 0);
      fill(255);
      textSize(intTitleSize);
      textAlign(CENTER, CENTER);
      text("YOU WIN", 400, 325);
    } else if (blnGameStart) {
      // Counts game time
      if (fltPlayerY < fltSafeZone) {
        intCurrentScore += intScoreIncreaseRate;
      }

      // Recalls background
      image(imgBG, 0, 0);

      // Prints score to the screen
      fill(255);
      textSize(intBodySize);
      textAlign(LEFT, TOP);
      text("Score: " + intCurrentScore, 10, 10);

      // Checks to see if player has won
      if (intCurrentScore > intScore) {
        blnHasPlayerWon = true;
      }

      // Changes speed of snow and rate the players score increases
      if (blnSnowSlow) {
        fltSnowSpeed = 1;
        intScoreIncreaseRate = 1;
      } else if (blnSnowFast) {
        fltSnowSpeed = 5;
        intScoreIncreaseRate = 5;
      } else {
        fltSnowSpeed = 3;
        intScoreIncreaseRate = 3;
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

      // Stops player from leaving the screen horizontally
      if (fltPlayerX <= 0) {
        fltPlayerX = 0;
      } else if (fltPlayerX + intPlayerW >= intScreenW) {
        fltPlayerX = intScreenW - intPlayerW;
      }

      // Stops player from leaving the top of the screen
      if (fltPlayerY <= 0) {
        fltPlayerY = 0;
      }

      // Checks if player has left the safe zone
      if (fltPlayerY + intPlayerH <= fltSafeZone) {
        blnInsideZone = false;
      }

      // Stops player from leaving the bottom of the screen
      if (blnInsideZone) {
        if (fltPlayerY + intPlayerH >= intScreenH) {
          fltPlayerY = intScreenH - intPlayerH;
        }
      } else if (!blnInsideZone) {
        // Stops player from returning to the safe zone
        if (fltPlayerY + intPlayerH >= fltSafeZone - 5) { // -5 is the width of the safe zone line
          fltPlayerY = fltSafeZone - intPlayerH - 5;
        }
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
    } else {
      // The start screen
      image(imgBG, 0, 0);

      // Sets text colour
      fill(255);

      // Title
      textSize(intTitleSize);
      textAlign(CENTER, CENTER);
      text("Welcome To Santa's Workshop!", 400, 40);

      // Story
      textSize(intBodySize);
      textAlign(LEFT, CENTER);
      textLeading(25);
      text(
          "The North Pole has become victim to a blizard, Santa is stuck outside and has\nlost the keys to his workshop. He is forced to stay outside and wait out the\nstorm, control Santa and avoid touching the snowflakes until the storm is over.\nGood Luck!",
          22, 125);

      // Heading
      textSize(intHeadingSize);
      text("Instructions:", 22, 220);

      // Instructions
      textSize(intBodySize);
      text(
          "1. Move Santa with the wasd keys\n2. The up & down arrows alter the snow's speed & the rate your score increases\n3. Clicking on snowflakes will make them disappear & increase your score\n4. You have 3 lives, you lose lives when touching a snowflake\n5. The game ends when you run out of lives, or if your score hits " + intScore + "!",
          22, 325);

      // The interval between each colour change
      intTime += 30; // 30 frames persecond

      // Calculates when it is time to change colour and changes the colour
      if (intTime < 1000) { // 1000 is the amount of times the code runs before switching the colour
        intTextColour = 255;
      } else if (intTime < 2000 && intTime >= 1000) { // 2000 is the interval doubled
        intTextColour = 0;
      } else {
        intTime = 0;
      }

      // Start prompt
      textAlign(CENTER, CENTER);
      fill(intTextColour);
      text("Press 'SPACE' to start", 400, 605);

      // Moves Santa back and forth
      fltSantaX += intSantaSpeed;
      if (fltSantaX + 100 >= intScreenW || fltSantaX <= 0) { // 100 is the width of Santa
        intSantaSpeed = intSantaSpeed * -1;
      }
      image(imgSanta, fltSantaX, fltSantaY);
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

    // Checks if the game has started
    if (key == ' ') {
      blnGameStart = true;
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
   * Resets snowflake's position that were clicked on and increases your score
   */
  public void mousePressed() {
    for (int i = 0; i < intSnowCount; i++) {
      if (snow[i].getSnowflakePositionX() < mouseX
          && snow[i].getSnowflakePositionX() + intSnowSize > mouseX
          && snow[i].getSnowflakePositionY() + intSnowSize > mouseY
          && snow[i].getSnowflakePositionY() < mouseY) {
        // Resets the snowflakes position
        snow[i].setSnowflakePosition(random(width), 0);

        // Checks if player in out of the safe zone so the score can increase
        if (fltPlayerY < fltSafeZone) {
          intCurrentScore += 50; // 50 is how much your score increases
        }
      }
    }
  }
}