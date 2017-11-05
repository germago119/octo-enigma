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

    public static List<String> getMethods() {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return null;
        }
        for (ASTData method : root.getChildren()) {
            result.add(method.getName());
        }
        return result;
    }

    public static ASTData getMethod(String Method) {
        for (ASTData method : root.getChildren()) {
            if (method.getName().equals(Method)) return method;
        }
        return null;
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


    //MAE OCUPO QUE METAS LOS BLOQUES DE CODIGO ACA, QUE A PUROS IF VERIFIQUE SI ES UN wHILE
    //POR EJEMPLO Y LE APLIQUE UN THIS.ADDCHILD(WHILESTORAGE) Y UN WHILESTORAGE.ADDCHILDREN(BLOCK.STATEMENTS())
    //POR ESO IMPORTE UN MONTON DE VARAS

    /**
     * Metodo auxiliar ayuda al metodo addChildren para poder funcionar
     */
    public void addChildrenAux(ASTNode child) {
        if (child == null) {
            return;
        }

        //EJEMPLO VOS SABES MEJOR USAR TODO ESTO ENTONCES LO PUEDES CAMBIAR

        String[] clase_temp = child.getClass().toString().split("\\.");
        String clase = clase_temp[clase_temp.length - 1];

        if (clase.equalsIgnoreCase("WhileStatement")) {

            WhileStatement While = (WhileStatement) child;
            ASTData WhileStorage = new ASTData(While, While.getExpression().toString());
            this.addChild(WhileStorage);

            Block block = (Block) While.getBody();

            WhileStorage.addChildren(block.statements());

        }
    }

    //TODO PRINT ARBOL

    /**
     * Muestra todo lo que hay dentro del arbol
     *
     * @throws ScriptException
     */
    public void print() throws ScriptException {
        if (element == null) {
            if (this.after) {

            } else {
                System.out.println("Else: ");
            }

        } else {
            //IF PARA CADA STATEMNET Y UN PRINT

            //EJEMPLO VOS SABES MEJOR USAR TODO ESTO ENTONCES LO PUEDES CAMBIAR en serio cambialo si sabes otra forma

            String[] clase_temp = element.getClass().toString().split("\\.");
            String clase = clase_temp[clase_temp.length - 1];

            System.out.print(compilationUnit.getLineNumber(element.getStartPosition()) + " ");

            if (clase.equalsIgnoreCase("WhileStatement")) {

                WhileStatement While = (WhileStatement) element;

                System.out.println("While(" + While.getExpression() + ")");
            }

        }


        for (ASTData child : children) {
            child.print();

        }

    }

    public Integer getLineNumber() {
        return compilationUnit.getLineNumber(element.getStartPosition());
    }
}
    
