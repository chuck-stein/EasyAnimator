package cs3500.animator.model.hw05;

public interface IState {

  /**
   * Returns the x value of the position.
   *
   * @return the x position of the state.
   */
  double getPositionX();

  /**
   * Returns the y value of the position.
   *
   * @return the y position of the state.
   */
  double getPositionY();

  /**
   * Returns the red value of the color.
   * @return the red value of the color
   */
  int getColorR();

  /**
   * Returns the green value of the color.
   * @return the green value of the color
   */
  int getColorG();

  /**
   * Returns the blue value of the color.
   * @return the blue value of the color
   */
  int getColorB();

  /**
   * Gets the width of this State.
   *
   * @return the width of this State
   */
  double getWidth();

  /**
   * Gets the height of this State.
   *
   * @return the height of this State
   */
  double getHeight();

  /**
   * Gets the time in ticks of this State.
   *
   * @return the time in ticks of this State
   */
  int getTick();

  /**
   * Outputs a textual representation of this State.
   *
   * @return a String representing this State's time, position, width,, and color
   */
  String getState();


}
