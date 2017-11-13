package org.tec.datos1.eclipse.grapher.Roger;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.tec.datos1.eclipse.grapher.data.ASTData;



public class Visitor2 extends ASTVisitor {
    ASTData Root = new ASTData(null, "Root");
    
   
    @SuppressWarnings("unchecked")
	@Override
    public boolean visit(MethodDeclaration methodNode) {
    		
   
    	if (ASTData.getRoot() == null) {
    		ASTData.setRoot(Root);
    		} 
    	
    	try {
    			ASTData dataMethod = new ASTData(methodNode,methodNode.getName().toString());
    			Root.addChild(dataMethod);
    			Block b1 = (Block)methodNode.getBody();
    			dataMethod.addChildren(b1.statements());
    			
    	}catch(Exception ex) {ex.printStackTrace();}

        return super.visit(methodNode);
    }
   

    
}