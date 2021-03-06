package cs3500.animator.view;

import java.io.IOException;
import java.util.List;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;

/**
 * Represents a basic view type. This view outputs the information of shapes and their motions by
 * giving a text representation of their end points.
 */
public final class TextEasyAnimatorView extends ATextAnimatorView {

  /**
   * Creates this type of text based animation according to certain parameters. All canvas and tick
   * animation is displayed, but not animated since its a text view.
   *
   * @param canvasX how far to move the origin in the x direction.
   * @param canvasY how far to move the origin in the y direction.
   * @param canvasWidth how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param output where to display the text.
   * @throws IllegalArgumentException if width, height, or ticks are negative or if output is null.
   */
  public TextEasyAnimatorView(int canvasX, int canvasY, int canvasWidth,
      int canvasHeight, Appendable output) throws IllegalArgumentException {
    super(canvasX, canvasY, canvasWidth, canvasHeight, output);
  }


  @Override
  public void animate() {
    StringBuilder motionsForOutput = new StringBuilder();
    List<IMotion> motions;
    motionsForOutput.append("canvas ");
    motionsForOutput.append(canvasX);
    motionsForOutput.append(" ");
    motionsForOutput.append(canvasY);
    motionsForOutput.append(" ");
    motionsForOutput.append(canvasWidth);
    motionsForOutput.append(" ");
    motionsForOutput.append(canvasHeight);
    motionsForOutput.append("\n");

    for (int layer = 0; layer < shapeLayers.size(); layer++) {
      System.out.println(layer);
      for (IReadableShape shape : shapeLayers.get(layer)) {
        motions = shape.getMotions();

        if (motions.size() > 0) {
          motionsForOutput.append("shape-at-layer ");
          motionsForOutput.append(shape.getName());
          motionsForOutput.append(" ");
          motionsForOutput.append(shape.getType().toString().toLowerCase());
          motionsForOutput.append(" ");
          motionsForOutput.append(layer);
          motionsForOutput.append("\n");

          for (int i = 0; i <= motions.size() - 1; i++) {
            IMotion m = motions.get(i);
            if (m.getIntermediateState(m.getStartTime()).getAngle() == 0
                    && m.getIntermediateState(m.getEndTime()).getAngle() == 0) {
              motionsForOutput.append("motion ");
            } else {
              motionsForOutput.append("rotation-motion ");
            }
            motionsForOutput.append(shape.getName());
            motionsForOutput.append(" ");
            motionsForOutput.append(motions.get(i).toString());
            if (i < motions.size() - 1) {
              motionsForOutput.append("\n");
            }
          }
          motionsForOutput.append("\n");
        }

      }
    }
    try {
      String outputString = motionsForOutput.toString();
      output.append(outputString.substring(0, outputString.length() - 1));
      this.doneAnimating = true;
    } catch (IOException e) {
      throw new IllegalStateException("Output has closed.");
    }


  }




}
