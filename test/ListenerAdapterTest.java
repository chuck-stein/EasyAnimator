import static org.junit.Assert.*;

import cs3500.animator.adapter.ListenerAdapter;
import cs3500.animator.adapter.ViewAdapter;
import cs3500.animator.controller.EasyAnimatorController;
import cs3500.animator.controller.EditorListener;
import cs3500.animator.controller.MockEditorListener;
import cs3500.animator.model.hw05.EasyAnimatorModel;
import cs3500.animator.provider.controller.Commands;
import cs3500.animator.provider.model.Shapes;
import cs3500.animator.provider.view.EditableView;
import cs3500.animator.provider.view.IView;
import cs3500.animator.view.AnimationEditorView;
import cs3500.animator.view.IEasyAnimatorView;
import java.awt.event.ActionEvent;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the adapter from the EditorListener interface to the provided equivalent, the
 * Commands interface.
 */
public class ListenerAdapterTest extends ListenerTest {
  private Commands listenerAdapt;

  @Before
  public void init() {
    output = new StringBuilder();
    m = new EasyAnimatorModel();
    IEasyAnimatorView v = new ViewAdapter(100, 100, 500, 500);

    editorListener = new EasyAnimatorController(v, m, 60);
    actionListener = new AnimationEditorView(200, 200, 300, 300);

    EditorListener mock = new MockEditorListener(output);
    ((AnimationEditorView) actionListener).setListener(mock);

    listenerAdapt = new ListenerAdapter(mock);
  }

  @Test
  public void testTogglePlaybackTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.pause();
    assertEquals("pause\n", output.toString());
    listenerAdapt.pause();
    assertEquals("pause\nplay\n", output.toString());
    listenerAdapt.pause();
    assertEquals("pause\nplay\npause\n", output.toString());
  }

  @Test
  public void testRestartTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.reset();
    assertEquals("restart\n", output.toString());
  }

  @Test
  public void testToggleLoopingTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.loop();
    assertEquals("looping on\n", output.toString());
    listenerAdapt.loop();
    assertEquals("looping on\nlooping off\n", output.toString());
    listenerAdapt.loop();
    assertEquals("looping on\nlooping off\nlooping on\n", output.toString());
  }

  @Test
  public void testSlowDownTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.slower();
    assertEquals("slow down\n", output.toString());
  }

  @Test
  public void testSpeedUpTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.faster();
    assertEquals("speed up\n", output.toString());
  }

  @Test
  public void testAddShapeTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.createShape("Cool", Shapes.RECTANGLE);
    assertEquals("add shape\n", output.toString());
  }

  @Test
  public void testRemoveShapeTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.shapeDel("Cool");
    assertEquals("remove shape\n", output.toString());
  }

  @Test
  public void testInsertKeyFrameTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.createKeyFrame("R",3);
    assertEquals("insert keyframe\n", output.toString());
  }

  @Test
  public void testRemoveKeyFrameTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.frameDel("R",3);
    assertEquals("remove keyframe\n", output.toString());
  }

  @Test
  public void testSaveFileTrigger() {
    assertEquals("", output.toString());
    listenerAdapt.save("save", "SVG");
    assertEquals("save file\n", output.toString());
  }

  @Test
  public void testLoadFileTrigger() {
    assertEquals("", output.toString());
   listenerAdapt.load("HELP");
   assertEquals("load file\n", output.toString());
  }

  @Test
  public void testSetTime() {
    assertEquals("", output.toString());
    listenerAdapt.goTo(2);
    assertEquals("setTime\n", output.toString());
  }

}

