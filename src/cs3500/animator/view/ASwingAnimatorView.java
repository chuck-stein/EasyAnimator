package cs3500.animator.view;

import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.animator.model.hw05.IReadableShape;

public abstract class ASwingAnimatorView extends JFrame implements IEasyAnimatorView {

  protected ShapePanel shapePanel;
  protected int ticksPerSecond;

  @Override
  public void setShapes(List<IReadableShape> shapes) throws IllegalArgumentException {
    if (Objects.isNull(shapes)) {
      throw new IllegalArgumentException("Cannot set a null list of shapes.");
    }
    shapePanel.setShapes(shapes);
  }

}
