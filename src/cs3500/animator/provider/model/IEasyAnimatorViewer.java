package cs3500.animator.model;

import java.awt.Color;
import java.util.List;

/**
 * Represents the observations one can make on an IEasyAnimator. While you cannot expose the
 * implementation of the operations below, you can see the {@code Posn}, {@code Size}, and {@code
 * Color} of a shape at any given time, and also see a list of all of the shapes names, all of the
 * times of operations on the shape, the dimensions of the canvas that the shapes are laying on, and
 * get a shape's type. The model itself holds all of the shapes and all of the operations to be done
 * on them. An instance of the IEasyAnimator represents one set of shapes and the operations on
 * them.
 */
public interface IEasyAnimatorViewer {

  /**
   * Gets the color of a shape at the given time.
   *
   * @param name a {@code String} that is the name of the shape that the color will be found for
   * @param time an {@code int} that represents the time at which you want to find the color of the
   *             shape
   * @return the {@code Color} of the shape with the name {@code name} at the given {@code time}
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   * @throws IllegalArgumentException if the shape has not appeared yet at the given time
   */
  Color getCurrentColor(String name, int time);

  /**
   * Gets the position of a shape at the given time.
   *
   * @param name a {@code String} that is the name of the shape that the position will be found for
   * @param time an {@code int} that represents the time at which you want to find the position of
   *             the shape
   * @return the {@code Posn} of the shape with the name {@code name} at the given {@code time}
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   * @throws IllegalArgumentException if the shape has not appeared yet at the given time
   */
  Posn getCurrentPosn(String name, int time);

  /**
   * Gets the size of a shape at the given time.
   *
   * @param name a {@code String} that is the name of the shape that the size will be found for
   * @param time an {@code int} that represents the time at which you want to find the size of the
   *             shape
   * @return the {@code Size} of the shape with the name {@code name} at the given {@code time}
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   * @throws IllegalArgumentException if the shape has not appeared yet at the given time
   */
  Size getCurrentSize(String name, int time);

  /**
   * Determines whether or not a shape is visible at a given time.
   *
   * @param name a {@code String} that is the name of the shape that is being queried
   * @param time an {@code int} that represents the time that you want to see if the shape {@code
   *             name} exists
   * @return whether the shape with the name {@code name} at the given {@code time} exists
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   */
  boolean isVisibleAtTime(String name, int time);

  /**
   * Gets all of the start times of the shape with the given name.
   *
   * @param name the name of the shape which the start times will be found
   * @return a List with all of the start times of the given
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   */
  List<Integer> getAllTimes(String name);


  /**
   * Gets all of the shape names in {@code this} IEasyAnimator in the order that they were added in.
   *
   * @return a List with the name of all of the shapes in {@code this} IEasyAnimator
   */
  List<String> getAllShapeNames();

  /**
   * Returns an array with a length of 4, where the first argument is the x coordinate of the top
   * left corner, the second is the y coordinate of the top left corner, the third is the width of
   * the canvas, and the fourth is the height of the canvas
   *
   * @return an array that contains the coordinates of the top left corner and width/height of the
   * canvas
   */
  java.awt.Rectangle getDimensions();

  /**
   * Gets the name of the shapeType for the name that was requested.
   *
   * @param name The name of the shape tht the shapeType of will be returned
   * @return The shapeType of the shape requested
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalArgumentException if the shape with the given name doesn't exist
   */
  Shapes getShapeType(String name);

  /**
   * Determines if {@code this} instance of the {@code IEasyAnimatorViewer} has no more operations
   * to be performed on it.
   */
  boolean isAnimationOverAtTime(int time);


}
