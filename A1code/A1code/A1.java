/*
 *  ============================================================================================
 *  A1.java : Extends JFrame and contains a panel where shapes move around on the screen.
 *  YOUR UPI: ANSWER
 *  ============================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class A1  extends JFrame {
	private AnimationViewer panel;  // panel for bouncing area
	/** main method for A1 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new A1();
			}
		});
	}
	/** constructor to initialise components */
	public A1() {
		super("Bouncing Application");
		panel = new AnimationViewer(true);
		panel.setPreferredSize(new Dimension(Shape.DEFAULT_MARGIN_WIDTH, Shape.DEFAULT_MARGIN_HEIGHT));
		add(panel, BorderLayout.CENTER);
		addComponentListener(
			new ComponentAdapter() { // resize the frame and reset all margins for all shapes
				public void componentResized(ComponentEvent componentEvent) {
					panel.resetMarginSize();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
		pack();
		setVisible(true);
	}
}

