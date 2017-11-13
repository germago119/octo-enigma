package org.tec.datos1.eclipse.grapher.data;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTData {

    static ASTData root;
    static CompilationUnit compilationUnit;
    public Boolean after;
    String name;
    ASTNode element;
    List<ASTData> children;


    public ASTData(ASTNode element, String name) {
        this.name = name;
        this.element = element;
        this.children = new ArrayList<ASTData>();

    }

    public ASTData(ASTNode element, Boolean after, String name) {
        this(element, name);
        this.after = after;
    }


    public static ASTData getRoot() {
        return root;
    }

    public static void setRoot(ASTData Root) {
        root = Root;
    }

    public static void setCompilationUnit(CompilationUnit Unit) {
        compilationUnit = Unit;
    }

    public static CompilationUnit getCompUnit() {
		return compilationUnit;
	}
    
    public static List<String> getMethods() {
		List<String> result = new ArrayList<>();
		if (root == null) {
			return null;
		}
		for (ASTData method :root.getChildren()) {
			result.add(method.getName());
		}
		return result;
	}
	
	public static ASTData getMethod(String Method) {
		for (ASTData method :root.getChildren()) {
			if (method.getName().equals(Method)) return method;
		}
		return null;
	}
	
	public static ASTData getMethodByLine(Integer Line) {
		ASTData result = null;
		for (ASTData method :root.getChildren()) {
			if (method.getLineNumber() > Line) {
				return result;
			}
			result = method;
		}
		return result;
	}
	
    public ASTNode getElement() {
        return this.element;
    }

    public List<ASTData> getChildren() {
        return children;
    }

    public void setAfter() {
        this.after = true;
    }

    public String getName() {
        return this.name;
    }

    public void addChild(ASTData Child) {
        children.add(Child);
    }

    /**
     * Busca la linea de codigo especifica dentro del ASTData
     *
     * @param lineNumber
     * @return
     */
    public ASTData findLine(Integer lineNumber) {

        if (element != null && (compilationUnit.getLineNumber(element.getStartPosition()) == lineNumber)) {
            return this;
        } else if (children.size() != 0) {
            for (ASTData child : children) {
                ASTData tempNode = child.findLine(lineNumber);
                if (tempNode != null)
                    return tempNode;
            }
        }
        return null;
    }


    /**
     * Cambia el AST de Eclipse a una estructura definida por mi
     *
     * @param Statements Lista de nodos hijos para agregar
     */
    public void addChildren(List<Block> Statements) {

        for (Object statement : Statements) {
            ASTNode child = (ASTNode) statement;
            this.addChildrenAux(child);
        }
    }

    public void deleteChildren() {
        this.children.clear();
        ASTData.root = null;
    }


    
    @SuppressWarnings("unchecked")
	public void addChildrenAux(ASTNode child) {
		if (child == null) {return;}
		String[] clazz_aux = child.getClass().toString().split("\\.");
		String clazz = clazz_aux[clazz_aux.length - 1];

		if (clazz.equalsIgnoreCase("WhileStatement")) {
			
			WhileStatement While = (WhileStatement) child;
			ASTData WhileStorage = new ASTData(While,While.getExpression().toString());
			this.addChild(WhileStorage);
			
			Block block = (Block) While.getBody();
			
			WhileStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("DoStatement")) {
			DoStatement Do = (DoStatement) child;
			ASTData DoStorage = new ASTData(Do, Do.getExpression().toString());
			this.addChild(DoStorage);
			//System.out.println("Do: \n" + Do.getExpression());
			Block block = (Block) Do.getBody();

			DoStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("EnhancedForStatement")) {
			EnhancedForStatement EnhancedFor = (EnhancedForStatement) child;
			ASTData EnhancedForStorage = new ASTData(EnhancedFor,EnhancedFor.getExpression().toString());
			this.addChild(EnhancedForStorage);
			Block block = (Block) EnhancedFor.getBody();

			EnhancedForStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("ForStatement")) {
			ForStatement For = (ForStatement) child;
			ASTData ForStorage = new ASTData(For,For.getExpression().toString());
			this.addChild(ForStorage);
			Block block = (Block) For.getBody();

			ForStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("IfStatement")) {
			IfStatement If = (IfStatement) child;
			ASTData IfStorage = new ASTData(If,If.getExpression().toString());
			this.addChild(IfStorage);
			ASTData thenStorage = new ASTData(null,true,If.getExpression().toString());
			IfStorage.addChild(thenStorage);
			Block b1 = (Block) If.getThenStatement();
			thenStorage.addChildren(b1.statements());

			if (If.getElseStatement() instanceof Block) {
				ASTData elseStorage = new ASTData(null,false,If.getExpression().toString());
				IfStorage.addChild(elseStorage);
				Block b2 = (Block) If.getElseStatement();
				elseStorage.addChildren(b2.statements());
			} else {
				ASTData elseStorage = new ASTData(null,false, If.getExpression().toString());
				IfStorage.addChild(elseStorage);
				IfStatement If2 = (IfStatement) If.getElseStatement();
				elseStorage.addChildrenAux(If2);
			}

		} else if(clazz.equalsIgnoreCase("TryStatement")){
			TryStatement Try = (TryStatement) child;
			ASTData TryStorage = new ASTData(Try,"Try");
			this.addChild(TryStorage);
			
			Block block = (Block) Try.getBody();

			TryStorage.addChildren(block.statements());

		} else if (clazz.equalsIgnoreCase("ExpressionStatement")) {
			
			ExpressionStatement Expression = (ExpressionStatement) child;
			try{
				
				MethodInvocation methodInvocation = (MethodInvocation) Expression.getExpression();
				String methodClass = methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName();
				if (methodClass.split("\\.")[0].equals("java")) {
					ASTData ExpressionStorage = new ASTData(Expression,Expression.toString());
					this.addChild(ExpressionStorage);
					return;
				}
				ASTData MethoInvocationStorage = new ASTData(methodInvocation, methodInvocation.toString());
				this.addChild(MethoInvocationStorage);
				
			}catch(Exception ex) {
				ASTData ExpressionStorage = new ASTData(Expression,Expression.toString());
				this.addChild(ExpressionStorage);
			}
			
		}else if (clazz.equalsIgnoreCase("VariableDeclarationStatement")) {
			VariableDeclarationStatement Variable = (VariableDeclarationStatement) child;
			ASTData VariableStorage = new ASTData(Variable,Variable.toString());
			this.addChild(VariableStorage);
		}else {}

    }


 
    public Integer getLineNumber() {
        return compilationUnit.getLineNumber(element.getStartPosition());
    }
}
    
