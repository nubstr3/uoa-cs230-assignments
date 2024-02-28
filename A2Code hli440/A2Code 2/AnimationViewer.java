/*
 *    ==========================================================================================
 *    AnimationViewer.java : Moves shapes around on the screen according to different paths.
 *    Henry Li, 12/05/21
 *    It is the main drawing area where shapes are added and manipulated.
 *    YOUR UPI: hli440
 *    ==========================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class AnimationViewer extends JComponent implements Runnable {
	private Thread animationThread = null;    // the thread for animation
    private static int DELAY = 20;         // the current animation speed
	private ArrayList<Shape> shapes = new ArrayList<Shape>(); //create the ArrayList to store shapes
	private Painter painter = new GraphicsPainter();
	private ShapeType currentShapeType=Shape.DEFAULT_SHAPETYPE; // the current shape type,
	private PathType currentPathType=Shape.DEFAULT_PATHTYPE;  // the current path type
	private Color currentBorderColor=Shape.DEFAULT_BORDER_COLOR, currentFillColor=Shape.DEFAULT_FILL_COLOR;  // the current fill colour of a shape
	private int marginWidth=Shape.DEFAULT_MARGIN_WIDTH, marginHeight = Shape.DEFAULT_MARGIN_HEIGHT, currentWidth=Shape.DEFAULT_WIDTH, currentHeight=Shape.DEFAULT_HEIGHT;
	private String currentImageFileName = Shape.DEFAULT_IMAGE_FILENAME;
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
        // complete this: create a new shape using the current properties and the mouse position
        case RECTANGLE:
            Shape newRect = new RectangleShape(x, y, getCurrentWidth(), getCurrentHeight(), marginWidth, marginHeight, getCurrentBorderColor(), getCurrentFillColor(), currentPathType);
                shapes.add(newRect);
                break;
        case XRECTANGLE:
            Shape newXRect = new XRectangleShape(x, y, getCurrentWidth(), getCurrentHeight(), marginWidth, marginHeight, getCurrentBorderColor(), getCurrentFillColor(), currentPathType);
                shapes.add(newXRect);
                break;
        case SQUARE:
            int minimum = 0;
            if (getCurrentWidth() < getCurrentHeight()) {
                minimum = getCurrentWidth();
            } else {
                minimum = getCurrentHeight();
            }
            Shape newSquare = new SquareShape(x, y, minimum, marginWidth, marginHeight, getCurrentBorderColor(), getCurrentFillColor(), currentPathType);
                shapes.add(newSquare);
                break;
        case OVAL:
            Shape newOval = new OvalShape(x, y, getCurrentWidth(), getCurrentHeight(), marginWidth, marginHeight, getCurrentBorderColor(), getCurrentFillColor(), currentPathType);
                shapes.add(newOval);
                break;
        case DYNAMIC:
            Shape newDynamic = new DynamicRectangleShape(x, y, getCurrentWidth(), getCurrentHeight(), marginWidth, marginHeight, getCurrentBorderColor(), getCurrentFillColor(), currentPathType);
                shapes.add(newDynamic);
                break;
        case IMAGE:
            Shape newImage = new ImageRectangleShape(x, y, getCurrentWidth(), getCurrentHeight(), marginWidth, marginHeight, getCurrentBorderColor(), getCurrentFillColor(), currentPathType, currentImageFileName);
                shapes.add(newImage);
                break;
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
    // add a list of set methods:
    public ShapeType getCurrentShapeType() {
        return currentShapeType;
    }
    public PathType getCurrentPathType() {
        return currentPathType;
    }
    public void setCurrentShapeType(int index) {
        currentShapeType = ShapeType.values()[index];
    }
    public void setCurrentPathType(int index) {
        currentPathType = PathType.values()[index];
    }
    public void setCurrentWidth(int w) {
        currentWidth = w;
        for (Shape s: shapes) {
            if (s.isSelected()) {
                s.setWidth(w);
            }
        }
    }
    public void setCurrentHeight(int h) {
        currentHeight = h;
        for (Shape s: shapes) {
            if (s.isSelected()) {
                s.setHeight(h);
            }
        }
    }
    public void setCurrentFillColor(Color fc) {
        currentFillColor = fc;
        for (Shape s: shapes) {
            if (s.isSelected()) {
                s.setFillColor(fc);
            }
        }
    }
    public void setCurrentBorderColor(Color bc) {
        currentBorderColor = bc;
        for (Shape s: shapes) {
            if (s.isSelected()) {
                s.setBorderColor(bc);
            }
        }
    }


// you don't need to make any changes after this line ______________
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

	/* Inner member class for mouse event handling */
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
    /**    update the painting area
     * @param g    the graphics control */
    public void update(Graphics g){ paint(g); }
    /** reset the margin size of all shapes from our ArrayList */
    public void resetMarginSize() {
        marginWidth = getWidth();
        marginHeight = getHeight() ;
        for (Shape currentShape: shapes)
			currentShape.setMarginSize(marginWidth,marginHeight );
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
