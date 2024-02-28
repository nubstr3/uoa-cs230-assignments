/*
 *  ============================================================================================
 *  Text version of a painter
 *  YOUR UPI: hli440
 *  Henry Li, 12/05/21
 *  ============================================================================================
 */

import java.awt.*;
import java.awt.Graphics2D;
public class GraphicsPainter implements Painter {
	private Graphics2D g;
	public GraphicsPainter() { }
	public void setGraphics(Graphics g) {
		this.g = (Graphics2D)g;
	}
	
	
	public void drawRect(int x, int y, int width, int height) { 
	      System.out.printf("Draw Rectangle (%d, %d) %d x %d\n", x, y, width, height); 
	  }
	  
	  public void fillRect(int x, int y, int width, int height) {
	      System.out.printf("Fill Rectangle (%d, %d) %d x %d\n", x, y, width, height);
	  }
	  
	  public void drawLine(int x1, int y1, int x2, int y2) {
	      System.out.printf("Draw Line (%d, %d) to (%d, %d)\n", x1, y1, x2, y2);
	  }
	  
	  public void drawOval(int x1, int y1, int width, int height) {
	      System.out.printf("Draw Oval (%d, %d) %d x %d\n", x1, y1, width, height);
	  }
	  
	  public void fillOval(int x1, int y1, int width, int height) {
	      System.out.printf("Fill Oval (%d, %d) %d x %d\n", x1, y1, width, height);
	  }
	@Override
	public void setPaint(Color c) {
		g.setPaint(c);
	}
	@Override
	public void drawHandles(boolean isSelected, int x, int y, int width, int height) {
		if (isSelected) {
            g.setPaint(Color.black);
            g.fillRect(x -2, y-2, 4, 4);
            g.fillRect(x + width -2, y + height -2, 4, 4);
            g.fillRect(x -2, y + height -2, 4, 4);
            g.fillRect(x + width -2, y-2, 4, 4);
        }
	}
	public void drawImage(Image img, int x, int y, int width, int height) {
		System.out.printf("Draw Image (%d, %d) %d x %d\n", x, y, width, height);
		g.drawImage(img, x, y, width, height, null);
	  }
}
