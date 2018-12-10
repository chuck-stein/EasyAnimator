package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.IState;
import cs3500.animator.model.hw05.ShapeType;

/**
 * A view of the Easy Animator which outputs the animation as SVG code, which can run in a web
 * browser to display the animation.
 */
public final class SvgEasyAnimatorView extends ATextAnimatorView {

  /**
   * Constructs an SVG view with the given canvas settings, speed, and output appendable.
   *
   * @param canvasX      how far to move the origin in the x direction.
   * @param canvasY      how far to move the origin in the y direction.
   * @param canvasWidth  how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param output       where to output the created view.
   * @throws IllegalArgumentException if width, height, or ticks are negative or if output is null.
   */
  public SvgEasyAnimatorView(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
                             Appendable output)
          throws IllegalArgumentException {
    super(canvasX, canvasY, canvasWidth, canvasHeight, output);
  }

  @Override
  public void animate() {
    this.doneAnimating = true;
    try {
      output.append("<svg width=\"" + canvasWidth + "\" height=\"" + canvasHeight
              + "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");
      for (IReadableShape s : shapes) {
        output.append(convertToSVG(s));
      }
      output.append("</svg>");

    } catch (IOException e) {
      throw new IllegalStateException("Output is not writable.");
    }

  }

  /**
   * Converts a shape to the SVG readout for the shape.
   *
   * @param s the Shape.
   * @return the output in SVG of the shape.
   */
  private String convertToSVG(IReadableShape s) {
    String shapeType = "";
    String xName = "";
    String yName = "";
    String widthName = "";
    String heightName = "";

    switch (s.getType()) {
      case RECTANGLE:
        shapeType = "rect";
        xName = "x";
        yName = "y";
        widthName = "width";
        heightName = "height";
        break;
      case ELLIPSE:
        shapeType = "ellipse";
        xName = "cx";
        yName = "cy";
        widthName = "rx";
        heightName = "ry";
        break;
      default:
        //no other possibilities because type is an enum
    }

    StringBuilder svg = new StringBuilder();
    IMotion firstMotion;
    if (s.getMotions().size() > 0) {
      firstMotion = s.getMotions().get(0);
    } else {
      return ""; //no svg code should be produced for a shape with no motions(need start state info)
    }
    IState init = firstMotion.getIntermediateState(firstMotion.getStartTime());

    svg.append("<");
    svg.append(shapeType);
    svg.append(" id=\"");
    svg.append(s.getName());
    svg.append("\"");
    svg.append(svgAttribute(xName, init.getPositionX() - canvasX));
    svg.append(svgAttribute(yName, init.getPositionY() - canvasY));
    if (s.getType() == ShapeType.ELLIPSE) {
      svg.append(svgAttribute(widthName, init.getWidth() / 2));
      svg.append(svgAttribute(heightName, init.getHeight() / 2));
    } else {
      svg.append(svgAttribute(widthName, init.getWidth()));
      svg.append(svgAttribute(heightName, init.getHeight()));
    }
    svg.append(" fill=\"rgb(");
    svg.append(init.getColorR());
    svg.append(",");
    svg.append(init.getColorG());
    svg.append(",");
    svg.append(init.getColorB());
    svg.append(")\" visibility=\"hidden\" >\n");

    svg.append(turnVisible(firstMotion.getStartTime()));

    for (IMotion m : s.getMotions()) {
      svg.append(convertToSVG(m, s.getType()));
    }

    svg.append("</");
    svg.append(shapeType);
    svg.append(">\n");

    return svg.toString();
  }

  /**
   * Converts a motion to SVG.
   *
   * @param m    the motion to convert.
   * @param type the type of shape the motion belongs to.
   * @return the motion in an SVG read.
   */
  private String convertToSVG(IMotion m, ShapeType type) {
    String xName = "";
    String yName = "";
    String widthName = "";
    String heightName = "";
    switch (type) {
      case ELLIPSE:
        xName = "cx";
        yName = "cy";
        widthName = "rx";
        heightName = "ry";
        break;
      case RECTANGLE:
        xName = "x";
        yName = "y";
        widthName = "width";
        heightName = "height";
        break;
      default:
        // no other cases cause type is an enum
    }
    StringBuilder svg = new StringBuilder();
    IState start = m.getIntermediateState(m.getStartTime());
    IState end = m.getIntermediateState(m.getEndTime());
    List<Boolean> attributeChanges = findChanges(start, end);
    for (int i = 0; i < attributeChanges.size(); i++) {
      if (attributeChanges.get(i)) {
        switch (i) {
          case 0:
            svg.append(svgAnimation(start.getTick(), end.getTick(), xName,
                    start.getPositionX() - canvasX, end.getPositionX() - canvasX));
            break;
          case 1:
            svg.append(svgAnimation(start.getTick(), end.getTick(), yName,
                    start.getPositionY() - canvasY, end.getPositionY() - canvasY));
            break;
          case 2:
            if (type == ShapeType.ELLIPSE) {
              svg.append(svgAnimation(start.getTick(), end.getTick(), widthName,
                      start.getWidth() / 2, end.getWidth() / 2));
            } else {
              svg.append(svgAnimation(start.getTick(), end.getTick(), widthName, start.getWidth(),
                      end.getWidth()));
            }
            break;
          case 3:
            if (type == ShapeType.ELLIPSE) {
              svg.append(svgAnimation(start.getTick(), end.getTick(), heightName,
                      start.getHeight() / 2, end.getHeight() / 2));
            } else {
              svg.append(svgAnimation(start.getTick(), end.getTick(), heightName, start.getHeight(),
                      end.getHeight()));
            }
            break;
          case 4:
            svg.append(svgColorAnimation(start, end));
            break;
          case 5:
            svg.append(svgRotation(start, end));
            break;
          default:
            // no other cases, because attributeChanges is always set to a list of size 6
        }
      }
    }
    return svg.toString();
  }

  /**
   * Converts the given attribute and value to SVG code.
   *
   * @param attributeName  the name of the attribute being converted
   * @param attributeValue the value of the attribute being converted
   * @return a String representing the converted SVG code
   */
  private String svgAttribute(String attributeName, double attributeValue) {
    StringBuilder svg = new StringBuilder();
    svg.append(" ");
    svg.append(attributeName);
    svg.append("=\"");
    svg.append(attributeValue);
    svg.append("\"");
    return svg.toString();
  }

  /**
   * Creates an SVG animation tag turning visibility on at the given time.
   *
   * @param appearanceTime the time at which the appearance animation occurs
   * @return a String representing the SVG code
   */
  private String turnVisible(int appearanceTime) {
    StringBuilder svg = new StringBuilder();
    svg.append("<animate attributeType=\"xml\" begin=\"");
    svg.append(toMS(appearanceTime));
    svg.append("\" dur=\"1ms\" attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
            "fill=\"freeze\" />\n");
    return svg.toString();
  }


  /**
   * Determines which attributes have changed between the two states.
   *
   * @param start the first state being compared
   * @param end   the second state being compared
   * @return a list of boolean values representing whether or not the x, y, width, height, red,
   * green, blue, or angle has changed, in that order.
   */
  private List<Boolean> findChanges(IState start, IState end) {
    List<Boolean> changes = new ArrayList<Boolean>();
    changes.add(differentValues(start.getPositionX(), end.getPositionX()));
    changes.add(differentValues(start.getPositionY(), end.getPositionY()));
    changes.add(differentValues(start.getWidth(), end.getWidth()));
    changes.add(differentValues(start.getHeight(), end.getHeight()));
    changes.add(differentValues(start.getColorR(), end.getColorR())
            || differentValues(start.getColorG(), end.getColorG())
            || differentValues(start.getColorB(), end.getColorB()));
    changes.add(differentValues(start.getAngle(), end.getAngle()));
    return changes;
  }

  /**
   * Returns true if the given values are different.
   *
   * @param val1 the first value being compared
   * @param val2 the second value being compared
   * @return true if the given values are different
   */
  private boolean differentValues(double val1, double val2) {
    return val1 != val2;
  }

  /**
   * Creates SVG code representing an animation of the given attribute from the given start value
   * and time to the given end value and time.
   *
   * @param t1            the start time of the animation
   * @param t2            the end time of the animation
   * @param attributeName the attribute being animated
   * @param fromValue     the starting value of the attribute
   * @param toValue       the ending value of the attribute
   * @return a String representing the converted SVG code
   */
  private String svgAnimation(int t1, int t2, String attributeName, double fromValue,
                              double toValue) {
    StringBuilder svg = new StringBuilder();
    svg.append("<animate attributeType=\"xml\" begin=\"");
    svg.append(toMS(t1));
    svg.append("\" dur=\"");
    svg.append(toMS(t2 - t1));
    svg.append("\" attributeName=\"");
    svg.append(attributeName);
    svg.append("\" from=\"");
    svg.append(fromValue);
    svg.append("\" to=\"");
    svg.append(toValue);
    svg.append("\" fill=\"freeze\" />\n");
    return svg.toString();
  }

  private String svgRotation(IState start, IState end) {
    StringBuilder svg = new StringBuilder();
    svg.append("<animateTransform attributeType=\"xml\" attributeName=\"transform\" ");
    svg.append("type=\"rotate\" begin=\"");
    svg.append(toMS(start.getTick()));
    svg.append("\" from=\"");
    svg.append(start.getAngle());
    svg.append(" ");
    svg.append(start.getPositionX() + (start.getWidth() / 2));
    svg.append(" ");
    svg.append(start.getPositionY() + (start.getHeight() / 2));
    svg.append("\" to=\"");
    svg.append(end.getAngle());
    svg.append(" ");
    svg.append(end.getPositionX() + (end.getWidth() / 2));
    svg.append(" ");
    svg.append(end.getPositionY() + (end.getHeight() / 2));
    svg.append("\" dur=\"");
    svg.append(toMS(end.getTick() - start.getTick()));
    svg.append("\" />");
    return svg.toString();

//    <animateTransform attributeName="transform"
//    attributeType="XML"
//    type="rotate"
//    from="0 60 70"
//    to="360 60 70"
//    dur="10s"
//    repeatCount="indefinite"/>
  }

  /**
   * Converts the change in color between the given states into SVG code representing a color
   * animation.
   *
   * @param start the start state of the color change
   * @param end   the end state of the color change
   * @return a String representing the converted SVG code
   */
  private String svgColorAnimation(IState start, IState end) {
    StringBuilder svg = new StringBuilder();
    svg.append("<animate attributeType=\"xml\" begin=\"");
    svg.append(toMS(start.getTick()));
    svg.append("\" dur=\"");
    svg.append(toMS(end.getTick() - start.getTick()));
    svg.append("\" attributeName=\"fill\" values=\"rgb(");
    svg.append(start.getColorR());
    svg.append(",");
    svg.append(start.getColorG());
    svg.append(",");
    svg.append(start.getColorB());
    svg.append(");rgb(");
    svg.append(end.getColorR());
    svg.append(",");
    svg.append(end.getColorG());
    svg.append(",");
    svg.append(end.getColorB());
    svg.append(")\" fill=\"freeze\" />\n");
    return svg.toString();
  }

  /**
   * Converts the given time in ticks to milliseconds.
   *
   * @param t the time to convert
   * @return the time in ms
   */
  private String toMS(double t) {
    return t / ticksPerSecond * 1000 + "ms";
  }

}
