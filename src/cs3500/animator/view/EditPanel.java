package cs3500.animator.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

final class EditPanel extends JPanel {

  private int canvasX;
  private int canvasY;

  /**
   * Constructs the edit panel at the given canvas location.
   *
   * @param canvasX how far to shift the origin in the x direction.
   * @param canvasY how far to shift the origin in the y direction.
   */
  EditPanel(int canvasX, int canvasY) {
    super();
    this.setBackground(Color.GRAY);
    this.canvasX = canvasX;
    this.canvasY = canvasY;
  }

}
