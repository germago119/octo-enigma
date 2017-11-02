package org.tec.datos1.graph.eclipse.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class MethodStatement implements Illustrator {
	String text;
	Point input;
	Point output;
	Rectangle focus;
	
	/**
	 * Constructor para el diagrama de un metodo
	 * @param text Texto dentro del metodo
	 * @param input Punto de inicio del metodo
	 */
	public MethodStatement(String text, Point input) {
		this.text = text;
		this.input = input;
		this.output = new Point(input.x, input.y + 40);
	}
	
	/**
	 * Constructor para el diagrama de un metodo
	 * @param text Texto dentro del metodo
	 * @param x Coordenada en x del punto de inicio del metodo
	 * @param y Coordenada en y del punto de inicio del metodo
	 */
	public MethodStatement(String text, int x, int y) {
		this(text, new Point(x, y));
	}
	
	/**
	 * @return Punto de inicio del metodo
	 */
	public Point getInput() {
		return input;
	}
	
	/**
	 * @return Punto de fin del metodo
	 */
	public Point getOutput() {
		return output;
	}
	
	public void end() {
		this.focus = null;
	}
	
	@Override
	public void sketch(GC gc) {
		Rectangle rectangle = new Rectangle(input.x - 10 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 20, 40);
		gc.drawRectangle(rectangle);
		Rectangle externalRectangle = new Rectangle(input.x - 20 - gc.stringExtent(text).x / 2, input.y, gc.stringExtent(text).x + 40, 40);
		gc.drawRectangle(externalRectangle);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + (40 - gc.stringExtent(text).y) / 2);
	}
}
