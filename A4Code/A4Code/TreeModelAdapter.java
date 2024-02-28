/*
 *  ============================================================================================
 *  TreeModelAdapter.java : Extends TreeModel and uses the Adapter method.
 *  YOUR UPI: hli440
 *  ============================================================================================
 */
import java.util.*;
import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


class TreeModelAdapter implements TreeModel {
    private Shape nestedShape;
    private ArrayList<TreeModelListener> treeModelListeners = new ArrayList<TreeModelListener>();
    
    public TreeModelAdapter(NestedShape s) {
        nestedShape = s;
    }
    public Shape getRoot() {
        return nestedShape;
    }
    public boolean isLeaf(Object node) {
        if (node instanceof NestedShape) {
            return false;
        } else {
            return true;
        }
    }
    public Shape getChild(Object parent, int index) {
        if (parent instanceof NestedShape) {
            if (index < ((NestedShape)parent).getSize()) {
                return ((NestedShape)parent).getShapeAt(index);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public int getChildCount(Object parent) {
        if (parent instanceof NestedShape) {
            return ((NestedShape)parent).getSize();
        } else {
            return 0;
        }
    }
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof NestedShape) {
            return ((NestedShape)parent).indexOf((Shape)child);
        } else {
            return -1;
        }
    }
    
    public void addTreeModelListener(TreeModelListener modelListener) {
        treeModelListeners.add(modelListener);
    }
    
	public void removeTreeModelListener(TreeModelListener modelListener) {
	    treeModelListeners.remove(modelListener);
	}
	public void fireTreeStructureChanged(final Object source, final Object[] path, final int[] childIndices, final Object[] children) {
	    TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
	    for (TreeModelListener tml: treeModelListeners) {
	        tml.treeStructureChanged(event);
	    }
	}
	
	public void fireTreeNodesInserted(Object source, Object[] path,int[] childIndices,Object[] children) {
	    TreeModelEvent event = new TreeModelEvent(source, path,childIndices,children);
	    for (TreeModelListener tml: treeModelListeners) {
	        tml.treeStructureChanged(event);
	    }
	}
	
	public void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children){
	    TreeModelEvent event = new TreeModelEvent(source, path, childIndices, children);
	    for (TreeModelListener tml: treeModelListeners) {
	        tml.treeStructureChanged(event);
	    }
	}
	public void addToRoot(Shape s) {
	    int size = ((NestedShape)nestedShape).getSize();
	    s.setParent(((NestedShape)nestedShape));
	    ((NestedShape)nestedShape).add(s);
	    Object[] obj = new Object[]{nestedShape};
	    int[] siize = new int[]{size};
	    Object[] param = new Object[]{s};
	    fireTreeNodesInserted(this, obj, siize, param);
	}
	public boolean addNode(TreePath selectedPath, ShapeType currentShapeType) {
	    Object node = selectedPath.getLastPathComponent();
	    if (node instanceof NestedShape == false) { return false; }
	    int siize = ((NestedShape)node).getSize();
	    ((NestedShape)node).createAddInnerShape(currentShapeType);
	    Shape inner = ((NestedShape)node).getShapeAt(siize);
	    int[] children = new int[]{siize + 1};
	    Object[] sh = new Object[]{inner};
	    fireTreeNodesInserted(this, selectedPath.getPath(), children, sh);
	    return true;
	}
	public boolean removeNodeFromParent(TreePath selectedPath) {
	    Object node = selectedPath.getLastPathComponent();
	    if (((Shape)node).getParent() == null) { return false; }
	    NestedShape parent = ((Shape)node).getParent();
	    int index = parent.indexOf(((Shape)node));
	    parent.remove(((Shape)node));
	    Object[] upper = new Object[]{selectedPath};
	    int[] child_idx = new int[]{index - 1};
	    Object[] selected = new Object[]{node};
	    fireTreeNodesRemoved(this, upper, child_idx, selected);
	    return true;
	}
	
	
	public void valueForPathChanged(TreePath path, Object newValue) {}
    public void fireTreeNodesChanged(TreeModelEvent e) {}
}





















