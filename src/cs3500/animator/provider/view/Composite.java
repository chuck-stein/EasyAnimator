/**
 *
 */

import javax.swing.Timer;
import java.awt.Color;


/**
 * View used by user to edit the Animation while it is occuring.
 */
public class Composite {

  Boolean looping = false;
  IView view;
  Timer timer = new Timer(1, new KeyBoardListener());

  /**
   * Constructor for Composite.
   *
   * @param view will take in a view used to display Animation.
   */
  public Composite(IView view) {
    this.view = view;

  }

  /**
   * Start the animation Time.
   *
   * @return inform user through string saying the animation has begun.
   */
  public String start() {
    timer.start();
    return "Animation begins";
  }

  /**
   * Will pause animation time.
   *
   * @return String saying that the animation has paused
   */
  public String pause() {
    timer.setInitialDelay(100);
    return "Animation will pause for 100 seconds";
  }

  /**
   * Will Restart the animation.
   *
   * @return inform user through string saying the animation has continued.
   */
  public String restart() {
    timer.setInitialDelay(0);
    return "Animation is unpaused";

  }


  /**
   * If looping is occuring stop, else turn on looping.
   *
   * @return a String telling the user what has occurred.
   */
  public String loopingSwitch() {

    if (looping) {
      looping = false;
      return "Looping Disabled";
    } else {
      looping = true;
      return "Looping Enabled";
    }

  }

  /**
   * Increase the speed of the animation.
   *
   * @param i is the speed the animation speed is being increased by.
   * @return a string letting the user know that the speed has increased an appropriate amount.
   */
  public String increaseSpeed(int i) {

    if (i <= 0) {
      throw new IllegalArgumentException("Invalid Speed");
    }
    return "Speed Increased" + Integer.toString(i);
  }

  /**
   * Decrease the speed of an animation.
   *
   * @param i is the amount the speed is being decreased by.
   * @return a string letting the user know that the speed has decreased by the inputted amount.
   */
  public String decreaseSpeed(int i) {
    if ((timer.speed - i <= 0) || (i <= 0)) {
      throw new IllegalArgumentException("Invalid Speed");
    }
    timer.speed = (timer.speed - i);
    return "Speed decreased by " + Integer.toString(i);
  }

  /**
   * Add a shape to the animation.
   *
   * @param name is the name of the to be created shape.
   * @param type is the type of the to be created shape.
   * @return a string letting the user know that the shape was created.
   */
  public String createShape(String name, String type) {

    animation.addShape(name, type);
    return "Shape " + name + " was created";
  }

  /**
   * Delete a shape from animation.
   *
   * @return a string letting the user know that the shape was created.
   */
  public String deleteShape(String name) {
    animation.delete(name);
    return "Shape " + name + " was deleted";
  }

  /**
   * Creates A Motioin.
   *
   * @param s is the shape that the motion will be added too.
   * @param start When the motion will begin.
   * @param end when the motion will end.
   * @param p where the motion will end.
   * @param w what the ending width will be.
   * @param h what the ending height will be.
   * @param c what the ending color will be.
   * @return a string letting the user know that the motion has been added.
   */
  public String addMotion(Shape s, int start, int end, Posn p, int w, int h, Color c) {

    animation.addMotion(s, start, end, p, w, h, c);
    return "Motion has been added";


  }

  /**
   * Deletes a motion.
   *
   * @param m motion to be dleted.
   * @return a string confirming deletion.
   */
  public String deleteMotion(Motion m) {
    animation.deleteFromMap(m);
    return "Motion has been deleted";

  }
}
