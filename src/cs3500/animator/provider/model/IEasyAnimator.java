package cs3500.animator.model;

/**
 * Represents an interface in which users can interact with the EasyAnimator. Through this, a user
 * can define shapes, define operations on those shapes, and get the current color, size, and
 * position of a given shape at a given time. Requires {@link Posn}, {@link Size}, and {@link
 * java.awt.Color}. An IEasyAnimator holds all of the shapes in an animation, and the operations to
 * be done on them. The operations are how the shapes, (currently only ellipses and rectangles), and
 * their properties (such as position, size, and color), will change.
 *
 * <p>INVARIANT: Each shape must have a unique name</p>
 *
 * <p>INVARIANT: Operations should never overlap on a shape time-wise. E.g. you cannot define an
 * operation on a shape from time 0 to 10, and then define another operation on that same shape from
 * time 5 to 20.</p>
 */
public interface IEasyAnimator extends IEasyAnimatorViewer {


  /**
   * Creates a new shape with the given name of the type of shapeType.
   *
   * @param name the name of the shape that is to be created
   * @param shapeType the type of shape to be created
   * @throws IllegalArgumentException if any of the passed in parameters are null
   * @throws IllegalArgumentException if a shape has already been defined with the given {@code
   * name}
   * @throws IllegalArgumentException if the given {@code shapeType} doesn't exist
   */
  void addShape(String name, Shapes shapeType);

  /**
   * Adds an operation to the shape with the given name.
   *
   * @param name the name of the shape which this operation is to be added
   * @param startTime the starting time of this operation
   * @param startX the starting x coordinate of this operation
   * @param startY the starting y coordinate of this operation
   * @param startWidth the starting width of this operation
   * @param startHeight the starting height of this operation
   * @param startRed the starting red value of this operation
   * @param startGreen the starting green value of this operation
   * @param startBlue the starting blue value of this operation
   * @param endTime the end time of this operation
   * @param endX the end x coordinate of this operation
   * @param endY the end y coordinate of this operation
   * @param endWidth the ending width of this operation
   * @param endHeight the ending height of this operation
   * @param endRed the ending red value of ths operation
   * @param endGreen the ending red value of this operation
   * @param endBlue the ending blue value of this operation
   * @throws IllegalArgumentException if {@code name} is null
   * @throws IllegalArgumentException if a shape with {@code name} doesn't exist
   * @throws IllegalArgumentException if any of the times are negative
   * @throws IllegalArgumentException if the operation added overlaps with another one
   * @throws IllegalArgumentException if any of the sizes aren't positive
   * @throws IllegalArgumentException if any of the color parameters aren't within [0, 255]
   * @throws IllegalArgumentException if endTime is less than startTime
   */
  void addOperationToShape(String name, int startTime, int startX, int startY, int startWidth,
                           int startHeight, int startRed, int startGreen, int startBlue,
                           int endTime, int endX, int endY, int endWidth, int endHeight,
                           int endRed, int endGreen, int endBlue);

  /**
   * Creates a keyframe for the shape with the given name at the given time. The model will
   * automatically determine the appropriate values for the keyframe through tweening.
   *
   * @param name the name of the shape to have a keyframe to be added to
   * @param time the time at which the shape will have a keyframe added
   * @throws IllegalArgumentException if the given time is negative
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   * @throws IllegalArgumentException if the name given is null
   */
  void createKeyFrame(String name, int time);

  /**
   * Changes the keyframe of the shape with the given name at the given time.
   *
   * @param name the name of the shape to have its keyframe changed
   * @param time the time of the keyframe to be changed
   * @param x the x position that will be set
   * @param y the y position that will be set
   * @param width the width to be set
   * @param height the height to be set
   * @param red the red value to be set
   * @param green the green value to be set
   * @param blue the blue value to be set
   * @throws IllegalArgumentException if the given time is negative
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalArgumentException if the keyframe with the given time doesn't exist
   * @throws IllegalArgumentException if there aren't any keyframes in the shape
   */
  void changeKeyFrameAtTime(String name, int time, int x, int y, int width, int height, int red,
                            int green, int blue);

  /**
   * Deletes the shape with the given name.
   *
   * @param name the name of the shape to be deleted
   * @throws IllegalArgumentException if the shape with the given name doesn't exxist
   * @throws IllegalArgumentException if the given name is null
   */
  void deleteShape(String name);

  /**
   * Deletes the keyframe of the shape with the given name at the given time.
   *
   * @param name the name of the shape to be deleted
   * @param time the time of the keyframe to be deleted
   * @throws IllegalArgumentException if the keyframe with te given time doesn't exist
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   * @throws IllegalArgumentException if the name given is null
   * @throws IllegalArgumentException if the given time is negative
   * @throws IllegalArgumentException if the shape doesn't have any keyframes
   */
  void deleteKeyFrame(String name, int time);


}
