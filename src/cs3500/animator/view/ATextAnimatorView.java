package cs3500.animator.view;

import cs3500.animator.controller.EditorListener;
import cs3500.animator.model.hw05.IReadableShape;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class for a simple view that contains shapes and outputs the motions to an
 * appendable. This view class can be extended for any view that follows the above. INVARIANTS:
 * shapes and output will not be null, canvas width and height will always be positive.
 */
public abstract class ATextAnimatorView implements IEasyAnimatorView {

  protected Appendable output;
  protected List<List<IReadableShape>> shapeLayers;
  protected int canvasX;
  protected int canvasY;
  protected int canvasWidth;
  protected int canvasHeight;
  protected int ticksPerSecond;
  protected boolean doneAnimating;

  /**
   * Constructs a basic view according to the given parameters.
   *
   * @param canvasX      how far to move the origin in the x direction.
   * @param canvasY      how far to move the origin in the y direction.
   * @param canvasWidth  how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param output       where to output the created view.
   * @throws IllegalArgumentException if width, height, or ticks are negative or if output is null.
   */
  ATextAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
                    Appendable output) throws IllegalArgumentException {
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be positive.");
    }
    if (Objects.isNull(output)) {
      throw new IllegalArgumentException("Output cannot be null.");
    }
    this.shapeLayers = new ArrayList<List<IReadableShape>>();
    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.output = output;
    this.doneAnimating = false;
  }

  @Override
  public void setShapes(List<IReadableShape> shapes, boolean buttonResponse) {
    // not necessary in this view
  }

  @Override
  public void setTime(int tick) {
    // no effect for this view type
  }

  @Override
  public void setTicksPerSecond(int ticksPerSecond) {
    if (ticksPerSecond < 1) {
      throw new IllegalArgumentException("Ticks per second must be be positive.");
    }
    this.ticksPerSecond = ticksPerSecond;
  }

  @Override
  public void setListener(EditorListener listener) {
    // no effect in this view
  }

  @Override
  public boolean doneAnimating() {
    return doneAnimating;
  }

  @Override
  public void resizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY) {
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
  }

  @Override
  public void popUp(String msg, boolean isError) {
    //does nothing not supported in text views.
  }

  @Override
  public void setLayers(List<List<IReadableShape>> layers) {
    shapeLayers = layers;
  }

}
