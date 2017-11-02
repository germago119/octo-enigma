package org.tec.datos1.graph.eclipse.assets;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Line implements Illustrator {
	Point first;
	Point last;
	
	public Line(Point first, Point last) {
		this.first = first;
		this.last = last;
	}
	
	
	public void sketch(GC gc) {
		if(last.y < first.y) {
			gc.drawLine(first.x, first.y, first.x, first.y + 10);
			gc.drawLine(first.x, first.y + 10, 20, first.y + 10);
			gc.drawLine(20, first.y + 10, 20, last.y);
			gc.drawLine(20, last.y, last.x, last.y);
		} else if (first.x != last.x) {
			gc.drawLine(first.x, first.y, last.x, first.y);
			gc.drawLine(last.x, first.y, last.x, last.y);
		} else {
			gc.drawLine(first.x, first.y, last.x, last.y);
		}
	}
}
