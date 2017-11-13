package org.tec.datos1.eclipse.grapher.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class Line implements Illustrator {
    Point first;
    Point last;
    TypeL type;
    String text = "";
    int width;

    public Line(Point first, Point last) {
        this.first = first;
        this.last = last;
        this.type = TypeL.NONE;
    }

    public Line(Point first, Point last, int width) {
        this(first, last);
        this.width = width;
    }

    public Line(Point first, Point last, int width, TypeL type) {
        this(first, last);
        this.width = width;
        this.type = type;
    }

    public Line(Point first, Point last, TypeL type) {
        this(first, last);
        this.type = type;
    }

    public Line(Point first, Point last, TypeL type, String text) {
        this(first, last, type);
        this.text = text;
    }

    public Line(Point first, Point last, TypeL type, String text, int width) {
        this(first, last, width);
        this.type = type;
        this.text = text;
    }

    public Line(Point first, Point last, String text) {
        this(first, last);
        this.text = text;
    }


    public void sketch(GC gc, int line) {
	    	Display display = Display.getCurrent();
	    	Color color = display.getSystemColor(SWT.COLOR_BLACK);
	    	gc.setForeground(color);
	    	gc.setLineWidth(1);
	    		switch (this.type) {
            case DORETURN:
                gc.drawLine(this.first.x, this.first.y, this.first.x - this.width / 2 - 20, this.first.y);
                gc.drawLine(this.first.x - this.width / 2 - 20, this.first.y, this.first.x - this.width / 2 - 20, this.last.y);
                gc.drawLine(this.first.x - this.width / 2 - 20, this.last.y, this.last.x, this.last.y);
                break;
            case RETURN:
                gc.drawLine(this.first.x, this.first.y, this.first.x, this.first.y + 10);
                gc.drawLine(this.first.x, this.first.y + 10, this.first.x - this.width / 2 - 20, this.first.y + 10);
                gc.drawLine(this.first.x - this.width / 2 - 20, this.first.y + 10, this.first.x - this.width / 2 - 20, this.last.y);
                gc.drawLine(this.first.x - this.width / 2 - 20, this.last.y, this.last.x, this.last.y);
                break;
            case JOIN:
                gc.drawLine(this.first.x, this.first.y, this.first.x, this.last.y);
                gc.drawLine(this.first.x, this.last.y, this.last.x, this.last.y);
                break;
            case JUMP:
                gc.drawText(text, this.first.x + 10, this.first.y - 15);
                gc.drawLine(this.first.x, this.first.y, this.first.x + this.width / 2 + 20, this.first.y);
                gc.drawLine(this.first.x + this.width / 2 + 20, this.first.y, this.first.x + this.width / 2 + 20, this.last.y);
                gc.drawLine(this.first.x + this.width / 2 + 20, this.last.y, this.last.x, this.last.y);
                break;
            case NONE:
                if (this.first.x != this.last.x) {
                    if (this.last.x < this.first.x) {
                        gc.drawText(text, first.x - 30, first.y - 15);
                    } else {
                        gc.drawText(text, this.first.x + 10, this.first.y - 15);
                    }
                    gc.drawLine(this.first.x, this.first.y, this.last.x, this.first.y);
                    gc.drawLine(this.last.x, this.first.y, this.last.x, this.last.y);
                } else {
                    gc.drawText(text, this.first.x + 5, this.first.y + 5);
                    gc.drawLine(this.first.x, this.first.y, this.last.x, this.last.y);
                }
                break;
        }
    }

    @Override
    public void fix(int x) {
        first = new Point(first.x + x, first.y);
        last = new Point(last.x + x, last.y);
    }
}
