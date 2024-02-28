/*
 *  ============================================================================================
 *  DynamicRectangleShape.java: a shape that is Dynamic and rectangular
 *  YOUR UPI: hli440
 *  Henry Li, 12/05/21
 *  ============================================================================================
 */


import java.awt.*;
class DynamicRectangleShape extends RectangleShape {
    DynamicRectangleShape() {
        super();
    }
    DynamicRectangleShape(int x, int y, int width, int height, int mWidth, int mHeight, Color border, Color fill, PathType path) {
        super(x, y, width, height, mWidth, mHeight, border, fill, path);
    }
    
    public void draw(Painter g) {
        if (this.path.deltaY < 0) {
            System.out.printf("Color: %s Fill Rectangle (%d, %d) %d x %d\n", super.getBorderColor(), super.getX(), super.getY(), super.getWidth(), super.getHeight());
            System.out.printf("Color: %s Draw Rectangle (%d, %d) %d x %d\n", super.getFillColor(), super.getX(), super.getY(), super.getWidth(), super.getHeight());
        }
        else {
            System.out.printf("Color: %s Fill Rectangle (%d, %d) %d x %d\n", super.getFillColor(), super.getX(), super.getY(), super.getWidth(), super.getHeight());
            System.out.printf("Color: %s Draw Rectangle (%d, %d) %d x %d\n", super.getBorderColor(), super.getX(), super.getY(), super.getWidth(), super.getHeight());
        }
    }
}