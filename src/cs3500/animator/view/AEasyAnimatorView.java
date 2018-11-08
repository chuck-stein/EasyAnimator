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
  protected int ticksPerSecond;
  protected Appendable getOutput;

  public AEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight, int ticksPerSecond, Appendable output) {



    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.ticksPerSecond = ticksPerSecond;
    this.output = output;
  }
  @Override
  public void setOutput(Appendable output) {
    this.output = output;
  }

  @Override
  public void setShapes(List<ReadableShape> shapes) {
    this.shapes = shapes;
  }



}
