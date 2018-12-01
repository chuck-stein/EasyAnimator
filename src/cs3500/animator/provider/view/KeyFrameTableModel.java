package cs3500.animator.view;

import javax.swing.table.AbstractTableModel;

/**
 * Represents a keyframe that is currently being edited.
 */
class KeyFrameTableModel extends AbstractTableModel {

  private String[] columnNames = {"x", "y", "width", "height", "red", "green", "blue"};
  private int[] param;

  KeyFrameTableModel() {
    param = new int[]{0, 0, 0, 0, 0, 0, 0};
  }

  KeyFrameTableModel(int[] args) {
    if (args.length != 7) {
      throw new IllegalArgumentException("Illegal amount of arguments passed in");
    }
    param = args;
  }

  @Override
  public int getRowCount() {
    return 1;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Integer getValueAt(int rowIndex, int columnIndex) {
    return param[columnIndex];
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    param[col] = Integer.parseInt((String) value);
    this.fireTableCellUpdated(row, col);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

}
