package org.tec.datos1.eclipse.grapher.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;


public class Run implements Illustrator {

    Point input;
    Point output;
    int width = 80;


    public Run(Point input) {
        this.input = input;
        this.output = new Point(input.x, input.y + 40);
    }

    public Run(int x, int y) {
        this(new Point(x, y));
    }


    public Point getOutput() {
        return output;
    }


    public Point getInput() {
        return input;
    }


    @Override
    public void sketch(GC gc) {
        gc.drawOval(input.x - this.width / 2, input.y, this.width, 40);
        gc.drawText("Beginning", input.x - gc.stringExtent("Start").x / 2, input.y + (40 - gc.stringExtent("Beginning").y) / 2);
    }

    @Override
    public void fix(int x) {
        input = new Point(input.x + x, input.y);
        output = new Point(output.x + x, output.y);
    }

    public int getWidth() {
        return this.width;
    }
}

	
	

