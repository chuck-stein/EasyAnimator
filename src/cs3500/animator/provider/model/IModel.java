import java.awt.Color;
import java.util.List;

/**
 * Will serve as a model for Animations. Will hold all the information of shapes and motions. All
 * request a user can make. As well as a way for the view to get information about which motions are
 * occuring at given tick.
 */

public interface IModel {


  /**
   * Will create a shape and add to AllShapes list.
   *
   * @param name is the name of the shape.
   * @param type is the type of the shape.
   * @throws IllegalArgumentException if a shape already exist with that name
   */
  void addShape(String name, String type);

  /**
   * Deletes a shape and remove it from any lists that may contain shape.
   *
   * @param name is the name of the shape that will be deleted.
   * @throws IllegalArgumentException if given shape does not exist.
   */
  void delete(String name);

  /**
   * Will create a motion.
   *
   * @param s the shape that the motion will occur on.
   * @param start the start time of the motion.
   * @param end the end time of the motion.
   * @param endPos the ending position of the shape.
   * @param endWidth the ending width of the shape.
   * @param endHeight the ending height of the shape.
   * @param endColor the ending color of the shape.
   * @throws IllegalArgumentException if shape does not exist, or fields are invalid.
   */
  void addMotion(Shape s, int start, int end, Posn endPos,
      int endWidth, int endHeight, Color endColor);


  /**
   * get the time of the animation in the form of tick.
   *
   * @return the Tick of the animation.
   */
  Tick getAnimationTime();


  /**
   * To access all shapes that exist in an animation.
   *
   * @return a list of all shapes in the animation.
   */
  List<Shape> getAllShapes();

  /**
   * To get a list of all motions in animation.
   *
   * @return all motions that exist in an animation in the form of a list.
   */
  List<Motion> getAllMotions();

  /**
   * To get all frames at a given Tick. To be able to draw motions and shapes.
   *
   * @param t is the Tick.
   * @return a List of Frames at given tick.
   */
  List<Frame> getFramesAtTime(Tick t);

  /**
   * Will reveal all motions and shapes that exist in an animation.
   *
   * @return a string output with all shapes and their respective motions.
   */
  String allShapesAndMotions();


}


