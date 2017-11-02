package org.tec.datos1.graph.eclipse.handlers;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.e4.core.di.annotations.Execute;

public class StepOverH {

	@Execute
	public void execute(Shell shell) {
		System.out.println("Stepped in");
	}

}
