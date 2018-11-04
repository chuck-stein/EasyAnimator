package cs3500.animator.view;

import cs3500.animator.model.hw05.IShape;
import java.util.List;

public class TextBasedEasyAnimatorView implements IEasyAnimatorView {

  Appendable output;
  List<IShape> shapes;



  @Override

  public void makeVisible() {

  }

  @Override
  public void refresh() {
    for (IShape shape : shapes) {
      StringBuilder motionsForOutput = new StringBuilder();
      if (motions.size() > 0) {
        motionsForOutput.append("Shape ");
        motionsForOutput.append(name);
        motionsForOutput.append(" ");
        motionsForOutput.append(type.toString());
        motionsForOutput.append("\n");

        int currentTime;
        int nextTime;
        for (int i = 0; i < motions.size() - 1; i++) {
          if (i < motions.size() - 2) {
            currentTime = motions.get(i).getEndTime();
            nextTime = motions.get(i + 1).getStartTime();
            if (currentTime != nextTime) {
              throw new IllegalStateException(String.format(
                  "There can be no gaps in a Shapes Motions. There is a gap between time %d and %d.",
                  currentTime, nextTime));
            }
          }
          motionsForOutput.append(getMotion(i));
          if (i < motions.size() - 1) {
            motionsForOutput.append("\n");
          }
        }
      }
      return motionsForOutput.toString();
    }
  }

  @Override
  public void setShapes(List<IShape> shapes) {

  }
}
