package cs3500.animator.adapter;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.controller.EditorListener;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.provider.view.EditableView;
import cs3500.animator.provider.view.IView;
import cs3500.animator.view.IEasyAnimatorView;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * An object adapter from IView to IEasyAnimatorView, so that we can use the provided view
 * implementation under our view interface, for easy integration with our current main class and
 * controller.
 */
public class ViewAdapter implements IEasyAnimatorView {

  private IView providerView;
  private ModelAdapter providerModel;
  private int tick;
  private Rectangle canvasInfo;

  /**
   * Constructs a ViewAdapter with the given canvas position and dimensions.
   *
   * @param canvasX      the x position of the canvas origin for the view
   * @param canvasY      the y position of the canvas origin for the view
   * @param canvasWidth  the width of the canvas for the view
   * @param canvasHeight the height of the canvas for the view
   */
  public ViewAdapter(int canvasX, int canvasY, int canvasWidth, int canvasHeight) {
    providerView = new EditableView();
    providerModel = new ModelAdapter();
    tick = 0;
    canvasInfo = new Rectangle(canvasX, canvasY, canvasWidth, canvasHeight);
    providerModel.setModelInfo(new ArrayList<IReadableShape>(), canvasInfo);
    providerView.display(providerModel);
    providerView.setModel(providerModel);
  }

  @Override
  public void animate() {
    providerView.refresh(tick);
  }

  @Override
  public void setShapes(List<IReadableShape> shapes, boolean buttonResponse)
          throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      throw new IllegalArgumentException("Cannot set shapes from a null list.");
    }

    providerModel.setModelInfo(shapes, canvasInfo);
    providerView.setModel(providerModel);
  }

  @Override
  public void setTime(int tick) {
    this.tick = tick;
  }

  @Override
  public void setTicksPerSecond(int ticksPerSecond) {
    // not necessary for this view
  }

  @Override
  public void setListener(EditorListener listener) throws IllegalArgumentException {
    providerView.setListener(new ListenerAdapter(listener), providerModel);
  }

  @Override
  public boolean doneAnimating() {
    return false;
  }

  @Override
  public void resizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY) {
    canvasInfo = new Rectangle(canvasX, canvasY, canvasWidth, canvasHeight);
  }

  @Override
  public void popUp(String msg, boolean isError) {
    if (isError) {
      JOptionPane
              .showMessageDialog((JFrame) providerView, msg,
                      "ERROR",
                      JOptionPane.ERROR_MESSAGE);
    } else {
      JOptionPane
              .showMessageDialog((JFrame) providerView, msg,
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

    this.setShapes(shapes, false);
  }
}
