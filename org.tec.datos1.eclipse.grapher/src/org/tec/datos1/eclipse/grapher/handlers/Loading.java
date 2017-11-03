package org.tec.datos1.eclipse.grapher.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;
import org.tec.datos1.eclipse.grapher.CodeParser;
import org.tec.datos1.eclipse.grapher.data.ASTData;
import org.tec.datos1.eclipse.grapher.views.DiagramView;


public class Loading {

	@Execute
	public void execute(Shell shell) {
		try {
			CodeParser.execute();
			List<String> methods = ASTData.getMethods();
			String[] array = new String[methods.size()];
			int cont = 0;
			for(String method : methods) {
				array[cont] = method;
				cont++;
			}
			DiagramView.setMethods(array);
			
		
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
}
