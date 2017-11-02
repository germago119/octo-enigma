package org.tec.datos1.graph.eclipse.assets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class IfStatement implements Illustrator {
	
	String text;
	Point input;
	Point outputT;
	Point outputF;
	GC gc;
	Rectangle focus;
	int[] shape;
	
	/**
	 * Constructor para el diagrama de un if
	 * @param text Texto dentro del if
	 * @param input Punto de inicio del if
	 */
	public IfStatement(String text, Point input) {
		this.text = text;
		this.input = input;
		this.outputT = new Point(input.x - 30 - gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		this.outputF = new Point(input.x + 30 + gc.stringExtent(text).x / 2, input.y + gc.stringExtent(text).y / 2 + 20);
		shape = new int[] {input.x, input.y, outputF.x, outputF.y, input.x, input.y + gc.stringExtent(text).y + 40, outputT.x, outputT.y};
	}
	/**
	 * Constructor para el diagrama de un ActiveStatement
	 * @param text Texto dentro del ActiveStatement
	 * @param x Coordenada en x del punto de inicio del if
	 * @param y Coordenada en y del punto de inicio del if
	 */
	public IfStatement(String text, int x, int y) {
		this(text, new Point(x, y));
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
	
	public void end() {
		this.focus = null;
	}
	
	@Override
	public void sketch(GC gc) {
		gc.drawPolygon(shape);
		gc.drawText(text, input.x - gc.stringExtent(text).x / 2, input.y + 20);
	}
	
}
