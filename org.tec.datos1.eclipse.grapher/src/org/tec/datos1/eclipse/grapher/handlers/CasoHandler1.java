package org.tec.datos1.eclipse.grapher.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.dom.*;
import org.tec.datos1.eclipse.grapher.Greibus.ProyectInfo;
import org.tec.datos1.eclipse.grapher.Greibus.Visitor;


public class CasoHandler1 extends AbstractHandler {
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	ProyectInfo info = new ProyectInfo();
    	Visitor visitor = new Visitor(info.getCompilation("carroTest"));
    	info.getMetodos();
    	for (int i = 0; i< info.getNumOfElements(); i++) {
    		System.out.println(i + " NOMBRE METODO: " + info.getMethodDescriptorByIndex(i, ProyectInfo.METHOD_NAME));
        	System.out.println(i + " PERTENECE A LA CLASE : " + info.getMethodDescriptorByIndex(i, ProyectInfo.METHOD_CLASS));
        	
    	}
    	// PRUEBA PARA VER SI SE EJECUTA BIEN
    	//System.out.println("METODOS DE LA CLASE CARROTESt" + info.getMethodDescriptorByClass("carroTest", ProyectInfo.METHOD_NAME));
    	
    	ASTNode metodo = info.getMethod("segundoTest(int, String[])", "carroTest");
    	metodo.accept(visitor);
    	for (ASTNode nodo : visitor.getNodosImprimibles()) {
    		if (! visitor.getParent(nodo).equals(metodo)) {
    			System.out.println(" MI PAPA ES  " + visitor.getParent(nodo));
    			System.out.println(" LOS HIJOS DE MI PAPA SON " + visitor.getChildrenOf(visitor.getParent(nodo)));
    			System.out.println(" YO SOY UN " + nodo);
    		}
    		
    	}
       
        return null;
    }

}
