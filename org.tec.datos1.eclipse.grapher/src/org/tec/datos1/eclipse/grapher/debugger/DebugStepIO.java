package org.tec.datos1.eclipse.grapher.debugger;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.tec.datos1.eclipse.grapher.data.*;
import org.tec.datos1.eclipse.grapher.views.DiagramView;


public class DebugStepIO {

	private static  IJavaThread debugThread;
	
	public static IJavaThread getDebugThread() {
		return debugThread;
	}

	public static void setDebugThread(IJavaThread DebugThread) {
		debugThread = DebugThread;
	}
	
	//Se encarga de ejecutar un stepinto del debugger de eclipse
	//ASTData step = ASTData.getRoot().findLine(update());
	//if (step.getElement() instanceof MethodInvocation) {
	//MAE VE PORQUE NO EN LUGAR DE BUSCAR LA LINEA DEL BREACKPOINT EN CODEPARSER LO HACEMOS DIRECTAMENTE EN EL DIAGRAMVIEW?
	//LE ACABO DE AGREGAR UN ATRIBUTO QUE ES LITERALMENTE lineNumber, entonces podriamos guardar eso ahi ya que a la hora de hacer un stepinto/over esto se deberia volver a instanciar, no?
	//Entonces no ocupariamos resume
	//fijate como es el stepover seria muy parecido
	//y lo ultimo seria que como arriba hay un if que valida si es una instancia de algun metodo, que si no lo es que se lo salte con stepover
	public static void stepInto() {}
	
	
	//Se encarga de ejecutar un stepover del debugger de eclipse
	//ENTONCES PORQUE NO USAS DEBUGTHREAD.STEPOVER y tambien llamas a diagramview.setlinenumber(current)  current seria la linea actual entonces deberia ir ligado con el update
	public static void stepOver() {}
	
	
	//Se encarga de conseguir la linea actual del debugger
	public static int update(){
		return 0;} //esto es para que compile BORRAR
	
	
	//Se encarga de activar la accion de resume del debugger de eclipse para que continue
	// ELIMINAR: public static void resume() {}
	
}
