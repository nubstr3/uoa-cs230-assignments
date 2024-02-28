/*
 *	===============================================================================
 *	NestedShape.java : A shape that has nested shapes inside of it.
 *  YOUR UPI: hli440
 *	=============================================================================== */
import java.awt.*;
import java.util.ArrayList;
class NestedShape extends RectangleShape {
    private ArrayList<Shape> nestedShapes = new ArrayList<Shape>();
    private static ShapeType nextShapeType = ShapeType.NESTED;
    
    public NestedShape() {
        super();
    }
    public NestedShape(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, PathType pt) {
        super(x, y, w, h, mw, mh, bc, fc, pt);
        nextShapeType = nextShapeType.next();
        createAddInnerShape(nextShapeType);
    }
    public NestedShape(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, PathType pt, String txt) {
        super(x, y, w, h, mw, mh, bc, fc, pt, txt);
        nextShapeType = nextShapeType.next();
        createAddInnerShape(nextShapeType);
    }
    public void createAddInnerShape(ShapeType st) {
        switch (st) {
            case RECTANGLE: {
                RectangleShape inner =  new RectangleShape(0,0,this.width/2,this.height/2,this.width,this.height,this.borderColor, this.fillColor,Shape.DEFAULT_PATHTYPE, this.text);
                inner.setParent(this);
        		nestedShapes.add(inner);
                break;
			} case XRECTANGLE: {
				XRectangleShape inner =  new XRectangleShape(0,0,this.width/2,this.height/2,this.width,this.height,this.borderColor, this.fillColor,Shape.DEFAULT_PATHTYPE, this.text);
                inner.setParent(this);
        		nestedShapes.add(inner);
                break;
		   } case OVAL: {
				OvalShape inner =  new OvalShape(0,0,this.width/2,this.height/2,this.width,this.height,this.borderColor, this.fillColor,Shape.DEFAULT_PATHTYPE, this.text);
                inner.setParent(this);
        		nestedShapes.add(inner);
                break;
		   } case SQUARE: {
		        SquareShape inner = new SquareShape(0,0,Math.min(this.width,this.height)/2,this.width,this.height,this.borderColor, this.fillColor,Shape.DEFAULT_PATHTYPE, this.text);
		        inner.setParent(this);
        		nestedShapes.add(inner);
                break;
			} case NESTED: {
			    NestedShape inner = new NestedShape(0,0,this.width/2,this.height/2,this.width,this.height,this.borderColor, this.fillColor,Shape.DEFAULT_PATHTYPE, this.text);
			    inner.setParent(this);
			    nestedShapes.add(inner);
			    break;
			}
		}
    }
    
    public Shape getShapeAt(int index) {
        return nestedShapes.get(index);
    }
    public int getSize() {
        return nestedShapes.size();
    }
    public void draw(Painter painter) {
        System.out.printf("Color: %s ", Color.BLACK);
        painter.drawRect(super.x, super.y, super.width, super.height);
        painter.translate(super.x, super.y);
        boolean swap = true;
        for (Shape s: nestedShapes) {
            s.draw(painter);
            if (swap == true) {
                painter.translate(-super.x, -super.y);
                swap = false;
            } else {
                painter.translate(super.x,super.y);
                swap = true;
            }
        }
    }
    public void move() {
        super.move();
        for (Shape s: nestedShapes) {
            s.move();
        }
    }
    public void add(Shape s) {
        s.setParent(this);
        this.nestedShapes.add(s);
    }
    public void remove(Shape s) {
        if (this.nestedShapes.contains(s)) {
            this.nestedShapes.remove(s);
            s.setParent(null);
        }
    }
    public int indexOf(Shape s) {
        return this.nestedShapes.indexOf(s);
    }
    
    
    public Shape[] getChildren() {
        Shape[] shapes = new Shape[this.nestedShapes.size()];
        int counter = 0;
        for (Shape s: this.nestedShapes) {
            shapes[counter] = this.nestedShapes.get(counter);
            counter++;
        }
        return shapes;
    }
}








