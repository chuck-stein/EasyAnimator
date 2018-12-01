package cs3500.animator.controller;

/**
 * The commands that can be performed for the Easy Animator program. Controllers should implement
 * this interface to ensure they have the required operations.
 */
public interface Commands {

  /**
   * Toggles between pausing/playing a view.
   */
  void pause();

  /**
   * Resets a view to time 0.
   */
  void reset();

  /**
   * Toggles looping/not looping of a view.
   */
  void loop();

  /**
   * Makes a view startProgram faster.
   */
  void faster();

  /**
   * Makes a view startProgram slower. The speed can never be less than 1.
   */
  void slower();

  /**
   * Goes to the time passed in.
   *
   * @param time the time at which the view will startProgram to.
   */
  void goTo(int time);

  /**
   * Deletes the shape with the given name.
   *
   * @param name the name of the shape that will be deleted
   */
  void shapeDel(String name);

  /**
   * Deletes the frame given for the shape with the given name.
   *
   * @param name the name of the shape with the keyframe that will be deleted
   * @param time the keyframe that will be deleted
   */
  void frameDel(String name, int time);

  /**
   * Changes the keyframe at the given time for the shape with the given name with the given
   * values.
   *
   * @param name the name of the shape with the keyframe will be changed
   * @param time the time of the keyframe that will be changed
   * @param x the x value that wil be changed
   * @param y the y value that will be changed
   * @param width the width value that will be changed
   * @param height the height value that will be changed
   * @param red the red value that will be changed
   * @param green the green value that will be changed
   * @param blue the blue value that will be changed
   */
  void changeKeyFrame(String name, int time, int x, int y, int width, int height, int red,
                      int green, int blue);

  /**
   * Creates a shape with the given name of the given type.
   *
   * @param name the name of the shape to be created
   * @param type the type of the shape to be created
   */
  void createShape(String name, String type);

  /**
   * Creates a keyframe for the shape with the given name at the given time.
   *
   * @param name the name of the shape where the keyframe will be added
   * @param time the time at which the keyframe will be added
   */
  void createKeyFrame(String name, int time);

  /**
   * Saves the current animation to the file with the given name.
   *
   * @param name the name of the file where {@code this} animation is to be saved at
   */
  void save(String name, String type);

  /**
   * Loads the file with the given name.
   * @param name the name of the file that you're trying to load
   */
  void load(String name);

  /**
   * Tells this function to start displaying the given model.
   */
  public void startProgram();


}
