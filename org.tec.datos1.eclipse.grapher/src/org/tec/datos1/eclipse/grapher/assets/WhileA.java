package org.tec.datos1.eclipse.grapher.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class WhileA implements Illustrator {

    String text;
    Point input;
    Point outputT;
    Point outputF;
    Point returnInput;
    Rectangle focus;
    int line;

    public WhileA(String text, Point input, int line) {
        Shell shell = new Shell();
        GC gc = new GC(shell);

        this.line = line;
        this.text = text;
        this.input = input;
        this.outputT = new Point(input.x, input.y + 60);
        this.outputF = new Point(input.x + 30 + gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
        this.returnInput = new Point(input.x - 30 - gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);

    }

    public WhileA(String text, int x, int y, int line) {
        this(text, new Point(x, y), line);
    }


    public Point getInput() {
        return input;
    }


    public Point getReturnInput() {
        return returnInput;
    }


    public Point getOutputT() {
        return outputT;
    }


    public Point getOutputF() {
        return outputF;
    }

    public int getWidth() {
        return outputF.x - returnInput.x;
    }

    @Override
    public void sketch(GC gc) {
        int[] shape = new int[]{input.x, input.y, outputF.x, outputF.y, outputT.x, outputT.y, returnInput.x, returnInput.y};

        gc.drawPolygon(shape);
        gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + 20);
    }

    @Override
    public void fix(int x) {
        input = new Point(input.x + x, input.y);
        outputT = new Point(outputT.x + x, outputT.y);
        outputF = new Point(outputF.x + x, outputF.y);
        returnInput = new Point(returnInput.x + x, returnInput.y);
    }


}
