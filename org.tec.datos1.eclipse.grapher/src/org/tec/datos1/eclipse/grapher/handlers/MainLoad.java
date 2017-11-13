package org.tec.datos1.eclipse.grapher.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;
import org.tec.datos1.eclipse.grapher.CodeParser;
import org.tec.datos1.eclipse.grapher.Roger.DiagramView2;
import org.tec.datos1.eclipse.grapher.Roger.Methods;
import org.tec.datos1.eclipse.grapher.data.ASTData;



public class MainLoad {

	@Execute
	public void execute(Shell shell) {
		try {
			CodeParser.execute();
			Methods.load();
			List<String> methods = ASTData.getMethods();
			String[] array = new String[methods.size()];
			int cont = 0;
			for(String method : methods) {
				array[cont] = method;
				cont++;
			}
			DiagramView2.setMethods(array);
			
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
}

