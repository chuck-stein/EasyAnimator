package cs3500.animator.view;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

import cs3500.animator.model.hw05.IMotion;
import cs3500.animator.model.hw05.IReadableShape;
import cs3500.animator.model.hw05.IState;

import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Represents a panel that has a list of keyframes in it. Sits inside the edit panel allowing
 * selection of keyframes
 */
class KeyFrameListPanel extends JPanel implements ListSelectionListener {

  private IReadableShape shape;
  private ArrayList<IState> keyframes;
  private JList<IState> keyFrameJlist;
  private JScrollPane scrollBarAndShapeList;
  private KeyFrameEditorPanel keyFrameEditor;

  /**
   * Constructs the list of keyframes panel.
   */
  KeyFrameListPanel() {
    super();

    this.keyframes = new ArrayList<>();
  }


  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      keyFrameEditor.setKeyframe(keyFrameJlist.getSelectedValue());
    }
  }

  /**
   * Sets the editor of keyframes that this list will send its keyframes to.
   * @param keyFrameEditor who will edit this keyframe.
   */
  void setKeyFrameEditor(KeyFrameEditorPanel keyFrameEditor) {
    this.keyFrameEditor = keyFrameEditor;
  }

  /**
   * Sets the shape that contains the keyframes to be displayed.
   * @param shape the shape with the keyframes to display.
   */
  void setShape(IReadableShape shape) {
    this.shape = shape;
    this.resetFrameList();

  }

  /**
   * When a new shape is selected the list changes to contain and show the new keyframes. Gets rid
   * of the old list as it is no longer needed.
   */
  private void resetFrameList() {

    keyframes.clear();
    ArrayList<IMotion> motions = new ArrayList<>(shape.getMotions());

    if (motions.size() > 0) {
      IMotion start = motions.get(0);
      keyframes.add(start.getIntermediateState(start.getStartTime()));
    }

    for (IMotion motion : motions) {
      IState keyframe = motion.getIntermediateState(motion.getEndTime());

      if (!keyframes.contains(keyframe)) {
        keyframes.add(keyframe);
      }
    }
    if (!Objects.isNull(scrollBarAndShapeList)) {
      this.remove(scrollBarAndShapeList);
    }
    keyFrameJlist = new JList(keyframes.toArray());
    scrollBarAndShapeList = new JScrollPane(keyFrameJlist, VERTICAL_SCROLLBAR_AS_NEEDED,
        HORIZONTAL_SCROLLBAR_AS_NEEDED);
    keyFrameJlist.addListSelectionListener(this);
    this.add(scrollBarAndShapeList);
  }
}
