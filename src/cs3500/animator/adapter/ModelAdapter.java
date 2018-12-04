package cs3500.animator.adapter;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.IState;
import cs3500.animator.provider.model.IEasyAnimatorViewer;
import cs3500.animator.provider.model.Posn;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.model.Size;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A pseudo-adapter from our representation of read-only model info (a list of IReadableShapes) to
 * the provided interface IEasyAnimatorViewer, which also represents read-only model info. The
 * purpose of this "adapter" is to use our model implementation under the provided interface, which
 * is necessary when passing model information to the provided view. Also contains a Rectangle
 * representing canvas settings, to match the provided data structure for canvas settings.
 */
public class ModelAdapter implements IEasyAnimatorViewer {

  private List<IReadableShape> shapes;
  private Rectangle canvas;

  /**
   * Constructs a blank ModelAdapter, with no shapes and default canvas settings of 0x0 at (0, 0).
   */
  ModelAdapter() {
    shapes = new ArrayList<IReadableShape>();
    canvas = new Rectangle();
  }

  /**
   * Sets the info of this ModelAdapter to the given list of shapes and canvas settings.
   *
   * @param shapes the list of read-only shapes representing the animation info.
   * @param canvas a Rectangle representing the position and dimensions of the animation canvas.
   * @throws IllegalArgumentException if the given list of shapes or canvas settings is null.
   */
  void setModelInfo(List<IReadableShape> shapes, Rectangle canvas)
          throws IllegalArgumentException {
    if (Objects.isNull(shapes) || Objects.isNull(canvas)) {
      throw new IllegalArgumentException("Cannot set the model to have a null list of shapes or " +
              "null canvas settings.");
    }
    this.shapes = shapes;
    this.canvas = canvas;
  }


  @Override
  public Color getCurrentColor(String name, int time) {
    for (IReadableShape shape : shapes) {
      if (shape.getName().equals(name)) {
        IState currentState = shape.getCurrentState(time);
        return new Color(currentState.getColorR(), currentState.getColorG(),
                currentState.getColorB());
      }
    }

    return null;
  }

  @Override
  public Posn getCurrentPosn(String name, int time) {
    for (IReadableShape shape : shapes) {
      if (shape.getName().equals(name)) {
        IState currentState = shape.getCurrentState(time);
        return new Posn((int) Math.round(currentState.getPositionX()),
                (int) Math.round(currentState.getPositionY()));
      }
    }
    return null;
  }

  @Override
  public Size getCurrentSize(String name, int time) {
    for (IReadableShape shape : shapes) {
      if (shape.getName().equals(name)) {
        IState currentState = shape.getCurrentState(time);
        return new Size((int) Math.round(currentState.getWidth()),
                (int) Math.round(currentState.getHeight()));
      }
    }

    return null;
  }

  @Override
  public boolean isVisibleAtTime(String name, int time) {
    for (IReadableShape shape : shapes) {
      if (shape.getName().equals(name)) {
        try {
          shape.getCurrentState(time);

        } catch (IllegalArgumentException e) {
          return false;
        }
        return true;
      }
    }

    return false;
  }

  @Override
  public List<Integer> getAllTimes(String name) {

      for (IReadableShape shape : shapes) {
        if (shape.getName().equals(name)) {
          return this.getAllKeyTimes(shape.getMotions());
        }
      }
      return null;
    }

  private List<Integer> getAllKeyTimes(List<IMotion> motions) {
    ArrayList<Integer> keyTimes = new ArrayList<>();
    for (IMotion motion : motions) {
      int startTime = motion.getStartTime();
      if (!keyTimes.contains(startTime)) {
        keyTimes.add(startTime);
      }

    }
    keyTimes.add(motions.get(motions.size()-1).getEndTime());
    return keyTimes;
  }

  @Override
  public List<String> getAllShapeNames() {
    List<String> names = new ArrayList<>();
    for (IReadableShape shape : shapes) {
      names.add(shape.getName());
    }

    return names;
  }

  @Override
  public Rectangle getDimensions() {
    return canvas;
  }

  @Override
  public Shapes getShapeType(String name) {
    for (IReadableShape shape : shapes) {
      if (shape.getName().equals(name)) {
        switch (shape.getType()) {
          case RECTANGLE:
            return Shapes.RECTANGLE;
          case ELLIPSE:
            return Shapes.ELLIPSE;
        }
      }
    }
    return null;
  }

  @Override
  public boolean isAnimationOverAtTime(int time) {


    for (IReadableShape shape : shapes) {
      if (shape.finalTick() >= time) {
        return false;
      }
    }
    return true;
  }
}
