/*
 * ==========================================================================================
 *  AnimationPanel.java : Moves shapes around on the screen according to different paths.
 *  It is the main drawing area where shapes are added and manipulated.
 *  YOUR UPI: ANSWER
 *  =========================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

class AnimationViewer extends JComponent implements Runnable {
	private Thread animationThread = null;    // the thread for animation
    private static int DELAY = 20;         // the current animation speed
    private ArrayList<Shape> shapes = new ArrayList<Shape>(); //create the ArrayList to store shapes
    private Painter painter = new GraphicsPainter();
    private ShapeType currentShapeType=Shape.DEFAULT_SHAPETYPE; // the current shape type,
    private PathType currentPathType=Shape.DEFAULT_PATHTYPE;  // the current path type
    private Color currentBorderColor=Shape.DEFAULT_BORDER_COLOR, currentFillColor=Shape.DEFAULT_FILL_COLOR;  // the current fill colour of a shape
    private int marginWidth=Shape.DEFAULT_MARGIN_WIDTH, marginHeight = Shape.DEFAULT_MARGIN_HEIGHT, currentWidth=Shape.DEFAULT_WIDTH, currentHeight=Shape.DEFAULT_HEIGHT;

     /** Constructor of the AnimationPanel */
    public AnimationViewer(boolean isGraphicsVersion) {
		if (isGraphicsVersion) {
			start();
			addMouseListener(new MyMouseAdapter());
		}
    }

    /** create a new shape
     * @param x     the x-coordinate of the mouse position
     * @param y    the y-coordinate of the mouse position */
    protected void createNewShape(int x, int y) {
		switch (currentShapeType) {
        // complete this: create a new shape dependent on all current properties and the mouse position

		}
		currentShapeType = currentShapeType.next(); //choose the next shape
		currentPathType = currentPathType.next();           //choose the next path
    }
 	/** get the current width
	 * @return currentWidth - the width value */
	public int getCurrentWidth() { return currentWidth; }
	/** get the current height
	 * @return currentHeight - the height value */
	public int getCurrentHeight() { return currentHeight; }
	/** get the current fill colour
	 * @return currentFillColor - the fill colour value */
	public Color getCurrentFillColor() { return currentFillColor; }
	/** get the current border colour
	 * @return currentBorderColor - the border colour value */
	public Color getCurrentBorderColor() { return currentBorderColor; }

    /**    move and paint all shapes within the animation area
     * @param g    the Graphics control */
	public void textPaintComponent(Painter painter) {
		for (Shape currentShape: shapes) {
			currentShape.move();
			currentShape.draw(painter);
			currentShape.drawHandles(painter);
		}
	}
   /**    move and paint all shapes within the animation area
     * @param g    the Graphics control */
    public void paintComponent(Graphics g) {
		((GraphicsPainter)painter).setGraphics(g);
		super.paintComponent(g);
        for (Shape currentShape: shapes) {
            currentShape.move();
		    currentShape.draw(painter);
            currentShape.drawHandles(painter);
		}
    }

    // you don't need to make any changes after this line ______________
    /**    update the painting area
     * @param g    the graphics control */
    public void update(Graphics g){ paint(g); }
   /** reset the margin size of all shapes from our ArrayList */
    public void resetMarginSize() {
        int marginWidth = getWidth();
        int marginHeight = getHeight();
        for (Shape currentShape: shapes)
				currentShape.setMarginSize(marginWidth,marginHeight );
    }
    class MyMouseAdapter extends MouseAdapter {
			public void mouseClicked( MouseEvent e ) {
				boolean found = false;
				for (Shape currentShape: shapes)
					if ( currentShape.contains( e.getPoint()) ) { // if the mousepoint is within a shape, then set the shape to be selected/deselected
						currentShape.setSelected( ! currentShape.isSelected() );
						found = true;
					}
				if (!found) createNewShape(e.getX(), e.getY());
			}
	}
	public void start() {
        animationThread = new Thread(this);
        animationThread.start();
    }
    public void stop() {
        if (animationThread != null) {
            animationThread = null;
        }
    }
    public void run() {
        Thread myThread = Thread.currentThread();
        while(animationThread==myThread) {
            repaint();
            pause(DELAY);
        }
    }
    private void pause(int milliseconds) {
        try {
            Thread.sleep((long)milliseconds);
        } catch(InterruptedException ie) {}
    }
}
