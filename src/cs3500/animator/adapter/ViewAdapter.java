package cs3500.animator.adapter;

import java.awt.*;
import java.util.ArrayList;
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
  private Rectangle canvasInfo;
  private ListerningRelay listeneRelay;

  public ViewAdapter(int canvasX, int canvasY, int canvasWidth, int canvasHeight) {
    providerView = new EditableView();
    providerModel = new ModelAdapter();
    tick = 0;
    canvasInfo = new Rectangle(canvasX, canvasY, canvasWidth, canvasHeight);
    providerModel.setModelInfo(new ArrayList<IReadableShape>(), canvasInfo);
    providerView.display(providerModel);
    providerView.setModel(providerModel);
    listeneRelay = new ListerningRelay();
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
   listeneRelay.setListener(listener);
    providerView.setListener(listeneRelay, providerModel);
  }

  @Override
  public boolean doneAnimating() {
    return false;
  }

  @Override
  public void resizeCanvas(int canvasWidth, int canvasHeight, int canvasX, int canvasY) {
    canvasInfo = new Rectangle(canvasX, canvasY, canvasWidth, canvasHeight);
  }
}
