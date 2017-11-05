package org.tec.datos1.eclipse.grapher.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MethodA implements Illustrator {
    String text;
    Point input;
    Point output;
    Rectangle focus;
    int line;


    /**
     * Constructor para el diagrama de un metodo
     *
     * @param text  Texto dentro del metodo
     * @param input Punto de inicio del metodo
     */
    public MethodA(String text, Point input, int line) {
        this.text = text;
        this.input = input;
        this.output = new Point(input.x, input.y + 40);
        this.line = line;
    }

    /**
     * Constructor para el diagrama de un metodo
     *
     * @param text Texto dentro del metodo
     * @param x    Coordenada en x del punto de inicio del metodo
     * @param y    Coordenada en y del punto de inicio del metodo
     */
    public MethodA(String text, int x, int y, int line) {
        this(text, new Point(x, y), line);
    }

    /**
     * @return Punto de inicio del metodo
     */
    public Point getInput() {
        return input;
    }

    public int getWidth() {
        Shell shell = new Shell();
        GC gc = new GC(shell);
        return gc.stringExtent(text).x + 20;

    }

    public int getLine() {
        return this.getLine();

    }


    /**
     * @return Punto de fin del metodo
     */
    public Point getOutput() {
        return output;
    }


    @Override
    public void sketch(GC gc) {
        Rectangle rectangle = new Rectangle(input.x - 10 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 20, 40);
        gc.drawRectangle(rectangle);
        Rectangle externalRectangle = new Rectangle(input.x - 20 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 40, 40);
        gc.drawRectangle(externalRectangle);
        gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + (40 - gc.stringExtent(text).y) / 2);
    }

    @Override
    public void fix(int x) {
        input = new Point(input.x + x, input.y);
        output = new Point(output.x + x, output.y);
    }

}

	
