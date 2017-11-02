package org.tec.datos1.eclipse.grapher.handlers;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.e4.core.di.annotations.Execute;

public class StepInH {

	@Execute
	public void execute(Shell shell) {
		System.out.println("Stepped in");
	}
	
}
