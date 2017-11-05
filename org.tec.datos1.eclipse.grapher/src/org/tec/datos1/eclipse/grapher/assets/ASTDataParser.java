package org.tec.datos1.eclipse.grapher.assets;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.swt.graphics.Point;
import org.tec.datos1.eclipse.grapher.assets.Illustrator;
import org.tec.datos1.eclipse.grapher.data.ASTData;
import org.tec.datos1.eclipse.grapher.data.DiMension;
import org.tec.datos1.eclipse.grapher.assets.TypeL;


public class ASTDataParser {

    LinkedList<Illustrator> scene;
    int padding = 30;
    DiMension dimension;

    public void setPadding(int pad) {
        this.padding = pad;
    }

    public LinkedList<Illustrator> parse(List<ASTData> data) {
        scene = new LinkedList<Illustrator>();

        DiMension dimension = new DiMension();
        dimension.afterOutput = new Point(100, 20);
        dimension.maxWidth = 0;

        Run run = new Run(dimension.afterOutput);
        scene.add(run);
        dimension.afterOutput = run.getOutput();
        dimension.maxWidth = run.getWidth();

        dimension = sketch(data, dimension, this.scene);

        this.dimension = dimension;

        return this.scene;
    }


    public DiMension sketch(List<ASTData> data, DiMension dimension, LinkedList<Illustrator> scene) {
        for (ASTData dataElement : data) {
            ASTNode element = dataElement.getElement();
            Point input = new Point(dimension.afterOutput.x, dimension.afterOutput.y + padding);
            Line union = new Line(dimension.afterOutput, input);
            scene.add(union);
            if (element == null) {
                if (dataElement.getName().equals("Root") && dataElement.after) {
                    dataElement.getChildren();
                    System.out.println("If body: ");
                } else {
                    System.out.println("Else: ");
                }

            } else {
                String[] clase_aux = element.getClass().toString().split("\\.");
                String clase = clase_aux[clase_aux.length - 1];

                if (clase.equalsIgnoreCase("WhileStatement")) {

                    WhileStatement WhileA = (WhileStatement) element;
                    WhileA whileDiagram = new WhileA(WhileA.getExpression().toString(), input, dataElement.getLineNumber());
                    dimension.afterOutput = whileDiagram.getOutputT();
                    scene.add(whileDiagram);

                    DiMension bodydimension = new DiMension();
                    bodydimension.afterOutput = dimension.afterOutput;
                    bodydimension.maxWidth = whileDiagram.getWidth();
                    bodydimension = sketch(dataElement.getChildren(), bodydimension, scene);
                    dimension.afterOutput = bodydimension.afterOutput;

                    Line returnLine = new Line(dimension.afterOutput, whileDiagram.getReturnInput(), bodydimension.maxWidth, TypeL.RETURN);
                    scene.add(returnLine);

                    bodydimension.maxWidth += 40;

                    if (bodydimension.maxWidth > dimension.maxWidth) {
                        dimension.maxWidth = bodydimension.maxWidth;
                    }
                    dimension.afterOutput = new Point(dimension.afterOutput.x, dimension.afterOutput.y + 40);

                    Line falseLine = new Line(whileDiagram.getOutputF(), dimension.afterOutput, TypeL.JUMP, "false", bodydimension.maxWidth - whileDiagram.getWidth());
                    scene.add(falseLine);

                } else if (clase.equalsIgnoreCase("DoStatement")) {
                    DoStatement Do = (DoStatement) element;
                    dimension = sketch(dataElement.getChildren(), dimension, scene);

                    Point newInput = new Point(dimension.afterOutput.x, dimension.afterOutput.y + padding);
                    Line line = new Line(dimension.afterOutput, newInput, TypeL.NONE);
                    scene.add(line);
                    WhileA doDrawing = new WhileA(Do.getExpression().toString(), newInput, dataElement.getLineNumber());
                    scene.add(doDrawing);

                    dimension.afterOutput = doDrawing.getReturnInput();
                    input.y -= 10;
                    Line returnLine = new Line(dimension.afterOutput, input, TypeL.DORETURN);
                    scene.add(returnLine);

                    dimension.afterOutput = doDrawing.outputT;

                } else if (clase.equalsIgnoreCase("EnhancedForStatement")) {
                    EnhancedForStatement enhancedFor = (EnhancedForStatement) element;

                    WhileA whileDiagram = new WhileA(enhancedFor.getParameter().toString() + ":" + enhancedFor.getExpression(), input, dataElement.getLineNumber());
                    dimension.afterOutput = whileDiagram.getOutputT();
                    if (whileDiagram.getWidth() > dimension.maxWidth) {
                        dimension.maxWidth = whileDiagram.getWidth();
                    }
                    scene.add(whileDiagram);

                    DiMension bodyDimension = new DiMension();
                    bodyDimension.afterOutput = dimension.afterOutput;
                    bodyDimension.maxWidth = 0;
                    bodyDimension = sketch(dataElement.getChildren(), bodyDimension, scene);
                    dimension.afterOutput = bodyDimension.afterOutput;

                    Line returnLine = new Line(dimension.afterOutput, whileDiagram.getReturnInput(), bodyDimension.maxWidth, TypeL.RETURN);
                    scene.add(returnLine);
                    bodyDimension.maxWidth += 40;

                    if (bodyDimension.maxWidth > dimension.maxWidth) {
                        dimension.maxWidth = bodyDimension.maxWidth;
                    }
                    dimension.afterOutput = new Point(dimension.afterOutput.x, dimension.afterOutput.y + 40);

                    Line falseLine = new Line(whileDiagram.getOutputF(), dimension.afterOutput, TypeL.JUMP, "", bodyDimension.maxWidth - whileDiagram.getWidth());
                    scene.add(falseLine);

                } else if (clase.equalsIgnoreCase("ForStatement")) {
                    ForStatement For = (ForStatement) element;

                    WhileA whileDiagram = new WhileA(For.getExpression().toString(), input, dataElement.getLineNumber());
                    dimension.afterOutput = whileDiagram.getOutputT();
                    if (whileDiagram.getWidth() > dimension.maxWidth) {
                        dimension.maxWidth = whileDiagram.getWidth();
                    }
                    scene.add(whileDiagram);

                    DiMension bodyDimension = new DiMension();
                    bodyDimension.afterOutput = dimension.afterOutput;
                    bodyDimension.maxWidth = 0;
                    bodyDimension = sketch(dataElement.getChildren(), bodyDimension, scene);
                    dimension.afterOutput = bodyDimension.afterOutput;

                    Line returnLine = new Line(dimension.afterOutput, whileDiagram.getReturnInput(), bodyDimension.maxWidth, TypeL.RETURN);
                    scene.add(returnLine);
                    bodyDimension.maxWidth += 40;

                    if (bodyDimension.maxWidth > dimension.maxWidth) {
                        dimension.maxWidth = bodyDimension.maxWidth;
                    }
                    dimension.afterOutput = new Point(dimension.afterOutput.x, dimension.afterOutput.y + 40);

                    Line falseLine = new Line(whileDiagram.getOutputF(), dimension.afterOutput, TypeL.JUMP, "false", bodyDimension.maxWidth - whileDiagram.getWidth());
                    scene.add(falseLine);

                } else if (clase.equalsIgnoreCase("IfStatement")) {
                    IfStatement If = (IfStatement) element;
                    IfA ifDrawing = new IfA(If.getExpression().toString(), input, dataElement.getLineNumber());
                    if (ifDrawing.getWidth() > dimension.maxWidth) {
                        dimension.maxWidth = ifDrawing.getWidth();
                    }
                    scene.add(ifDrawing);

                    LinkedList<Illustrator> falseScene = new LinkedList<Illustrator>();
                    DiMension falseDimension = new DiMension();
                    falseDimension.afterOutput = new Point(ifDrawing.getOutputF().x + 20, ifDrawing.getOutputF().y);
                    falseDimension.maxWidth = 0;
                    falseDimension = sketch(dataElement.getChildren().get(1).getChildren(), falseDimension, falseScene); //False
                    if (falseDimension.afterOutput.x - falseDimension.maxWidth / 2 <= input.x) {
                        for (Illustrator Illustrator : falseScene) {
                            Illustrator.fix(input.x - (falseDimension.afterOutput.x - falseDimension.maxWidth / 2) + 20);
                        }
                        falseDimension.afterOutput.x += input.x - (falseDimension.afterOutput.x - falseDimension.maxWidth / 2) + 20;
                    }
                    Line elseLine = new Line(ifDrawing.getOutputF(), new Point(falseDimension.afterOutput.x, ifDrawing.getOutputF().y), "false");
                    scene.add(elseLine);
                    scene.addAll(falseScene);


                    LinkedList<Illustrator> trueScene = new LinkedList<Illustrator>();
                    DiMension trueDimension = new DiMension();
                    trueDimension.afterOutput = new Point(ifDrawing.getOutputT().x - 20, ifDrawing.getOutputT().y);
                    trueDimension.maxWidth = 0;
                    trueDimension = sketch(dataElement.getChildren().get(0).getChildren(), trueDimension, trueScene); //True

                    if (trueDimension.afterOutput.x + trueDimension.maxWidth / 2 >= input.x) {
                        for (Illustrator Illustrator : trueScene) {
                            Illustrator.fix(input.x - (trueDimension.afterOutput.x + trueDimension.maxWidth / 2) - 20);
                        }
                        trueDimension.afterOutput.x += input.x - (trueDimension.afterOutput.x + trueDimension.maxWidth / 2) - 20;
                    }
                    Line ifLine = new Line(ifDrawing.getOutputT(), new Point(trueDimension.afterOutput.x, ifDrawing.getOutputT().y), "true");
                    scene.add(ifLine);
                    scene.addAll(trueScene);

                    if (trueDimension.maxWidth > falseDimension.maxWidth) {
                        if (dimension.maxWidth < 2 * (trueDimension.maxWidth + this.padding)) {
                            dimension.maxWidth = 2 * (trueDimension.maxWidth + this.padding);
                        }
                    } else {
                        if (dimension.maxWidth < 2 * (falseDimension.maxWidth + this.padding)) {
                            dimension.maxWidth = 2 * (falseDimension.maxWidth + this.padding);
                        }
                    }

                    int outputY;
                    if (trueDimension.afterOutput.y >= falseDimension.afterOutput.y) {
                        outputY = trueDimension.afterOutput.y;
                    } else {
                        outputY = falseDimension.afterOutput.y;
                    }
                    dimension.afterOutput = new Point(input.x, outputY + 10);
                    Line trueLine = new Line(trueDimension.afterOutput, dimension.afterOutput, TypeL.JOIN);
                    Line falseLine = new Line(falseDimension.afterOutput, dimension.afterOutput, TypeL.JOIN);
                    scene.add(trueLine);
                    scene.add(falseLine);

                } else if (clase.equalsIgnoreCase("TryStatement")) {
                    TryStatement Try = (TryStatement) element;

                    System.out.println("Try:");

                } else if (clase.equalsIgnoreCase("ExpressionStatement")) {

                    ExpressionStatement expression = (ExpressionStatement) element;

                    ActiveStatement activeStatement = new ActiveStatement(expression.toString().replaceAll("\n", ""), input, dataElement.getLineNumber());
                    dimension.afterOutput = activeStatement.getOutput();
                    if (activeStatement.getWidth() > dimension.maxWidth) {
                        dimension.maxWidth = activeStatement.getWidth();
                    }
                    scene.add(activeStatement);

                } else if (clase.equalsIgnoreCase("VariableDeclarationStatement")) {
                    VariableDeclarationStatement variable = (VariableDeclarationStatement) element;
                    ActiveStatement activeStatement = new ActiveStatement(variable.toString().replaceAll("\n", ""), input, dataElement.getLineNumber());
                    dimension.afterOutput = activeStatement.getOutput();
                    if (activeStatement.getWidth() > dimension.maxWidth) {
                        dimension.maxWidth = activeStatement.getWidth();
                    }
                    scene.add(activeStatement);

                } else if (clase.equalsIgnoreCase("MethodInvocation")) {
                    MethodInvocation method = (MethodInvocation) element;
                    String args = method.arguments().toString().replace('[', '(').replace(']', ')');
                    MethodA methodDiagram = new MethodA(method.getName().toString() + args, input, dataElement.getLineNumber());
                    dimension.afterOutput = methodDiagram.getOutput();
                    if (methodDiagram.getWidth() > dimension.maxWidth) {
                        dimension.maxWidth = methodDiagram.getWidth();
                    }
                    scene.add(methodDiagram);
                } else {
                    System.out.println("OTHER: " + clase);
                }

            }
        }
        return dimension;
    }

    public DiMension getdimension() {
        return this.dimension;
    }
}
