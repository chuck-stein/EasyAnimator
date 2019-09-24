package cs3500.animator.view;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.model.hw05.IReadableShape;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * Represents the generic form of all Swing-based EasyAnimator views, such as the editor view and
 * the basic visual playback view.
 */
public abstract class ASwingAnimatorView extends JFrame implements IEasyAnimatorView {

  protected ShapePanel shapePanel;

  /**
   * Constructs an swing-based animation view with the given canvas and speed settings.
   *
   * @param canvasX      how far to move the origin in the x direction.
   * @param canvasY      how far to move the origin in the y direction.
   * @param canvasWidth  how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @throws IllegalArgumentException if canvas dimensions or ticks per second are not positive.
   */
  public ASwingAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight)
          throws IllegalArgumentException {
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
  public void setShapes(List<IReadableShape> shapes, boolean buttonResponse)
          throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      throw new IllegalArgumentException("Cannot set a null list of shapes.");
    }
    if (!buttonResponse) {
      shapePanel.setShapes(shapes);
    }
  }

  @Override
  public void resizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY) {
    this.setSize(new Dimension(canvasWidth, canvasHeight));

    shapePanel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    this.pack();
  }

  @Override
  public void setTicksPerSecond(int ticksPerSecond) {
    // no effect for this view type
  }

  @Override
  public void popUp(String msg, boolean isError) {
    if (isError) {

      JOptionPane
              .showMessageDialog(this, msg,
                      "ERROR",
                      JOptionPane.ERROR_MESSAGE);
    } else {
      JOptionPane
              .showMessageDialog(this, msg,
                      "INFO",
                      JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public void setLayers(List<List<IReadableShape>> layers) {
    List<IReadableShape> shapes = new ArrayList<>();
    for (List<IReadableShape> layer : layers) {
      shapes.addAll(layer);
    }

    shapePanel.setShapes(shapes);
  }

}
