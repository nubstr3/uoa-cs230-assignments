/*
 *  ============================================================================================
 *  TableModelAdapter.java : Extends AbtractTableModel and implements the Adapter method for JTable.
 *  YOUR UPI: hli440
 *  ============================================================================================
 */
import javax.swing.table.AbstractTableModel;

class TableModelAdapter extends AbstractTableModel {
    private Shape nestedShape;
    private static String[] columnNames = new String[]{"Type", "X-pos", "Y-pos", "Width", "Height"};
    
    public TableModelAdapter(NestedShape s) {
        nestedShape = s;
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }
    public int getRowCount() {
        return ((NestedShape)nestedShape).getSize();
    }
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public String getValueAt(int rowIndex, int columnIndex) {
        Shape sh = ((NestedShape)nestedShape).getShapeAt(rowIndex);
        switch (columnIndex) {
            case 0:
                return sh.getClass().getName();
            case 1:
                return String.valueOf(sh.getX());
            case 2:
                return String.valueOf(sh.getY());
            case 3:
                return String.valueOf(sh.getWidth());
            case 4:
                return String.valueOf(sh.getHeight());
        } return "";
    }
    public void setNestedShape(Shape s) {
        nestedShape = s;
    }
}