package org.tec.datos1.graph.eclipse.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;
import org.tec.datos1.graph.eclipse.data.ASTData;

public class MethodVisitor extends ASTVisitor {
    List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();

    /**
     * metodo visit del ASTNode el cual extrae el metodo, lo agrega a la lista
     *
     * @param node Expresion de codigo
     * @return "true" para revisar los hijos
     */

    //TEMPORAL REVISAR
    /*@Override
	public boolean visit(MethodDeclaration node) {

		methods.add(node);
		return super.visit(node);
	}
	*/
    public List<MethodDeclaration> getMethods() {
        return methods;
    }

    ////////////////////////////////////////////////////////////////////
    ASTData Root = new ASTData(null, "Root");


    @Override
    public boolean visit(MethodDeclaration nodeMethod) {


        if (ASTData.getRoot() == null) {
            ASTData.setRoot(Root);
        }
        try {
            ASTData dataMethod = new ASTData(nodeMethod, nodeMethod.getName().toString());
            System.out.println(dataMethod.getName());
            Root.addChild(dataMethod);
            Block block1 = (Block) nodeMethod.getBody();
            dataMethod.addChildren(block1.statements());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return super.visit(nodeMethod);
    }


}