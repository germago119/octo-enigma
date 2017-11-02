package org.tec.datos1.graph.eclipse.Greibus;

import org.eclipse.core.resources.IMarker;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.debug.core.DebugException;

import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.debug.core.*;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;


public class DebugBreakpointListener implements IJavaBreakpointListener {
	
	@Override
	public void breakpointInstalled(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint) {
		
	}
	
	@Override
	public int breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
		String methodName = this.getMethodName(breakpoint);
		String methodClass = this.getClassName(breakpoint);
		ProyectInfo myInfo = new ProyectInfo();
		MethodDeclaration nodo = myInfo.getMethod(methodName, methodClass);
		Visitor visitor = new Visitor();
		if (nodo != null) {
			System.out.println("Nombre metodo: " + methodName);
			nodo.accept(visitor);
		} else {
			System.out.println("HAY UN ERROR :V");
		}
			
			
		return 0;
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
//				variable.substring(index + 4);
				String split[] = variable.split("] - ");
//				System.out.println("SPLIT POSICION 1 " + split[1]);
				//index = split[1].indexOf('(');
				metodo = split[1]/*.substring(0, index)*/;
				
//				System.out.println("getMarkermarker: " + metodo );
			} else {
//				System.out.println("MAYOR: " + variable.length() + " INDEX: " + index);
			}
			
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			metodo = "NO se puede obtener metodo";
			
		}
		return metodo;
	}
	
	@Override
	public void breakpointHasRuntimeException(IJavaLineBreakpoint breakpoint,
			DebugException exception) {
		
	}
	
	@Override
	public void breakpointHasCompilationErrors(IJavaLineBreakpoint breakpoint,
			Message[] errors) {
		
	}
	
	@Override
	public void addingBreakpoint(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint) {
		
	}
	
	@Override
	public void breakpointRemoved(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint) {
		
	}
	
	@Override
	public int installingBreakpoint(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint, IJavaType type) {
				return 0;
		
	}

}
