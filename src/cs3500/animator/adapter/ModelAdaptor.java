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

public class ModelAdaptor implements IEasyAnimatorViewer {

  private List<IReadableShape> modelInfo;
  private Rectangle dimensions;


  ModelAdaptor() {
    modelInfo = new ArrayList<>();
    dimensions = new Rectangle();


  }

  public void setModelInfo(List<IReadableShape> modelInfo, Rectangle dims) {
    this.modelInfo = modelInfo;
    this.dimensions = dims;
  }


  @Override
  public Color getCurrentColor(String name, int time) {
    for (IReadableShape shape : modelInfo) {
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
    for (IReadableShape shape : modelInfo) {
      if (shape.getName().equals(name)) {
        IState currentState = shape.getCurrentState(time);
        return new Posn((int) Math.round(currentState.getPositionX()),
            (int) Math.round(currentState.getPositionY()));
      }
    }
      return null;
    }

    @Override
    public Size getCurrentSize (String name,int time){
      for (IReadableShape shape : modelInfo) {
        if (shape.getName().equals(name)) {
          IState currentState = shape.getCurrentState(time);
          return new Size((int) Math.round(currentState.getWidth()),
              (int) Math.round(currentState.getHeight()));
        }
      }

      return null;
    }

    @Override
    public boolean isVisibleAtTime (String name,int time){
      for (IReadableShape shape : modelInfo) {
        if (shape.getName().equals(name)) {
          try {
            shape.getCurrentState(time);
            return true;
          } catch (IllegalArgumentException e) {
            return false;
          }
        }
      }

      return false;
    }

    @Override
    public List<Integer> getAllTimes (String name){

      for (IReadableShape shape : modelInfo) {
        if (shape.getName().equals(name)) {
          return this.getAllStartTimes(shape.getMotions());
        }
      }
      return null;
    }

  private List<Integer> getAllStartTimes(List<IMotion> motions) {
    ArrayList<Integer> startTimes = new ArrayList<>();
    for (IMotion motion : motions) {
      int startTime = motion.getStartTime();
      if (!startTimes.contains(startTime)) {
        startTimes.add(startTime);
      }

    }
    return startTimes;
  }

    @Override
    public List<String> getAllShapeNames () {
      List<String> names = new ArrayList<>();
      for (IReadableShape shape : modelInfo) {
        names.add(shape.getName());
      }

      return names;
    }

    @Override
    public Rectangle getDimensions () {
      return dimensions;
    }

    @Override
    public Shapes getShapeType (String name){
      for (IReadableShape shape : modelInfo) {
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
    public boolean isAnimationOverAtTime ( int time){


      for (IReadableShape shape : modelInfo) {
        if (shape.finalTick() >= time) {
          return false;
        }
      }
      return true;
    }
  }
