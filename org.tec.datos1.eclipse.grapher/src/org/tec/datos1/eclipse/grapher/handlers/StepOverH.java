package org.tec.datos1.eclipse.grapher.handlers;

import org.eclipse.swt.widgets.Shell;
import org.tec.datos1.eclipse.grapher.debugger.DebugStepIO;
import org.eclipse.e4.core.di.annotations.Execute;

public class StepOverH {

	@Execute
	public void execute(Shell shell) {
		if (DebugStepIO.getDebugThread() != null)
			DebugStepIO.stepOver();
	}

}
