package org.tec.datos1.graph.eclipse.Greibus;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class Visitor extends ASTVisitor {
	
	@Override
	public boolean preVisit2(ASTNode node) {
		switch (node.getNodeType()) {
		case ASTNode.IF_STATEMENT:
			//System.out.println(node);
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
		case ASTNode.VARIABLE_DECLARATION_EXPRESSION:
		case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
		case ASTNode.INFIX_EXPRESSION:
		case ASTNode.ASSIGNMENT:
		case ASTNode.POSTFIX_EXPRESSION:
		case ASTNode.STRING_LITERAL:
		case ASTNode.METHOD_INVOCATION:
		case ASTNode.ARRAY_TYPE:
		case ASTNode.SIMPLE_TYPE:
		case ASTNode.ARRAY_INITIALIZER:
			return true;
		default:
			//System.out.println("Tipo padre: " + node.getProperty(ASTNode.TEXT_ELEMENT));
			System.out.println(node);
		}
		return true;
		
	}
		
	
	public boolean visitIf(IfStatement node) {
		System.out.println("if condition: " + "(" +node.getExpression() + ")");
		//System.out.println("else : " + node.getElseStatement());
		//System.out.println(getChildren(node));
		//node.getThenStatement().accept(this);
		return false;
	}
	
	
	public boolean visitWhile(WhileStatement node) {
		System.out.println("while condition: " + "(" + node.getExpression() + ")");
		//System.out.println(getChildren(node).size());
		//node.getBody().accept(this);
		return false;
	}
	
	
	public boolean visitFor(ForStatement node) {
		System.out.println("For condition: " + "(" + node.getExpression() + ")");
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
	public static List<ASTNode> getChildren(ASTNode node) {
		List<ASTNode> children = new ArrayList<ASTNode>();
		List list = node.structuralPropertiesForType();
		for (int i = 0; i < list.size(); i++) {
			Object child = node.getStructuralProperty((StructuralPropertyDescriptor)list.get(i));
			if (child instanceof ASTNode) {
				children.add((ASTNode) child);
			}
			
		}
		return children;
	}
	
}