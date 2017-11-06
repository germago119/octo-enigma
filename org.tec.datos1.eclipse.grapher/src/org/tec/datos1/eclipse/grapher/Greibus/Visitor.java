package org.tec.datos1.eclipse.grapher.Greibus;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.*;

public class Visitor extends ASTVisitor {
	CompilationUnit unit;
	List<Integer> lineNumbers = new ArrayList<Integer>();
	List<Point> lineElse = new ArrayList<Point>();
	Stack<ASTNode> pila = new Stack<ASTNode>();
	List<ASTNode> nodosImprimibles = new ArrayList<ASTNode>();
	
	
	
	
	public Visitor(CompilationUnit unit) {
		this.unit = unit;
		this.lineNumbers.add(-1);
		
	}
	
	public Visitor() {
		super();
	}
	
	@Override
	public boolean preVisit2(ASTNode node) {
		ASTNode lastNode = getParent(node);
		
		if (unit != null) {
			int lineNumer = unit.getLineNumber(node.getStartPosition()) - 1;
			if (this.lineNumbers.contains(lineNumer))
				return true;
			this.lineNumbers.add(lineNumer);
		}
			
		switch (node.getNodeType()) {
		case ASTNode.IF_STATEMENT:
			this.visitIf((IfStatement) node);
			break;
		case ASTNode.WHILE_STATEMENT:
			this.visitWhile((WhileStatement) node);
			break;
		case ASTNode.FOR_STATEMENT:
			this.visitFor((ForStatement) node);
		case ASTNode.BLOCK:
		case ASTNode.BLOCK_COMMENT:
		case ASTNode.METHOD_DECLARATION:
		case ASTNode.MODIFIER:
		case ASTNode.PRIMITIVE_TYPE:
		case ASTNode.SIMPLE_NAME:
		case ASTNode.NUMBER_LITERAL:
		case ASTNode.BOOLEAN_LITERAL:
		case ASTNode.SINGLE_VARIABLE_DECLARATION:
		//case ASTNode.VARIABLE_DECLARATION_EXPRESSION:
		case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
		case ASTNode.INFIX_EXPRESSION:
		case ASTNode.ASSIGNMENT:
		case ASTNode.POSTFIX_EXPRESSION:
		case ASTNode.STRING_LITERAL:
		case ASTNode.METHOD_INVOCATION:
		//case ASTNode.ARRAY_TYPE:
		case ASTNode.SIMPLE_TYPE:
		case ASTNode.ARRAY_INITIALIZER:
			return true;
		default:
			nodosImprimibles.add(node);
			Point pNode = getPoint(node);
			ASTNode papa = lastNode;
			if (papa != null)
				System.out.println(" MI PAPA ES " + papa.getClass().getName());
			for (Point p : lineElse) {
				if (pNode.getX() > p.getX() && pNode.getY() < p.getY())
					System.out.println(" PERTENECE ELSE ");
			}
			
			System.out.println(node);
		}
		return true;
		
	}
	
	public Point getPoint(ASTNode node) {
		Point p = new Point();
		int startLine = unit.getLineNumber(node.getStartPosition()) - 1;
		int endLine = unit.getLineNumber(node.getLength() + node.getStartPosition() - 1);
		p.setLocation(startLine, endLine);
		return p;
	}
		
	
	public boolean visitIf(IfStatement node) {
		System.out.println("if condition: " + "(" +node.getExpression() + ")");
		ASTNode papa = getParent(node);
		nodosImprimibles.add(node);
		
		if (papa != null)
			System.out.println(" SOY UN IF Y MI PAPA ES " + papa.getClass().getName());
		else
			System.out.println(" SOY UN IF Y NO TENGO PAPA ");
		pila.push(node);
		if (node.getElseStatement() != null) {
			nodosImprimibles.add(node.getElseStatement());
			Point p = getPoint(node.getElseStatement());
			lineElse.add(p);
			
			
		}
		
		//System.out.println(" CUERPO " + node.getThenStatement());
		//System.out.println(" ELSE " + node.getElseStatement());
		
		return false;
	}
	
	
	public boolean visitWhile(WhileStatement node) {
		System.out.println("while condition: " + "(" + node.getExpression() + ")");
		ASTNode papa = getParent(node);
		nodosImprimibles.add(node);
		if (papa != null)
			System.out.println(" SOY UN WHILE Y MI PAPA ES " + papa.getClass().getName());
		else
			System.out.println(" SOY UN WHILE Y NO TENGO PAPA ");
			
		pila.push(node);
		
		//System.out.println(getChildren(node).size());
		//System.out.println(" CUERPO " + node.getBody());
		//node.getBody().accept(this);
		return false;
	}
	
	
	public boolean visitFor(ForStatement node) {
		System.out.println("For condition: " + "(" + node.getExpression() + ")");
		ASTNode papa = getParent(node);
		nodosImprimibles.add(node);
		if (papa != null)
			System.out.println(" SOY UN FOR Y MI PAPA ES " + papa.getClass().getName());
		else
			System.out.println(" SOY UN FOR Y NO TENGO PAPA ");
		pila.push(node);
		//node.getBody().accept(this);
		return false;
	}
	
//	@Override
//	public boolean visit(Assignment node) {
//		System.out.println("Assignment expresion: " + node.getOperator());
//		//node.getBody().accept(this);
//		return false;
//	}
	
	@Override
	public boolean visit(InstanceofExpression node) {
		System.out.println("Instance derecha expresion: " + node.getRightOperand()
		+ "Instance izquierda: " + node.getLeftOperand());
		//node.getBody().accept(this);
		return false;
	}
//	public Object[] getChildren(ASTNode node) {
//    	List list = node.structuralPropertiesForType();
//    	for (int i = 0; i < list.size(); i++) {
//    		StructuralPropertyDescriptor curr = (StructuralPropertyDescriptor) list.get(i);
//    		Object child = node.getStructuralProperty(curr);
//    		if (child instanceof List) {
//    			return ((List) child).toArray();
//    		} else if (child instanceof ASTNode) {
//    			return new Object[] { child };
//    		}
//    		
//    	}
//		return new Object[0];
//    }
	
	public ASTNode getParent (ASTNode node) {
		ASTNode lasNode = null;
		boolean papudo = true;
		while (papudo) {
			if (pila.isEmpty())
				
				return node.getParent().getParent();
			else
				lasNode = pila.peek();
			
			ASTNode nodeTemp = node.getParent();
			while (nodeTemp != null && nodeTemp.getNodeType() != ASTNode.METHOD_DECLARATION) {
				if (! nodeTemp.equals(lasNode)) {
					nodeTemp = nodeTemp.getParent();
				} else {
					break;
				}
				
			}
			if (nodeTemp.getNodeType() == ASTNode.METHOD_DECLARATION)
				System.out.println(" SOY UN METHOD " + nodeTemp.getClass().getName());
			if (nodeTemp != null && ! nodeTemp.equals(lasNode)) {
				System.out.println("SOY LA PILA Y SOY " + lasNode);
				if (! pila.isEmpty()) {
					pila.pop();
				}
				lasNode = nodeTemp;
			} else {
				papudo = false;
			}

			
		}
		
		return lasNode;
	}
	
	public List<ASTNode> getChildrenOf(ASTNode node) {
		List<ASTNode> childrens = new ArrayList<ASTNode>();
		for (ASTNode node1 : this.nodosImprimibles) {
			if (this.getParent(node1).equals(node)) {
				childrens.add(node1);
			}
			
		}
		return childrens;
		
	}
	
	public List<ASTNode> getNodosImprimibles () {
		return this.nodosImprimibles;
	}
}