package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * Represents a view that displays in a moving image. Draws shapes on a canvas, of given size, and
 * draws them moving to a given tick per second.
 */
public final class VisualEasyAnimatorView extends ASwingAnimatorView implements IEasyAnimatorView {

  private Timer timer;
  private int tick;
  private int ticksPerSecond;


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
    this.ticksPerSecond = ticksPerSecond;
    this.tick = 0;
    this.timer = new Timer();
    this.setTitle("Animation Playback");
    this.pack();
    this.setLocationRelativeTo(null); // center the frame
  }

  @Override
  public void animate() {
    this.setVisible(true);
    TimerTask advanceTime = new TimerTask() {
      @Override
      public void run() {
        updateImage();
      }
    };
    timer.schedule(advanceTime, 0, 1000 / ticksPerSecond);
  }

  /**
   * Draws the current state of the image then advances it by one tick.
   */
  private void updateImage() {
    this.repaint();
    this.tick++;
    shapePanel.updateTick(tick);
  }
}
