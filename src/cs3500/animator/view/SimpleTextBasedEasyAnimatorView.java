package cs3500.animator.view;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IShape;
import java.io.IOException;
import java.util.List;

public class SimpleTextBasedEasyAnimatorView extends AEasyAnimatorView {


  public SimpleTextBasedEasyAnimatorView() {
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

        for (int i = 0; i <= motions.size() - 1; i++) {
          motionsForOutput.append("motion ");
          motionsForOutput.append(shape.getName());
          motionsForOutput.append(" ");
          motionsForOutput.append(motions.get(i).toString());
          if (i < motions.size() - 1) {
            motionsForOutput.append("\n");
          }
        }
      }
      motionsForOutput.append("\n");
    }
    try {
      String outputString = motionsForOutput.toString();
      output.append(outputString.substring(0,outputString.length()-1));
    } catch (IOException e) {
      e.printStackTrace();
    }


  }




}
