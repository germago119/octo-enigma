package org.tec.datos1.graph.eclipse.Greibus;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;



public class Caso1Handler extends AbstractHandler {
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	ProyectInfo info = new ProyectInfo();
    	info.getMetodos();
    	for (int i = 0; i< info.getNumOfElements(); i++) {
    		System.out.println(i + " NOMBRE METODO: " + info.getMethodDescriptorByIndex(i, ProyectInfo.METHOD_NAME));
        	System.out.println(i + " PERTENECE A LA CLASE : " + info.getMethodDescriptorByIndex(i, ProyectInfo.METHOD_CLASS));
        	
    	}
    	// PRUEBA PARA VER SI SE EJECUTA BIEN
    	System.out.println("METODOS DE LA CLASE CARROTESt" + info.getMethodDescriptorByClass("carroTest", ProyectInfo.METHOD_NODE));
       
        return null;
    }

    
}