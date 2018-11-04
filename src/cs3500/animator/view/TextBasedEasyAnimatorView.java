package cs3500.animator.view;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IShape;
import java.io.IOException;
import java.util.List;

public class TextBasedEasyAnimatorView implements IEasyAnimatorView {

  Appendable output;
  List<IShape> shapes;



  @Override

  public void makeVisible() {

  }

  @Override
  public void refresh() {
    StringBuilder motionsForOutput = new StringBuilder();
    List<IMotion> motions;
    for (IShape shape : shapes) {
      motions = shape.getMotions();

      if (motions.size() > 0) {
        motionsForOutput.append("Shape ");
        motionsForOutput.append(shape.getName());
        motionsForOutput.append(" ");
        motionsForOutput.append(shape.getType().toString());
        motionsForOutput.append("\n");


        for (int i = 0; i < motions.size() - 1; i++) {

          motionsForOutput.append(motions.get(i).toString());
          if (i < motions.size() - 1) {
            motionsForOutput.append("\n");
          }
        }
      }
      try {
        output.append(motionsForOutput.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void setShapes(List<IShape> shapes) {
this.shapes = shapes;
  }


}