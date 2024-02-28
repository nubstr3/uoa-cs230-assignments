/*
 *  ============================================================================================
 *  ImageRectangleShape: a shape that is an image with a rectangular border
 *  YOUR UPI: hli440
 *  Henry Li, 12/05/21
 *  ============================================================================================
 */
import java.awt.*;
class ImageRectangleShape extends RectangleShape {
    private Image image;
    private String imageFilename;
    
    ImageRectangleShape(String filename) {
        super();
        imageFilename = filename;
        image = Toolkit.getDefaultToolkit().createImage(A2.class.getResource(imageFilename));
    }
    ImageRectangleShape(int x, int y, int width, int height, int mWidth, int mHeight, Color border, Color fill, PathType path, String filename) {
        super(x, y, width, height, mWidth, mHeight, border, fill, path);
        imageFilename = filename;
        image = Toolkit.getDefaultToolkit().createImage(A2.class.getResource(imageFilename));
    }
    public Image loadImage(String filename) {
        return image;
    }
    public void draw(Painter g) {
        System.out.printf("Draw an image (%d, %d) %d x %d\n", x, y, width, height);
        System.out.printf("Color: %s Draw Rectangle (%d, %d) %d x %d\n", getBorderColor(), x, y, width, height);
        
    }
}