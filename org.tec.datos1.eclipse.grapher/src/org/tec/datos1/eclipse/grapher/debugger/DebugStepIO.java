package org.tec.datos1.eclipse.grapher.debugger;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.tec.datos1.eclipse.grapher.CodeParser;
import org.tec.datos1.eclipse.grapher.Roger.DiagramView2;
import org.tec.datos1.eclipse.grapher.Roger.Methods;
import org.tec.datos1.eclipse.grapher.data.ASTData;



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
	//MAE VE PORQUE NO EN LUGAR DE BUSCAR LA LINEA DEL BREACKPOINT EN CODEPARSER LO HACEMOS DIRECTAMENTE EN EL DiagramView2?
	//LE ACABO DE AGREGAR UN ATRIBUTO QUE ES LITERALMENTE lineNumber, entonces podriamos guardar eso ahi ya que a la hora de hacer un stepinto/over esto se deberia volver a instanciar, no?
	//Entonces no ocupariamos resume
	//fijate como es el stepover seria muy parecido
	//y lo ultimo seria que como arriba hay un if que valida si es una instancia de algun metodo, que si no lo es que se lo salte con stepover
	public static void stepInto() {
		try {
			ASTData step = ASTData.getRoot().findLine(update());
			if (step.getElement() instanceof MethodInvocation) {
				debugThread.stepInto();
				int currentLine = update();
				DiagramView2.setLineNumber(currentLine);
				try {
					
					MethodInvocation methodInvocation = (MethodInvocation) step.getElement();
					ICompilationUnit newUnit = Methods.findClass(methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName());
					
					
					if (!ASTData.getCompUnit().getJavaElement().getElementName()
							.equals(newUnit.getElementName())){
						CodeParser.executeSingular(newUnit);
					}
					
					List<String> methods = ASTData.getMethods();
					String[] array = new String[methods.size()];
					int cont = 0;
					for(String method : methods) {
						array[cont] = method;
						cont++;
					}
					
					DiagramView2.setMethods(array);
					DiagramView2.selectMethod(methodInvocation.getName().toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				stepOver();
			}
			
		} catch (Exception e) {
			stepOver();
		}
		
	}
	
	
	//Se encarga de ejecutar un stepover del debugger de eclipse
	//ENTONCES PORQUE NO USAS DEBUGTHREAD.STEPOVER y tambien llamas a DiagramView2.setlinenumber(current)  current seria la linea actual entonces deberia ir ligado con el update
	public static void stepOver() {
		try {
			debugThread.stepOver();
			
			int currentLine = update();
			DiagramView2.setLineNumber(currentLine);
			if ( !DiagramView2.getMethodSelector().getText().equalsIgnoreCase(
					((MethodDeclaration)ASTData.getMethodByLine(currentLine).getElement())  //Null pointer exception al hacer step into o over a println
					.getName().toString())) {
				DiagramView2.Select();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//Se encarga de conseguir la linea actual del debugger
	public static int update(){
		try {
    		
			IStackFrame Frame = null;
			while (Frame == null) {
				Frame = debugThread.getTopStackFrame();
			}
			IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
			IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
    			ICompilationUnit IcUnit = (ICompilationUnit) JavaCore.create(file);
    		
			if(!IcUnit.getElementName().equalsIgnoreCase(ASTData.getCompUnit().getJavaElement().getElementName())) {
				CodeParser.executeSingular(IcUnit);
			}
			return Frame.getLineNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
