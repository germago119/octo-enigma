package org.tec.datos1.graph.eclipse.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class WhileStatement implements Illustrator {

	String text;
	Point input;
	Point outputT;
	Point outputF;
	Point returnInput;
	Rectangle focus;
	int[] shape;
	
	public WhileStatement(String text, Point input) {
		this.input = input;
		this.text = text;
		
		shape = new int[] {input.x, input.y, outputF.x, outputF.y, outputT.x, outputT.y, returnInput.x, returnInput.y};
	}
	
	public WhileStatement(String text, int x, int y) {
		this(text, new Point(x, y));
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
	
	
	@Override
	public void sketch(GC gc) {
		this.outputT = new Point(input.x, input.y + gc.stringExtent(text).y + 40);
		this.outputF = new Point(input.x + 30 + gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		this.returnInput = new Point(input.x - 30 - gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		
		gc.drawPolygon(shape);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + 20);
	}
	
}
