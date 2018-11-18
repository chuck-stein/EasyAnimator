package cs3500.animator.view;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.animator.model.hw05.IReadableShape;

/**
 * Represents the generic form of all Swing-based EasyAnimator views, such as the editor view and
 * the basic visual playback view.
 */
public abstract class ASwingAnimatorView extends JFrame implements IEasyAnimatorView {

  protected ShapePanel shapePanel;
  protected int ticksPerSecond;

  /**
   * Constructs an swing-based animation view with the given canvas and speed settings.
   *
   * @param canvasX        how far to move the origin in the x direction.
   * @param canvasY        how far to move the origin in the y direction.
   * @param canvasWidth    how wide to make the canvas.
   * @param canvasHeight   how tall to make the canvas.
   * @param ticksPerSecond how fast to animate the image, in ticks per second.
   * @throws IllegalArgumentException if canvas dimensions or ticks per second are not positive.
   */
  public ASwingAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
                            int ticksPerSecond) throws IllegalArgumentException {
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be positive.");
    }
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("Ticks per second must be be positive.");
    }
    this.ticksPerSecond = ticksPerSecond;
    this.setSize(canvasWidth, canvasHeight);
    this.setLayout(new BorderLayout());
    shapePanel = new ShapePanel(-canvasX, -canvasY);
    shapePanel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    JScrollPane scrollBarAndPane = new JScrollPane(shapePanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.add(scrollBarAndPane, BorderLayout.CENTER);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      throw new IllegalArgumentException("Cannot set a null list of shapes.");
    }
    shapePanel.setShapes(shapes);
  }

}
