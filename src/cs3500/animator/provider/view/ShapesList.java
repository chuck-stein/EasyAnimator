package cs3500.animator.view;

import cs3500.animator.model.IEasyAnimatorViewer;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 * A JList that represents the list of shapes in an {@code IEasyAnimatorViewer}.
 */
final class ShapesList extends JList<String> {

  /**
   * A constructor that uses the given model to extract the list of shapes.
   *
   * @param model the {@code IEasyAnimator} which the list of shapes will be extracted from.
   */
  ShapesList(IEasyAnimatorViewer model) {
    super(model.getAllShapeNames().toArray(new String[0]));
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setLayoutOrientation(JList.VERTICAL);
    this.setValueIsAdjusting(true);
  }
}
