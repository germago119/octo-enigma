package org.tec.datos1.eclipse.grapher.debugger;


import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;
import org.tec.datos1.eclipse.grapher.Greibus.ProyectInfo;
import org.tec.datos1.eclipse.grapher.Greibus.Visitor;
import org.tec.datos1.eclipse.grapher.views.*;

public class DebugListener implements IJavaBreakpointListener {@Override
	public void addingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {}

	@Override
	public void breakpointHasCompilationErrors(IJavaLineBreakpoint arg0, Message[] arg1) {}
	
	@Override
	public void breakpointHasRuntimeException(IJavaLineBreakpoint arg0, DebugException arg1) {}
	
	/**
	 * Este metodo se activa cuando se encunetra un breakpoint a la hora de depurar y mantiene una referencia del hilo
	 */
	
	//Unido con DiagramView
	@Override
	public int breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
		String methodName = this.getMethodName(breakpoint);
		String methodClass = this.getClassName(breakpoint);
		ProyectInfo myInfo = new ProyectInfo();
		MethodDeclaration nodo = myInfo.getMethod(methodName, methodClass);
		CompilationUnit unit = myInfo.getCompilation(methodClass);
		Visitor visitor = new Visitor(unit);
		if (nodo != null) {
			System.out.println("Nombre metodo: " + methodName);
			nodo.accept(visitor);
		} else {
			System.out.println("HAY UN ERROR :V");
		}
				
		DebugStepIO.setDebugThread(thread);
		
		
		ILineBreakpoint lineBreak = (ILineBreakpoint)breakpoint;
		int lineNumber = 0; 
				try {
					lineNumber = lineBreak.getLineNumber();
		 		}catch(CoreException e) {}
				DiagramView.setLineNumber(lineNumber);
				System.out.println(DiagramView.getLineNumber());
				return lineNumber;
	}
	
	public String getClassName(IJavaBreakpoint breakpoint) {
		String resource = breakpoint.getMarker().getResource().toString();
		resource = resource.substring(resource.lastIndexOf("/") + 1, resource.length());
		resource = resource.substring(0, resource.indexOf("."));
		return resource;
	}
	
	public String getMethodName (IJavaBreakpoint breakpoint) {
		String variable = null;
		String metodo = null;
		try {
			variable = (String) breakpoint.getMarker().getAttribute(IMarker.MESSAGE);
			int index = variable.indexOf("] - ");
			if (index< variable.length()) {
				String split[] = variable.split("] - ");
				metodo = split[1];
			} 
			
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			metodo = "NO se puede obtener metodo";
			
		}
		return metodo;
	}

	@Override
	public void breakpointInstalled(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {}

	@Override
	public void breakpointRemoved(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {}

	@Override
	public int installingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1, IJavaType arg2) {return 0;}

}
