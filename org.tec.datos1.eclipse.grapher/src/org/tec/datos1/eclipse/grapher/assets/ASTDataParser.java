package org.tec.datos1.graph.eclipse.assets;

import java.util.LinkedList;

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
import org.tec.datos1.graph.eclipse.assets.Illustrator;
import org.tec.datos1.graph.eclipse.data.ASTData;


public class ASTDataParser {

	LinkedList<Illustrator> scene;
	Point lastOut;
	
	public LinkedList<Illustrator> parse(ASTData data){
		scene = new LinkedList<Illustrator>();
		
		lastOut = new Point(200, 20);
		
		sketch(data, lastOut.x, lastOut.y);
		
		return scene;
	}
	
	
	public void sketch(ASTData data, int x, int y) {	}
}
