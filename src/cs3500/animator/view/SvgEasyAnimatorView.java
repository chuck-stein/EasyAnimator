package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.IState;
import cs3500.animator.model.hw05.ShapeType;

public class SvgEasyAnimatorView extends AEasyAnimatorView {


  public SvgEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight, int ticksPerSecond, Appendable output) {
    super(canvasX, canvasY, canvasWidth, canvasHeight, ticksPerSecond, output);
  }

  @Override
  public void animate() {
    try {
      output.append("<svg width=\"" + canvasWidth + "\" height=\"" + canvasHeight + "\" version=\"1" +
              ".1\"\n     xmlns=\"http://www.w3.org/2000/svg\">");
      for (IReadableShape s : shapes) {
        output.append(convertToSVG(s));
      }
      output.append("</svg>");
    } catch (IOException e) {
      throw new IllegalStateException("Output is not writable.");
    }
  }

  private String convertToSVG(IReadableShape s) {
    StringBuilder svg = new StringBuilder();
    IMotion firstMotion;
    if (s.getMotions().size() > 0) {
      firstMotion = s.getMotions().get(0);
    } else {
      return ""; //no svg code can be produced for a shape with no motions (need start state info)
    }
    IState init = firstMotion.getIntermediateState(firstMotion.getStartTime());
    switch (s.getType()) {
      case RECTANGLE:
        svg.append("<rect id=\"");
        svg.append(s.getName());
        svg.append("\" x=\"");
        svg.append(init.getPositionX());
        svg.append("\" y=\"");
        svg.append(init.getPositionY());
        svg.append("\" width=\"");
        svg.append(init.getWidth());
        svg.append("\" height=\"");
        svg.append(init.getHeight());
        svg.append("\" fill=\"rgb(");
        svg.append(init.getColorR());
        svg.append(",");
        svg.append(init.getColorG());
        svg.append(",");
        svg.append(init.getColorB());
        svg.append(")\" visibility=\"visible\" >\n");

        for (IMotion m : s.getMotions()) {
          svg.append(convertToSVG(m, s.getType()));
        }

        svg.append("</rect>\n");
        break;
      case ELLIPSE:
        break;
      default:

    }
    return svg.toString();
  }

  private String convertToSVG(IMotion m, ShapeType type) {
    String xName;
    String yName;
    String widthName;
    String heightName;
    switch (type) {
      case ELLIPSE:
        xName = "cx";
        yName = "cy";
        widthName = "rx";
        heightName = "ry";
        break;
      default:
        xName = "x";
        yName = "y";
        widthName = "width";
        heightName = "height";
    }
    StringBuilder svg = new StringBuilder();
    IState start = m.getIntermediateState(m.getStartTime());
    IState end = m.getIntermediateState(m.getEndTime());
    List<Boolean> attributeChanges = findChanges(start, end);
    for (int i = 0; i < attributeChanges.size(); i++) {
      if (attributeChanges.get(i)) {
        switch(i) {
          case 0:
            svg.append(svgAnimation(start.getTick(), end.getTick(), xName, start.getPositionX(),
                    end.getPositionX()));
            break;
          case 1:
            svg.append(svgAnimation(start.getTick(), end.getTick(), yName, start.getPositionY(), end.getPositionY()));
            break;
          case 2:
            svg.append(svgAnimation(start.getTick(), end.getTick(), widthName, start.getWidth(), end.getWidth()));
            break;
          case 3:
            svg.append(svgAnimation(start.getTick(), end.getTick(), heightName, start.getHeight(), end.getHeight()));
            break;
//          case 4:
//            svg.append(svgAnimation("fill", svgFill(start), svgFill(end)));
//            break;
        }
      }
    }
    return svg.toString();
  }

  private List<Boolean> findChanges(IState start, IState end) {
    List<Boolean> changes = new ArrayList<Boolean>();
    changes.add(differentValues(start.getPositionX(), end.getPositionX()));
    changes.add(differentValues(start.getPositionY(), end.getPositionY()));
    changes.add(differentValues(start.getWidth(), end.getWidth()));
    changes.add(differentValues(start.getHeight(), end.getHeight()));
    changes.add(differentValues(start.getColorR(), end.getColorR())
            || differentValues(start.getColorG(), end.getColorG())
            || differentValues(start.getColorB(), end.getColorB()));
    return changes;
  }

  private boolean differentValues(double val1, double val2) {
    return val1 != val2;
  }

  private String svgAnimation(int t1, int t2, String attributeName, double fromValue,
                              double toValue) {
    StringBuilder svg = new StringBuilder();
    svg.append("<animate attributeType=\"xml\" begin=\"");
    svg.append(toMS(t1));
    svg.append("\" dur=\"");
    svg.append(toMS(t2));
    svg.append("\" attributeName=\"");
    svg.append(attributeName);
    svg.append("\" from=\"");
    svg.append(fromValue);
    svg.append("\" to=\"");
    svg.append(toValue);
    svg.append("\" fill=\"freeze\" />");
    return svg.toString();
  }

  private String toMS(int t) {
    StringBuilder ms = new StringBuilder();
    ms.append(t * ticksPerSecond * 1000);
    ms.append("ms");
    return ms.toString();
  }

  /*
  <rect id="P" x="200" y="200" width="50" height="100" fill="rgb(128,0,128)" visibility="visible" >
    <!-- starting at time=1s, move the rectangle horizontally from x=200 to x=300 in 4 seconds -->
    <!-- fill=freeze keeps it there after the animation ends -->
    <animate attributeType="xml" begin="1000ms" dur="4000ms" attributeName="x" from="200" to="300" fill="freeze" />

    <!--add more animations here for this rectangle using animate tags -->
</rect>

<!--An orange ellipse named "E" with center at (500,100), x-radius 60 and y-radius 30 -->
<ellipse id="E" cx="500" cy="100" rx="60" ry="30" fill="rgb(255,128,0)" visibility="visible" >
    <!-- starting at time=2s, move the ellipse's center from (500,100) to (600,400) in 5 seconds -->
    <!-- fill=remove, which is the default if you don't specify it, brings the shape back to its original attributes after
    this animation is over -->
    <animate attributeType="xml" begin="2000.0ms" dur="5000.0ms" attributeName="cx" from="500" to="600" fill="remove" />
    <animate attributeType="xml" begin="2000.0ms" dur="5000.0ms" attributeName="cy" from="100" to="400" fill="remove" />
    <!--add more animations here for this ellipse using animate tags -->
</ellipse>
   */

}
