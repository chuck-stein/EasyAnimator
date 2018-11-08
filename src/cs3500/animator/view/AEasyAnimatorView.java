package cs3500.animator.view;

import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.ReadableShape;
import java.util.List;

public abstract class AEasyAnimatorView implements IEasyAnimatorView{

  protected Appendable output;
  protected List<IReadableShape> shapes;
  protected int canvasX;
  protected int canvasY;
  protected int canvasWidth;
  protected int canvasHeight;
  protected int ticksPerSecond;

  public AEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight, int ticksPerSecond, Appendable output) {



    this.canvasX = canvasX;
    this.canvasY = canvasY;
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;
    this.ticksPerSecond = ticksPerSecond;
    this.output = output;
  }


  @Override
  public void setShapes(List<IReadableShape> shapes) {
    this.shapes = shapes;
  }



}
