package cs3500.animator.view;

import cs3500.animator.controller.EditorListener;


/**
 * Represents a view that displays in a moving image. Draws shapes on a canvas, of given size, and
 * draws them moving to a given tick per second.
 */
public final class VisualEasyAnimatorView extends ASwingAnimatorView implements IEasyAnimatorView {

  /**
   * Constructs a VisualEasyAnimatorView with the given canvas and speed settings.
   *
   * @param canvasX how far to move the origin in the x direction.
   * @param canvasY how far to move the origin in the y direction.
   * @param canvasWidth how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param ticksPerSecond how fast to animate the image, in ticks per second.
   * @throws IllegalArgumentException if canvas dimensions or ticks per second are not positive.
   */
  public VisualEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
      int ticksPerSecond) throws IllegalArgumentException {
    super(canvasX, canvasY, canvasWidth, canvasHeight);
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("Ticks per second must be be positive.");
    }

    this.setTitle("Animation Playback");
    this.pack();
    this.setLocationRelativeTo(null); // center the frame
  }

  @Override
  public void animate() {
    this.setVisible(true);
    this.repaint();
  }

  @Override
  public void setTime(int tick) {
    shapePanel.updateTick(tick);
  }

  @Override
  public void setTicksPerSecond(int ticksPerSecond) {
    //not used in this view type.
  }

  @Override
  public void setListener(EditorListener listener) {
//not used in this view type.
  }

  @Override
  public boolean doneAnimating() {
    return false;
  }


}
