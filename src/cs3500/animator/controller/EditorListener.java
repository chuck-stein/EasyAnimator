package cs3500.animator.controller;

import cs3500.animator.model.hw05.ShapeType;

/**
 * Represents the listener for an animation editor, with all the methods that will be called when
 * the user interacts with the editing controls.
 */
public interface EditorListener {

  /**
   * Toggles whether or not the animation is currently paused or playing.
   */
  void togglePlayback();

  /**
   * Sets the animation back to the first tick.
   */
  void restart();

  /**
   * Toggles whether or not the animation automatically restarts upon completion.
   */
  void toggleLooping();

  /**
   * Decreases the animation speed by 5 ticks per second, with a minimum of 1.
   */
  void slowDown();

  /**
   * Increases the animation speed by 5 ticks per second.
   */
  void speedUp();

  /**
   * Adds a shape of the given type and name to the animation, or displays an error popup if the
   * specified shape name is taken.
   *
   * @param name the name of the shape to be added to the animation.
   * @param type the type of the shape to be added to the animation.
   */
  void addShape(String name, ShapeType type);

  /**
   * Removes the shape with the given name from the model, or displays an error popup if the given
   * name doesn't match any existing shapes.
   *
   * @param name the name of the shape to be deleted.
   */
  void removeShape(String name);

  /**
   * Removes the keyframe at the given time of the shape with the given name, by replacing the two
   * motions adjacent to it with one motion, or displays an error popup if the given shape name and
   * time do not match an existing keyframe.
   *
   * @param shapeName the name of the shape whose keyframe is being deleted
   * @param t         the time in ticks at which the keyframe which will be deleted occurs
   */
  void removeKeyframe(String shapeName, int t);

  /**
   * Adds a keyframe with the given specifications at the given time to the shape with the given
   * name, by replacing the motion occurring during that time with two separate motions divided by
   * the keyframe state. Displays an error popup if the specified shape does not exist, or a
   * keyframe cannot be added to it at the given time.
   *
   * @param shapeName the name of the shape to which a keyframe is being added
   * @param t         the time in ticks at which the keyframe which will be added
   */
  void insertKeyframe(String shapeName, int t);

  /**
   * Edits the keyframe at the given time for the shape with the given name to have the specified
   * values for color, position, and dimensions. Displays an error popup if any of the given
   * keyframe specifications are invalid, the specified shape does not exist, or it has no keyframe
   * at the given time.
   *
   * @param shapeName the name of the shape to which a keyframe is being added
   * @param t         the time in ticks at which the keyframe which will be added
   * @param x         the x-position of the keyframe state
   * @param y         the y-position of the keyframe state
   * @param w         the width of the keyframe state
   * @param h         the height of the keyframe state
   * @param r         the amount of red in the color of the keyframe state
   * @param g         the amount of green in the color of the keyframe state
   * @param b         the amount of blue in the color of the keyframe state
   */
  void editKeyframe(String shapeName, int t, int x, int y, int w, int h, int r, int g, int b);

}
