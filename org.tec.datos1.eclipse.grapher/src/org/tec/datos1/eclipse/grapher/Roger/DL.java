package org.tec.datos1.eclipse.grapher.Roger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;
import org.tec.datos1.eclipse.grapher.debugger.DebugStepIO;

public class DL implements IJavaBreakpointListener{

	@Override
	public void addingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {}

	@Override
	public void breakpointHasCompilationErrors(IJavaLineBreakpoint arg0, Message[] arg1) {}
	
	@Override
	public void breakpointHasRuntimeException(IJavaLineBreakpoint arg0, DebugException arg1) {}
	
	@Override
	public int breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
		
		DebugStepIO.setDebugThread(thread);
		
		ILineBreakpoint lineBreak = (ILineBreakpoint)breakpoint;
		int lineNumber = 0; 
		try {
			lineNumber = lineBreak.getLineNumber();
		}catch(CoreException e) {}
		
		return lineNumber;
	}

	@Override
	public void breakpointInstalled(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {}

	@Override
	public void breakpointRemoved(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {}

	@Override
	public int installingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1, IJavaType arg2) {return 0;}

}
