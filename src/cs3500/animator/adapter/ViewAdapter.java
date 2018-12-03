package cs3500.animator.adapter;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.controller.EditorListener;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.provider.controller.Commands;
import cs3500.animator.provider.model.IEasyAnimatorViewer;
import cs3500.animator.provider.view.EditableView;
import cs3500.animator.provider.view.IView;
import cs3500.animator.view.IEasyAnimatorView;

public class ViewAdapter implements IEasyAnimatorView {

  IView providerView;
  ModelAdapter providerModel;
  private int tick;
  private int canvasWidth;
  private int canvasHeight;
  private int canvasX;
  private int canvasY;

  public ViewAdapter(int canvasX, int canvasY, int canvasWidth, int canvasHeight) {
    providerView = new EditableView();
    providerModel = new ModelAdapter();
    tick = 0;
    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
  }

  @Override
  public void animate() {
    providerView.refresh(tick);
  }

  @Override
  public void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      throw new IllegalArgumentException("Cannot set shapes from a null list.");
    }
    providerModel.setModelInfo(shapes, new Rectangle(canvasX, canvasY, canvasWidth, canvasHeight));
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
    if (!(listener instanceof Commands)) { //TODO: make sure instanceof handles null cases
      throw new IllegalArgumentException("Must use a ControllerAdapter when using a ViewAdapter.");
    }
    providerView.setListener((Commands) listener, providerModel);
  }

  @Override
  public boolean doneAnimating() {
    return false;
  }

  @Override
  public void resizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY) {
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.canvasX = canvasX;
    this.canvasY = canvasY;
  }
}
