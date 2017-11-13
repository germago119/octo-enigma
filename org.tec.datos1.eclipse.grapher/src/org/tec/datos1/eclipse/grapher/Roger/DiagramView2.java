package org.tec.datos1.eclipse.grapher.Roger;

import java.util.LinkedList;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.tec.datos1.eclipse.grapher.assets.ADP;
import org.tec.datos1.eclipse.grapher.assets.Illustrator;
import org.tec.datos1.eclipse.grapher.data.ASTData;
import org.tec.datos1.eclipse.grapher.data.DiMension;



public class DiagramView2 {
	private static Canvas canvas;
	static LinkedList<Illustrator> diagram;
	static DiMension diMension;
	static Combo methodSelector;
	private static Integer lineNumber = -1;
	
	/**
	 * Crea la vista para el plugin
	 * @param parent Componente de la interfaz en el cual se va a generar los graficos
	 */
	@Inject
	public DiagramView2(Composite parent) {
		GridLayout layout = new GridLayout();
		GridData diagramLayout = new GridData();
		diagramLayout.grabExcessVerticalSpace = true;
		diagramLayout.grabExcessHorizontalSpace = true;
		
		GridData comboLayout = new GridData();
		comboLayout.widthHint = 200;
		
		parent.setLayout(layout);
		
		methodSelector = new Combo(parent, SWT.NONE);
		methodSelector.setLayoutData(comboLayout);
		
		ScrolledComposite container = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		container.setLayoutData(diagramLayout);
		canvas = new Canvas(container, SWT.NONE);
		canvas.setSize(1000, 1000);
		container.setContent(canvas);



		diagram = new LinkedList<Illustrator>();
		

		//Anade todo al selector
		methodSelector.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				Select();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("Default selected");
			}
		});

		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				for(Illustrator Illustrator : diagram) {
					Illustrator.sketch(e.gc, lineNumber);
				}
			}
		});
	}
	
	public static void Select() {
		try {
			ADP astParser = new ADP();
			if (lineNumber == -1) {
				diagram = astParser.parse(ASTData.getMethod(methodSelector.getText()).getChildren());
			}else {
				ASTData storage = ASTData.getMethodByLine(lineNumber);
				diagram = astParser.parse(storage.getChildren());
				methodSelector.setText(((MethodDeclaration)storage.getElement()).getName().toString());
			}
			diMension = astParser.getdimension();
			canvas.setSize(diMension.maxWidth + 60, diMension.afterOutput.y + 40);
			if (diMension.maxWidth / 2 > 100) {
				for(Illustrator illustrator : diagram) {
					illustrator.fix(diMension.maxWidth / 2 - 80);
				}
			}
			canvas.redraw();
			
		} catch (Exception e1) {
			System.err.println("Tree parse ERROR");
			e1.printStackTrace();
			canvas.redraw();
		}
	}

	public static void setMethods(String[] methods) {
		methodSelector.setItems(methods);
	}

	public static void selectMethod(String method) {
		System.out.println("Seaching Method");
		System.out.println(method);
		String[] methods = methodSelector.getItems();
		for(int index = 0; index < methods.length ; index++) {
			System.out.println("Compared with: " + methods[index]);
			if (methods[index].equals(method)) {
				System.out.println("Method Selected");
				methodSelector.select(index);
				Select();
			}
		}
	}

	public static Integer getLineNumber() {
		return lineNumber;
	}


	public static void setLineNumber(Integer LineNumber) {
		lineNumber = LineNumber;
		canvas.redraw();
		
		
	}
	public static Combo getMethodSelector() {
		return methodSelector;
	}
}
