package org.tec.datos1.eclipse.grapher.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class IfA implements Illustrator {

    String text;
    Point input;
    Point outputT;
    Point outputF;
    Rectangle focus;
    int[] shape;
    int line;

    /**
     * Constructor para el diagrama de un if
     *
     * @param text  Texto dentro del if
     * @param input Punto de inicio del if
     */
    public IfA(String text, Point input, int line) {
        Shell shell = new Shell();
        GC gc = new GC(shell);

        this.text = text;
        this.line = line;
        this.input = input;
        this.outputT = new Point(input.x - 30 - gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
        this.outputF = new Point(input.x + 30 + gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);

    }

    /**
     * Constructor para el diagrama de un ActiveStatement
     *
     * @param text Texto dentro del ActiveStatement
     * @param x    Coordenada en x del punto de inicio del if
     * @param y    Coordenada en y del punto de inicio del if
     */
    public IfA(String text, int x, int y, int line) {
        this(text, new Point(x, y), line);
    }

    /**
     * @return Punto de fin en caso de que sea "true" el if
     */
    public Point getOutputT() {
        return outputT;
    }

    /**
     * @return Punto de inicio del if
     */
    public Point getInput() {
        return input;
    }

    /**
     * @return Punto de fin en caso de que sea "false" el if
     */
    public Point getOutputF() {
        return outputF;
    }

    public int getWidth() {
        return outputF.x - outputT.x;
    }


    @Override
    public void sketch(GC gc, int line) {
        shape = new int[]{input.x, input.y, outputF.x, outputF.y, input.x, input.y + gc.stringExtent(text).y + 40, outputT.x, outputT.y};	
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
		gc.drawPolygon(shape);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + 20);
        gc.drawPolygon(shape);
        gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + 20);
    }

    @Override
    public void fix(int x) {
        input = new Point(input.x + x, input.y);
        outputT = new Point(outputT.x + x, outputT.y);
        outputF = new Point(outputF.x + x, outputF.y);
    }


}
