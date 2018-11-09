package cs3500.animator.view;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;
import java.io.IOException;
import java.util.List;

/**
 * Represents a basic view type. This view outputs the information in a text style.
 */
public class TextEasyAnimatorView extends AEasyAnimatorView {

  /**
   * Creates this type of text based animation according to certain parameters. All canvas and tick
   * animation is displayed, but not animated since its a text view.
   *
   * @param canvasX how far to move the origin in the x direction.
   * @param canvasY how far to move the origin in the y direciton.
   * @param canvasWidth how wide to make the canvas.
   * @param canvasHeight how tall to make the canvas.
   * @param ticksPerSecond how fast to animate the image.
   * @param output where to display the text.
   */
  public TextEasyAnimatorView(int canvasX, int canvasY, int canvasWidth,
                              int canvasHeight, int ticksPerSecond, Appendable output) {
    super(canvasX, canvasY, canvasWidth, canvasHeight, ticksPerSecond, output);
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

    for (IReadableShape shape : shapes) {
      motions = shape.getMotions();

      if (motions.size() > 0) {
        motionsForOutput.append("shape ");
        motionsForOutput.append(shape.getName());
        motionsForOutput.append(" ");
        motionsForOutput.append(shape.getType().toString().toLowerCase());
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
      output.append(outputString.substring(0, outputString.length() - 1));
    } catch (IOException e) {
      throw new IllegalStateException("Output has closed.");
    }


  }


}
