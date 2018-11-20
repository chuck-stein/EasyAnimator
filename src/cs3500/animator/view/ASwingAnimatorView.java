package cs3500.animator.view;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.animator.model.hw05.IReadableShape;
import javax.swing.border.Border;

/**
 * Represents the generic form of all Swing-based EasyAnimator views, such as the editor view and
 * the basic visual playback view.
 */
public abstract class ASwingAnimatorView extends JFrame implements IEasyAnimatorView {

  protected ShapePanel shapePanel;

  /**
   * Constructs an swing-based animation view with the given canvas and speed settings.
   *
   * @param canvasX        how far to move the origin in the x direction.
   * @param canvasY        how far to move the origin in the y direction.
   * @param canvasWidth    how wide to make the canvas.
   * @param canvasHeight   how tall to make the canvas.
   * @throws IllegalArgumentException if canvas dimensions or ticks per second are not positive.
   */
  public ASwingAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight) throws IllegalArgumentException {
    if (canvasWidth <= 0 || canvasHeight <= 0) {
      throw new IllegalArgumentException("Canvas dimensions must be positive.");
    }
    this.setSize(canvasWidth, canvasHeight);
    this.setLayout(new BorderLayout());
    shapePanel = new ShapePanel(-canvasX, -canvasY);
    shapePanel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    JScrollPane scrollBarAndPane = new JScrollPane(shapePanel,
        VERTICAL_SCROLLBAR_AS_NEEDED,
        HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
