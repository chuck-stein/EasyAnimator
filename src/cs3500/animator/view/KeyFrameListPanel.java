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

class KeyFrameListPanel extends JPanel implements ListSelectionListener {

  private IReadableShape shape;
  private ArrayList<IState> keyframes;
  private JList<IState> keyFrameJlist;
 private JScrollPane scrollBarAndShapeList;
 private KeyFrameEditorPanel keyFrameEditor;

  KeyFrameListPanel() {
    super();

    this.keyframes = new ArrayList<>();
  }


  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()){
      keyFrameEditor.setKeyframe(keyFrameJlist.getSelectedValue());
    }
  }

  void setKeyFrameEditor(KeyFrameEditorPanel keyFrameEditor) {
    this.keyFrameEditor = keyFrameEditor;
  }

  void setShape(IReadableShape shape) {
    this.shape = shape;
    this.resetFrameList();

  }

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
