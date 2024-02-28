/*
 *  ============================================================================================
 *  A4.java : Extends JFrame and contains a panel where shapes move around on the screen.
 *  YOUR UPI: hli440
 *  ============================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;
import javax.swing.tree.*;

public class A4  extends JFrame {
	private AnimationViewer panel;  // panel for bouncing area
	JButton borderButton, fillButton, addNodeButton, removeNodeButton;  //buttons to start and stop the animation
	JComboBox<ShapeType> shapesComboBox;
	JComboBox<PathType> pathComboBox;
	JTextField heightText, widthText, messageText;
	JTree tree;
	JTable table;
	/** main method for A4 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new A4();
			}
		});
	}
	public A4() {
		super("Bouncing Application");
		JPanel mainPanel = setUpMainPanel();
		add(mainPanel, BorderLayout.CENTER);
		add(setUpToolsPanel(), BorderLayout.NORTH);
		addComponentListener(
			new ComponentAdapter() { // resize the frame and reset all margins for all shapes
				public void componentResized(ComponentEvent componentEvent) {
					panel.resetMarginSize();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
		setVisible(true);
	}
	public JPanel setUpMainPanel() {
		JPanel mainPanel = new JPanel();
		panel = new AnimationViewer(true);
		panel.setPreferredSize(new Dimension(600, 800));
		JPanel dataPanel = setUpDataPanel();
		dataPanel.setPreferredSize(new Dimension(600, 800));
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dataPanel, panel);
		mainSplitPane.setResizeWeight(0.5);
		mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setContinuousLayout(true);
        mainPanel.add(mainSplitPane);
		return mainPanel;
	}
	/** Set up the tools panel
		* @return toolsPanel		the Panel */
	public JPanel setUpDataPanel() {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(600,400));
		//complete this
		//table =
		TableModelAdapter tablema = panel.getTableModelAdapter();
		JTable table = new JTable(tablema);
		tablePanel.add(table);
		tablePanel.add(new JScrollPane(table));
		
		
		JPanel treePanel = new JPanel(new BorderLayout());
		treePanel.setPreferredSize(new Dimension(600,400));
		//complete this
		//tree = 
		TreeModelAdapter treema = panel.getTreeModelAdapter();
		JTree tree = new JTree(treema);
		treePanel.add(tree);
		treePanel.add(new JScrollPane(tree));


		JPanel treeButtonsPanel = new JPanel();
		addNodeButton = new JButton("Add Node");
		//complete this : addActionListener...
		addNodeButton.addActionListener(new AddNodeActionListener());
		removeNodeButton = new JButton("Remove Node");
		//complete this : addActionListener...
		removeNodeButton.addActionListener(new RemoveNodeActionListener());

		treeButtonsPanel.add(addNodeButton);
		treeButtonsPanel.add(removeNodeButton);
		treePanel.add(treeButtonsPanel,BorderLayout.NORTH);
		JSplitPane dataSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,treePanel,tablePanel);
		dataSplitPane.setResizeWeight(0.5);
		dataSplitPane.setOneTouchExpandable(true);
        dataSplitPane.setContinuousLayout(true);
		JPanel dataPanel = new JPanel();
		dataPanel.add(dataSplitPane);
		return dataPanel;
	}
	/** Set up the tools panel
	* @return toolsPanel		the Panel */
	public JPanel setUpToolsPanel() {
		shapesComboBox = new JComboBox<ShapeType>();
		shapesComboBox.setModel(new DefaultComboBoxModel<ShapeType>(ShapeType.values()));
		shapesComboBox.setToolTipText("Set shape");
		shapesComboBox.addActionListener( new ShapeActionListener()) ;
		pathComboBox = new JComboBox<PathType>();
		pathComboBox.setModel(new DefaultComboBoxModel<PathType>(PathType.values()));
		pathComboBox.addActionListener( new PathActionListener());
		heightText = new JTextField("" + Shape.DEFAULT_HEIGHT);
		heightText.setToolTipText("Set Height");
		heightText.addActionListener( new HeightActionListener());
		//Set up the width TextField
		widthText = new JTextField("" + Shape.DEFAULT_WIDTH);
		widthText.setToolTipText("Set Width");
		widthText.addActionListener( new WidthActionListener());
		//set up the text message TextField
		messageText = new JTextField("" + Shape.DEFAULT_TEXT);
		messageText.setToolTipText("Set Message");
		messageText.addActionListener( new TextActionListener());
		//Set up the fill colour button
		fillButton = new JButton("Fill");
		fillButton.setToolTipText("Set Fill Color");
		fillButton.setForeground(panel.getCurrentFillColor());
		fillButton.addActionListener( new FillActionListener());
		//Set up the border colour button
		borderButton = new JButton("Border");
		borderButton.setToolTipText("Set Border Color");
		borderButton.setForeground(panel.getCurrentBorderColor());
		borderButton.addActionListener( new BorderActionListener());
		JPanel toolsPanel = new JPanel();
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
		toolsPanel.add(new JLabel(" Shape: ", JLabel.RIGHT));
		toolsPanel.add(shapesComboBox);
		toolsPanel.add(new JLabel(" Path: ", JLabel.RIGHT));
		toolsPanel.add(pathComboBox);
		toolsPanel.add(new JLabel(" Width: ", JLabel.RIGHT));
		toolsPanel.add(widthText);
		toolsPanel.add( new JLabel(" Height: ", JLabel.RIGHT));
		toolsPanel.add(heightText);
		toolsPanel.add( new JLabel(" Text: ", JLabel.RIGHT));
		toolsPanel.add(messageText);
		toolsPanel.add(borderButton);
		toolsPanel.add(fillButton);
		return toolsPanel;
	}
	//complete the action listener for tree selection
	class MyTreeSelectionListener implements TreeSelectionListener {
	    public void valueChanged(TreeSelectionEvent e) {
	        Object node = tree.getLastSelectedPathComponent();
	        if (node instanceof NestedShape) {
	            TableModelAdapter x = panel.getTableModelAdapter();
	            x.setNestedShape((Shape)node);
	            x.fireTableDataChanged();
	        }
	    }
	}

	//complete the action listener for the addNodeButton
	class AddNodeActionListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	        TreePath selectedPath = tree.getSelectionPath();
	        if (selectedPath == null) {
	        	JOptionPane.showMessageDialog(null, "ERROR: No node selected.");
	        } else {
	            Object node = selectedPath.getLastPathComponent();
	            if (node instanceof NestedShape) {
	                panel.addShape(selectedPath);
	            } else {
	            	JOptionPane.showMessageDialog(null, "ERROR: Must select a NestedShape node.");
	            }
	        }
	    }
	}
	//complete the action listener for the removeNodeButton
	class RemoveNodeActionListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	        TreePath path = tree.getSelectionPath();
	        if (e.getSource() == removeNodeButton && path == null) {
	        	JOptionPane.showMessageDialog(null, "ERROR: No node selected.");
	        } else {
	            Object node = path.getLastPathComponent();
	            TreeModel x = panel.getTreeModelAdapter();
	            if (e.getSource() == removeNodeButton && node != x.getRoot()) {
	                panel.removeShape(path);
	            } else if (e.getSource() == removeNodeButton && node == x.getRoot()) {
	            	JOptionPane.showMessageDialog(null, "ERROR: Must not remove the root.");
	            }
	        }
	    }
	}
	class ShapeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel.setCurrentShapeType(shapesComboBox.getSelectedIndex());
		}
	}
	class PathActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel.setCurrentPathType(pathComboBox.getSelectedIndex());
		}
	}
	class WidthActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int newValue = Integer.parseInt(widthText.getText());
				if (newValue > 0 && newValue < Shape.DEFAULT_MARGIN_WIDTH/2 ) // if the value is valid, then change the current height
					panel.setCurrentWidth(newValue);
				else
					widthText.setText(panel.getCurrentWidth()+""); //undo the changes
			} catch (Exception ex) {
				widthText.setText(panel.getCurrentWidth()+""); //if the number entered is invalid, reset it
			}
		}
	}
	class HeightActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int newValue = Integer.parseInt(heightText.getText());
				if (newValue > 0 && newValue < Shape.DEFAULT_MARGIN_HEIGHT/2 ) // if the value is valid, then change the current height
					panel.setCurrentHeight(newValue);
				else
					heightText.setText(panel.getCurrentHeight()+""); //undo the changes
			} catch (Exception ex) {
				heightText.setText(panel.getCurrentHeight()+""); //if the number entered is invalid, reset it
			}
		}
	}
	class TextActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel.setCurrentText(messageText.getText());
		}
	}
	class FillActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e) {
			Color newColor = JColorChooser.showDialog(panel, "Fill Color", panel.getCurrentFillColor());
			if ( newColor != null) {
				fillButton.setForeground(newColor);
				panel.setCurrentFillColor(newColor);
			}
		}
	}
	class BorderActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e) {
			Color newColor = JColorChooser.showDialog(panel, "Border Color", panel.getCurrentBorderColor());
			if ( newColor != null) {
				borderButton.setForeground(newColor);
				panel.setCurrentBorderColor(newColor);
			}
		}
	}
}

