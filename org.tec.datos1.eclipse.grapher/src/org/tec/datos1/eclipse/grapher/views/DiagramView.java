package org.tec.datos1.eclipse.grapher.views;

import java.util.LinkedList;
import javax.inject.Inject;

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
import org.tec.datos1.eclipse.grapher.assets.ASTDataParser;
import org.tec.datos1.eclipse.grapher.assets.Illustrator;
import org.tec.datos1.eclipse.grapher.data.ASTData;
import org.tec.datos1.eclipse.grapher.data.DiMension;


public class DiagramView {
    static Combo methodSelector;
    private static Integer lineNumber;
    Canvas canvas;
    LinkedList<Illustrator> diagram;
    DiMension diMension;

    @Inject
    public DiagramView(Composite parent) {

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
        this.canvas = new Canvas(container, SWT.NONE);
        canvas.setSize(1000, 1000);
        container.setContent(canvas);


        ASTDataParser astParser = new ASTDataParser();
        diagram = new LinkedList<Illustrator>();


        methodSelector.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    diagram = astParser.parse(ASTData.getMethod(methodSelector.getText()).getChildren());
                    diMension = astParser.getdimension();
                    canvas.setSize(diMension.maxWidth + 60, diMension.afterOutput.y + 40);
                    if (diMension.maxWidth / 2 > 100) {
                        for (Illustrator illustrator : diagram) {
                            illustrator.fix(diMension.maxWidth / 2 - 80);
                        }
                    }
                } catch (Exception e1) {
                    System.err.println("Error with parse on tree");
                    e1.printStackTrace();
                }
                canvas.redraw();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                System.out.println("Default selected");
            }
        });

        canvas.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                for (Illustrator widget : diagram) {
                    widget.sketch(e.gc);
                }
            }
        });
    }

    public static void setMethods(String[] methods) {
        methodSelector.setItems(methods);
    }

    public static Integer getLineNumber() {
        return lineNumber;
    }

    public static void setLineNumber(Integer lineNumber) {
        DiagramView.lineNumber = lineNumber;
    }

    /**
     * Actualiza el diagrama y la pantalla
     */
    public void draw() {
        canvas.redraw();
    }


}
