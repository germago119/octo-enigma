package org.tec.datos1.graph.eclipse.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class Visitor extends ASTVisitor {
	/**
	 * preVisit2 del ASTNode, si el nodo es una expresion necesaria se llama al visit de 
	 * la expresion, si es algo que no se necesita se retorna true y se sigue revisando, 
	 * si se encuentra algo necesario hace el print
	 * @param node expresion del codigo
	 * @return true si no lo necesita.
	 */
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
			return true;
		case ASTNode.BLOCK_COMMENT:
			return true;
		case ASTNode.METHOD_DECLARATION:
			return true;
		case ASTNode.MODIFIER:
			return true;
		case ASTNode.PRIMITIVE_TYPE:
			return true;
		case ASTNode.SIMPLE_NAME:
			return true;
		case ASTNode.NUMBER_LITERAL:
			return true;
		case ASTNode.BOOLEAN_LITERAL:
			return true;
		case ASTNode.SINGLE_VARIABLE_DECLARATION:
			return true;
		case ASTNode.VARIABLE_DECLARATION_EXPRESSION:
			return true;
		case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
			return true;
		case ASTNode.INFIX_EXPRESSION:
			return true;
		case ASTNode.ASSIGNMENT:
			return true;
		case ASTNode.POSTFIX_EXPRESSION:
			return true;
		case ASTNode.STRING_LITERAL:
			return true;
		case ASTNode.METHOD_INVOCATION:
			return true;
		default:
			System.out.println(node);
		}
		return true;
		
	}
		
	/**
	 * metodo visit del ASTNode extrae unicamente los if del metodo
	 * @param node Expresion de codigo
	 * @return false
	 */
	public boolean visitIf(IfStatement node) {
		System.out.println("if content: " + node.getExpression());
		//System.out.println(getChildren(node));
		//node.getThenStatement().accept(this);
		return false;
	}
	
	/**
	 * metodo visit del ASTNode extrae unicamente los while del metodo
	 * @param node Expresion de codigo
	 * @return false
	 */
	public boolean visitWhile(WhileStatement node) {
		System.out.println("while content: " + node.getExpression());
		//System.out.println(getChildren(node).size());
		//node.getBody().accept(this);
		return false;
	}
	
	/**
	 * metodo visit del ASTNode extrae unicamente los for del metodo
	 * @param node Expresion de codigo
	 * @return false
	 */
	public boolean visitFor(ForStatement node) {
		System.out.println("For content: " + node.getExpression());
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

	/**
	 * Obtiene los hijos de cada nodo
	 * @param nodo expresion de codigo
	 */
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
