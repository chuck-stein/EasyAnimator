package cs3500.animator.view;

import cs3500.animator.model.hw05.ReadableShape;
import java.util.List;

public abstract class ATextBasedEasyAnimatorView implements IEasyAnimatorView{

  Appendable output;
  List<ReadableShape> shapes;

  public void setOutput(Appendable output) {
    this.output = output;
  }

  @Override
  public void setShapes(List<ReadableShape> shapes) {
    this.shapes = shapes;
  }

}
