package org.tec.datos1.graph.eclipse.views;

import java.util.LinkedList;

import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.tec.datos1.graph.eclipse.assets.Illustrator;


public class DiagramView {
	Canvas canvas;
	LinkedList<Illustrator> diagram;

	@Inject
	public DiagramView(Composite parent) {
		//Aca se llama a la estructura donde se guardan los datos y se refrencia esta instancia
		
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		
		Combo mainSelector = new Combo(parent, SWT.NONE);
		Combo auxSelector = new Combo(parent, SWT.NONE);
		
		
		ScrolledComposite container = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		this.canvas = new Canvas(container, SWT.NONE);
		canvas.setSize(500, 500);
		container.setContent(canvas);
	}
	
	public void draw() { 
		canvas.redraw();
	}
}
