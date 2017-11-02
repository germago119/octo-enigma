package org.tec.datos1.graph.eclipse.Greibus;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.*;

public class ClassVisitor extends ASTVisitor {
	String clase = "";
	
	
	@Override
	public boolean visit(TypeDeclaration node) {
		if (node.isInterface() == false) {
			clase = node.getName().toString();
			
		}
		return super.visit(node);
	}
	
	public String getClassName() {
		return clase;
		
	}
	
}