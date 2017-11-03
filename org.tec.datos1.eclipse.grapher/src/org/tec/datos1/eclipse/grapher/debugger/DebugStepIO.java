package org.tec.datos1.eclipse.grapher.debugger;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.tec.datos1.eclipse.grapher.data.*;
import org.tec.datos1.eclipse.grapher.CodeParser;

public class DebugStepIO {

	private static  IJavaThread debugThread;
	
	public static IJavaThread getDebugThread() {
		return debugThread;
	}

	public static void setDebugThread(IJavaThread DebugThread) {
		debugThread = DebugThread;
	}
	
	//Se encarga de ejecutar un stepinto del debugger de eclipse
	//debe utilizar ASTData.getroot().findLine(update())
	//debe utilizar codeparser.executesingular
	//debe utilizar ASTData.getmethod
	public static void stepInto() {}
	
	
	//Se encarga de ejecutar un stepover del debugger de eclipse
	//debe utilizar ASTData.getRoot().findLine(currentLine)
	public static void stepOver() {}
	
	
	//Se encarga de conseguir la linea actual del debugger
	public static int update(){
		return 0;} //esto es para que compile BORRAR
	
	
	//Se encarga de activar la accion de resume del debugger de eclipse para que continue
	public static void resume() {}
	
}
