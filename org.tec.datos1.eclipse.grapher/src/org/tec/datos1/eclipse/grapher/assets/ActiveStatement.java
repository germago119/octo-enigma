package org.tec.datos1.eclipse.grapher.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class ActiveStatement implements Illustrator {

    String text;
    Point input;
    Point output;
    int line;

    /**
     * Constructor para el diagrama de un ActiveStatement
     *
     * @param text  Texto dentro del ActiveStatement
     * @param input Punto de inicio del ActiveStatement
     */
    public ActiveStatement(String text, Point input, int line) {
        this.line = line;
        this.text = text;
        this.input = input;
        this.output = new Point(input.x, input.y + 40);

    }

    /**
     * Constructor para el diagrama de un ActiveStatement
     *
     * @param text Texto dentro del ActiveStatement
     * @param x    Coordenada en x del punto de inicio del ActiveStatement
     * @param y    Coordenada en y del punto de inicio del ActiveStatement
     */
    public ActiveStatement(String text, int x, int y, int line) {
        this(text, new Point(x, y), line);
    }

    /**
     * @return Punto de inicio del ActiveStatement
     */
    public Point getInput() {
        return input;
    }

    /**
     * @return Punto de fin del ActiveStatement
     */
    public Point getOutput() {
        return output;
    }

    public int getWidth() {
        Shell shell = new Shell();
        GC gc = new GC(shell);
        return gc.stringExtent(text).x + 20;

    }

    public int getLine() {
        return this.line;
    }

    @Override
    public void sketch(GC gc, int line) {
    		Display display = Display.getCurrent();
		Color color;
		int border;
		if (line == this.line) {
			color = display.getSystemColor(SWT.COLOR_RED);
			border = 3;
		} else {
			color = display.getSystemColor(SWT.COLOR_BLACK);
			border = 1;
		}
		gc.setForeground(color);
		gc.setLineWidth(border);
        Rectangle rectangle = new Rectangle(input.x - 10 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 20, 40);
        gc.drawRectangle(rectangle);
        gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + (40 - gc.stringExtent(text).y) / 2);
    }

    @Override
    public void fix(int x) {
        input = new Point(input.x + x, input.y);
        output = new Point(output.x + x, output.y);
    }

}
