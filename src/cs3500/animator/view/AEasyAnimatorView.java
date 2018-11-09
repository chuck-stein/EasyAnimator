package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class for a simple view that contains shapes and outputs the motions to an
 * appendable. This view class can be extended for any view that follows the above.
 */
public abstract class AEasyAnimatorView implements IEasyAnimatorView {

  protected Appendable output;
  protected List<IReadableShape> shapes;
  protected int canvasX;
  protected int canvasY;
  protected int canvasWidth;
  protected int canvasHeight;
  protected int ticksPerSecond;

  /**
   * Constructs a basic view according to the given parameters.
   *
   * @param canvasX how far to move the origin in the x direction.
   * @param canvasY how far to move the origin in the y direction.
   * @param canvasWidth how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param ticksPerSecond how fast to animate the image.
   * @param output where to output the created view.
   * @throws IllegalArgumentException if width, height, or ticks are negative or if output is null.
   */
   AEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
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
