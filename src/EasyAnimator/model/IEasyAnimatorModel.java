package EasyAnimator.model;

import java.awt.Color;

/**
 * Represents for the model for an Easy Animator, which can create shapes and their states, and
 * output motions of shapes.
 */
public interface IEasyAnimatorModel {

  /**
   * Adds a Shape with the given characteristics to the animation.
   *
   * @param type the {@link ShapeType}
   * @param name the name of the Shape
   * @param color the starting {@link Color} of the Shape
   * @param position the starting {@link Position2D position} of the Shape
   * @param w the starting width of the Shape
   * @param h the starting height of the Shape
   * @throws IllegalArgumentException if any of the given Shape characteristics are invalid:
   * <p>-shape name already exists</p><p>-null color</p><p>-null
   * position</p><p>-dimensions are not positive</p>
   */
  void createShape(ShapeType type, String name, Color color, Position2D position, double w,
      double h)
      throws IllegalArgumentException;

  /**
   * Creates a State for a Shape to end up at after the given period of time.
   *
   * @param shapeName the name of the Shape
   * @param dt the change in time since the Shape's last State
   * @param color the updated {@link Color} of the Shape
   * @param position the updated {@link Position2D position} of the Shape
   * @param w the updated width of the Shape
   * @param h the updated height of the Shape
   * @throws IllegalArgumentException if the shape does not exist or if any of the given state
   * characteristics are invalid:
   * <p>-negative delta time</p><p>-null color</p><p>-null
   * position</p><p>-dimensions are not positive</p>
   */
  void createState(String shapeName, int dt, Color color, Position2D position, double w, double h)
      throws IllegalArgumentException;

  /**
   * Ats a state to the shape according to the specified commands. The duration or DeltaT is
   * required to be included in the specifications otherwise all others are optional, and order does
   * not matter. The options are as follows:
   * <p>"-deltaT #"(REQUIRED) sepcifies the duration of the motion that will result. </p>
   * <p>"-move # #" specifies how far the shapes moves in x y respectively.</p>
   * <p>"-changeColor # # #" specifies the new color the shape changes to by r g b respectively.</p>
   * <p>"-changeSize # #" specifies the factor to multiply the width and height by
   * respectively.</p>
   *
   * @param shapeName the name of the shape to receive the motion.
   * @param specifications the options for creating the motion
   * @throws IllegalArgumentException if shape does not exsist, if deltaT is not specified, or if
   * there are faulty strings in the specifications
   */
  void createStatePars(String shapeName, String specifications)
      throws IllegalArgumentException;

  /**
   * Creates a textual representation of all motions from one State to the next for each Shape in
   * the animation.
   *
   * @return a String listing all motions from one State to the next for each Shape in the
   * animation.
   */
  String getAllMotions();

  /**
   * Creates a textual representation of all Shape motions occurring at the given time.
   *
   * @param t the time in ticks at which motions should be returned
   * @return a String listing all Shape motions occurring at the given time.
   */
  String getCurrentMotions(int t);

}
