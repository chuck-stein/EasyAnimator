package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Represents a view that displays in a moving image. Draws shapes on a canvas, of given size, and
 * draws them moving to a given tick per second.
 */
public class SwingBasedEasyAnimatorView extends JFrame implements IEasyAnimatorView {

  private ShapePanel shapePanel;
  private Timer timer;

  private int ticksPerSecond;

  /**
   * Creates this type of imaged based animation according to certain parameters.
   * @param canvasX how far to move the origin in the x direction.
   * @param canvasY how far to move the origin in the y direciton.
   * @param canvasWidth how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param ticksPerSecond how fast to animate the image.
   */
  public SwingBasedEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
      int ticksPerSecond) {
    super();

    this.timer = new Timer();

    this.ticksPerSecond = ticksPerSecond;

    this.setTitle("The Animation!");
    this.setSize(canvasWidth, canvasHeight);

    this.setLayout(new BorderLayout());
    shapePanel = new ShapePanel(-canvasX, -canvasY);
    shapePanel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

    JScrollPane scrollBarAndPane = new JScrollPane(shapePanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollBarAndPane, BorderLayout.CENTER);
    this.pack();

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

  @Override
  public void setShapes(List<IReadableShape> shapes) {
    shapePanel.setShapes(shapes);
  }

  /**
   * Draws the current state of the image then advances it by one tick.
   */
  private void updateImage() {
    this.repaint();
    shapePanel.updateTick();
  }
}
