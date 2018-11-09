package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.ReadableShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class AEasyAnimatorView implements IEasyAnimatorView{

  protected Appendable output;
  protected List<IReadableShape> shapes;
  protected int canvasX;
  protected int canvasY;
  protected int canvasWidth;
  protected int canvasHeight;
  protected int ticksPerSecond;

  public AEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
                           int ticksPerSecond, Appendable output) throws IllegalArgumentException {
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be positive.");
    }
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("Ticks per second must be be positive.");
    }
    if (Objects.isNull(output)) {
      throw new IllegalArgumentException("Output cannot be null.");
    }
    this.shapes = new ArrayList<IReadableShape>();
    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.ticksPerSecond = ticksPerSecond;
    this.output = output;
  }

  @Override
  public void setShapes(List<IReadableShape> shapes) {
    this.shapes = shapes;
  }

}
