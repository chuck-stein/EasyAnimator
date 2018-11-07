package cs3500.animator.view;

import cs3500.animator.model.hw05.ReadableShape;
import java.util.List;

public abstract class AEasyAnimatorView implements IEasyAnimatorView{

  protected Appendable output;
  protected List<ReadableShape> shapes;
  protected int canvasX;
  protected int canvasY;
  protected int canvasWidth;
  protected int canvasHeight;

  @Override
  public void setOutput(Appendable output) {
    this.output = output;
  }

  @Override
  public void setShapes(List<ReadableShape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void setCanvas(int x, int y, int w, int h) {
    canvasX = x;
    canvasY = y;
    canvasWidth = w;
    canvasHeight = h;
  }

 public void startTicking(int ticksPerSecond){

  }

}
