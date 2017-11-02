package org.tec.datos1.graph.eclipse.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;


public class ActiveStatement implements Illustrator {
		
	String text;
	Point input;
	Point output;
	
	/**
	 * Constructor para el diagrama de un ActiveStatement
	 * @param text Texto dentro del ActiveStatement
	 * @param input Punto de inicio del ActiveStatement
	 */
	public ActiveStatement(String text, Point input) {
		this.text = text;
		this.input = input;
		this.output = new Point(input.x, input.y + 40);
	}
	
	/**
	 * Constructor para el diagrama de un ActiveStatement
	 * @param text Texto dentro del ActiveStatement
	 * @param x Coordenada en x del punto de inicio del ActiveStatement
	 * @param y Coordenada en y del punto de inicio del ActiveStatement
	 */
	public ActiveStatement(String text, int x, int y) {
		this(text, new Point(x, y));
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
	
	
	@Override
	public void sketch(GC gc) {
		Rectangle rectangle = new Rectangle(input.x - 10 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 20, 40);
		gc.drawRectangle(rectangle);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + (40 - gc.stringExtent(text).y) / 2);
	}
}
