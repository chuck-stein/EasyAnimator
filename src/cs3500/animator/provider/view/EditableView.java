package cs3500.animator.view;

import cs3500.animator.controller.Commands;
import cs3500.animator.model.IEasyAnimatorViewer;
import cs3500.animator.model.Posn;
import cs3500.animator.model.Size;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/**
 * Represents a view that can be edited. Shapes can be added and deleted, and the view can be
 * started, stopped, reset, set to loop/not loop, and sped up/slowed down.
 */
public class EditableView extends JFrame implements IView {

  ShapesList shapeList;
  private ButtonPanel buttons = new ButtonPanel();
  private CellPanel canvas;

  JList<Integer> keyframes;
  private String shapeCurrentlyChosen;

  private ListSelectionListener curListener;

  JTable keyFrameTable;
  private KeyFrameTableModel modelTable;

  JTextField addShapeName;
  JTextField changeTime;
  private JTextField saveFileName;

  private JScrollPane animationPane;
  JTextField loadFileName;


  /**
   * The default constructor that initializes the {@code JFrame} for this {@code EditableView}. It
   * sets the title, layout, makes sure it closes on exit, and creates the initial CellPanel.
   */
  public EditableView() {
    super();
    this.setTitle("Not so ExCELLent");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());

    canvas = new CellPanel();
    canvas.setBackground(Color.WHITE);

  }


  @Override
  public void display(IEasyAnimatorViewer model) {
    if (model == null) {
      throw new IllegalArgumentException("Given model was null");
    }

    // Sets up panel in center
    Rectangle rect = model.getDimensions();
    this.setPreferredSize(new Dimension(rect.width + 600, rect.height + 600));
    canvas = new CellPanel();
    canvas.setPreferredSize(new Dimension(rect.width, rect.height));

    JViewport view = new JViewport();
    view.setSize(rect.width, rect.height);
    view.setView(canvas);
    view.setViewPosition(new Point(rect.x, rect.y));

    animationPane = new JScrollPane(view);
    animationPane.setWheelScrollingEnabled(true);
    animationPane.setBackground(Color.WHITE);
    animationPane.setPreferredSize(new Dimension(rect.width, rect.height));
    animationPane.setViewport(view);
    animationPane.setMaximumSize(new Dimension(500, 500));

    // List of shapes
    shapeList = new ShapesList(model);
    shapeList.addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        if (model.getAllShapeNames().size() >= shapeList.getSelectedIndex()
                && shapeList.getSelectedIndex() != -1) {
          String localString = shapeList.getSelectedValue();
          // buttons.deleteShape.setShape(localString);
          this.updateKeyFrameList(model, localString);
          shapeCurrentlyChosen = localString;
          canvas.setCurrentlyChosen(localString);
          canvas.repaint();
        } else if (shapeList.getSelectedIndex() == -1) {
          this.updateKeyFrameList(model, null);
        }
      }
    });

    ExCELLenceScrollPane listScroller = new ExCELLenceScrollPane("Shapes", shapeList);

    // List of keyframes
    keyframes = new JList<>();
    keyframes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    keyframes.setValueIsAdjusting(true);

    ExCELLenceScrollPane keyframeScroller = new ExCELLenceScrollPane("Keys", keyframes);

    // Table
    modelTable = new KeyFrameTableModel();
    keyFrameTable = new JTable(modelTable);
    keyFrameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    keyFrameTable.setCellSelectionEnabled(true);
    keyFrameTable.setRowSelectionAllowed(false);
    keyFrameTable.setColumnSelectionAllowed(false);
    keyFrameTable.getTableHeader().setReorderingAllowed(false);

    JPanel tableScrollPane = new TableScrollPane(keyFrameTable, buttons.keyframeButt,
            buttons.deleteKeyFrame);
    tableScrollPane.add(buttons.keyframeButt);

    // Add Shape Text Field
    addShapeName = new JTextField(20);
    addShapeName.addActionListener(e -> {
    });
    TextPanel shapeTextPanel = new TextPanel("New Shape Name", addShapeName,
            buttons.ellipseButt, buttons.rectButt);

    // Add Time Text Field
    changeTime = new JTextField(10);
    changeTime.addActionListener(e -> this.updateKeyFrameList(model, shapeCurrentlyChosen));
    TextPanel keyFrameTextPanel = new TextPanel("Time to add new keyframe", changeTime,
            buttons.createFrameButt);

    // Saving text fields/buttons
    saveFileName = new JTextField(10);
    TextPanel savePanel = new TextPanel("File you want to save to", saveFileName,
            buttons.textSave, buttons.svgSave);
    loadFileName = new JTextField(10);
    TextPanel loadPanel = new TextPanel("File you want to load from", loadFileName,
            buttons.loadButt);

    this.add(listScroller);
    this.add(keyframeScroller);
    this.add(animationPane);

    this.add(buttons);

    this.add(tableScrollPane);
    this.add(shapeTextPanel);
    this.add(keyFrameTextPanel);
    this.add(savePanel);
    this.add(loadPanel);

    this.pack();
    this.setVisible(true);

    canvas.setModel(model);
  }

  /**
   * Updates the {@code JList} of keyframes for the given shape on the given model.
   *
   * @param model the model which the list of keyframes will be extracted from
   * @param s the name of the shape of the list of keyframes
   */
  void updateKeyFrameList(IEasyAnimatorViewer model, String s) {

    // buttons.deleteKeyFrame.setDefault();
    if (s == null) {
      DefaultListModel<Integer> list = new DefaultListModel<>();
      keyframes.setModel(list);
      return;
    }

    shapeCurrentlyChosen = s;
    AtomicInteger keyFrameToBeChanged = new AtomicInteger();

    DefaultListModel<Integer> list = new DefaultListModel<>();
    for (int ii : model.getAllTimes(s)) {
      list.addElement(ii);
    }

    keyframes.removeListSelectionListener(curListener);
    keyframes.setModel(list);

    curListener = e -> {
      if (!e.getValueIsAdjusting()) {
        if (keyframes.getSelectedIndex() != -1) {
          try {
            keyFrameToBeChanged.set(model.getAllTimes(s).get(keyframes.getSelectedIndex()));
            shapeCurrentlyChosen = s;
            // buttons.consideredHarmful.setKeyFrame(keyFrameToBeChanged.get());
            // buttons.deleteKeyFrame.setDelete(s, keyFrameToBeChanged);
            this.updateTableModel(model, s, keyFrameToBeChanged);

          } catch (IllegalArgumentException ignored) {
          }
        }
      }
    };
    keyframes.addListSelectionListener(curListener);

  }

  /**
   * Updates the table of the values in the selected keyframe.
   *
   * @param model the model where the data will be extracted
   * @param s the name of the shape where the data will be extracted
   * @param keyFrameToBeChanged the time of the keyframe that is to be changed
   */
  private void updateTableModel(IEasyAnimatorViewer model, String s,
                                AtomicInteger keyFrameToBeChanged) {

    Posn posn;
    Size size;
    Color color;

    try {
      posn = model.getCurrentPosn(s, keyFrameToBeChanged.get());
      size = model.getCurrentSize(s, keyFrameToBeChanged.get());
      color = model.getCurrentColor(s, keyFrameToBeChanged.get());
    } catch (IllegalArgumentException e) {
      return;
    }

    modelTable = new KeyFrameTableModel(
            new int[]{posn.x, posn.y, size.width, size.height, color.getRed(), color.getGreen(),
                    color.getBlue()});

    keyFrameTable.setModel(modelTable);
  }


  @Override
  public void refresh(int time) {
    canvas.setTime(time);
    canvas.repaint();
  }

  /**
   * Updates the {@code JList} of shapes in this {@code EditableView} to reflect any changes made.
   *
   * @param model the model which the list of shapes will be extracted
   */
  void updateListOfShapes(IEasyAnimatorViewer model) {
    DefaultListModel<String> l = new DefaultListModel<>();
    for (String s : model.getAllShapeNames()) {
      l.addElement(s);
    }
    shapeList.setModel(l);
    //buttons.deleteShape.setShape(null);
  }

  @Override
  public void setListener(Commands c, IEasyAnimatorViewer model) {
    buttons.pause.addActionListener(e -> {
      c.pause();
    });

    buttons.reset.addActionListener(e -> c.reset());

    buttons.loop.addActionListener(e -> c.loop());

    buttons.faster.addActionListener(e -> c.faster());

    buttons.slower.addActionListener(e -> c.slower());

    buttons.consideredHarmful.addActionListener(e -> {
      c.goTo(keyframes.getSelectedValue());
    });

    buttons.deleteShape.addActionListener(
            e -> {
              if (shapeList.getSelectedIndex() != -1) {
                c.shapeDel(shapeList.getSelectedValue());
                this.updateListOfShapes(model);
              }
            });

    buttons.deleteKeyFrame.addActionListener(e -> {
      c.frameDel(shapeList.getModel().getElementAt(shapeList.getSelectedIndex()),
              keyframes.getSelectedValue());
      this.updateKeyFrameList(model,
              shapeList.getModel().getElementAt(shapeList.getSelectedIndex()));
    });

    buttons.keyframeButt.addActionListener(e -> {
      KeyFrameTableModel tableModel = (KeyFrameTableModel) keyFrameTable.getModel();
      c.changeKeyFrame(shapeList.getSelectedValue(),
              keyframes.getModel().getElementAt(keyframes.getSelectedIndex()),
              tableModel.getValueAt(0, 0), tableModel.getValueAt(0, 1), tableModel.getValueAt(0, 4),
              tableModel.getValueAt(0, 5), tableModel.getValueAt(0, 6), tableModel.getValueAt(0, 2),
              tableModel.getValueAt(0, 3));
    });

    buttons.rectButt.addActionListener(e -> {
      c.createShape("rectangle", addShapeName.getText());
      this.updateListOfShapes(model);
    });

    buttons.ellipseButt.addActionListener(e -> {
      c.createShape("ellipse", addShapeName.getText());
      this.updateListOfShapes(model);
    });

    buttons.createFrameButt.addActionListener(e -> {
              c.createKeyFrame(shapeList.getSelectedValue(),
                      Integer.parseInt(changeTime.getText()));
              if (shapeList != null) {
                this.updateKeyFrameList(model,
                        shapeList.getModel().getElementAt(shapeList.getSelectedIndex()));
              }
            }
    );

    // buttons.loadButt.addActionListener(//);
    buttons.svgSave.addActionListener(e -> c.save(saveFileName.getText(), "SVG"));

    buttons.textSave.addActionListener(e -> c.save(saveFileName.getText(), "text"));

    buttons.loadButt.addActionListener(e -> c.load(loadFileName.getText()));

  }

  @Override
  public void setModel(IEasyAnimatorViewer model) {
    this.canvas.setModel(model);
    this.updateListOfShapes(model);
    Rectangle r = model.getDimensions();
    animationPane.setPreferredSize(new Dimension(r.width, r.height));
    this.pack();

  }

  /*
   * A {@code JPanel} that has all the buttons.
   */
  protected class ButtonPanel extends JPanel {

    protected JButton pause = new JButton("play/pause");
    protected JButton reset = new JButton("reset");
    protected JButton loop = new JButton("toggle loop");
    protected JButton faster = new JButton("faster");
    protected JButton slower = new JButton("slower");
    protected JButton consideredHarmful = new JButton("goto keyframe");
    protected JButton deleteShape = new JButton("delete shape");
    protected JButton deleteKeyFrame = new JButton("delete keyframe");
    protected JButton keyframeButt = new JButton("change keyframe");
    protected JButton loadButt = new JButton("load");
    protected JButton createFrameButt = new JButton("create keyframe");
    protected JButton rectButt = new JButton("create rectangle");
    protected JButton ellipseButt = new JButton("create ellipse");
    protected JButton svgSave = new JButton("save as SVG");
    protected JButton textSave = new JButton("save as text");


    /**
     * The default constructor that adds all of the buttons to this {@code ButtonPanel}.
     */
    ButtonPanel() {

      this.add(pause);
      this.add(reset);
      this.add(loop);
      this.add(faster);
      this.add(slower);
      this.add(consideredHarmful);
      this.add(deleteShape);
      this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

  }


}

