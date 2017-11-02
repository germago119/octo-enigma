package org.tec.datos1.graph.eclipse.Greibus;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;


public class ProyectInfo extends AbstractHandler {
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	List<String> nombreMetodos = new ArrayList<String>();
	List<MethodDeclaration> nodos = new ArrayList<MethodDeclaration>();  
	List<String> nombreClases = new ArrayList<String>();
	public static final int METHOD_NAME = 0;
	public static final int METHOD_CLASS = 1;
	public static final int METHOD_NODE = 2;

 
    public ProyectInfo() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject[] projects = root.getProjects();
        for (IProject project : projects) {
            try {
                if (project.isNatureEnabled(JDT_NATURE)) {
                    analyseMethods(project);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    private void analyseMethods(IProject project) throws JavaModelException {
        IPackageFragment[] packages = JavaCore.create(project)
                .getPackageFragments();
        for (IPackageFragment mypackage : packages) {
            if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
                createAST(mypackage);
            }

        }
    }

    private void createAST(IPackageFragment mypackage)
            throws JavaModelException {
        for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
            // now create the AST for the ICompilationUnits
            CompilationUnit parse = parse(unit);
            MethodVisitor visitorMethod = new MethodVisitor();
            Visitor visitor = new Visitor();
            ClassVisitor classVisitor = new ClassVisitor();
            parse.accept(classVisitor);
            parse.accept(visitorMethod);
            for (MethodDeclaration method : visitorMethod.getMethods()) {
            	//System.out.println("Metodo " + method.getName() + method.parameters());
            	
            	this.nombreMetodos.add(method.getName() + obtenerParametros(method));
            	this.nombreClases.add(classVisitor.getClassName());
            	this.nodos.add(method);
            	
            	//method.accept(visitor);
            }
        }
            
    }
    
    private String obtenerParametros(MethodDeclaration method) {
    	String vacio = "(";
    	for (Object parameter : method.parameters()) {
    		vacio += parameter.toString().split(" ")[0];
    		vacio += ", ";
    		
    	}
    	vacio = vacio.substring(0, vacio.lastIndexOf(", "));
    	vacio += ")";
    	return vacio;
    	
    }
    
    public int getNumOfElements() {
		return this.nombreMetodos.size();
    	
    }
    
    public MethodDeclaration getMethod (String methodName, String methodClass) {
    	for (int i = 0; i < this.nombreMetodos.size(); i++) {
    		if (this.nombreMetodos.get(i).equals(methodName) && this.nombreClases.get(i).equals(methodClass)) {
    			return this.nodos.get(i);
    		}
    	}
    
		return null;
    	
    }
    


    /**
     * Reads a ICompilationUnit and creates the AST DOM for manipulating the
     * Java source file
     *
     * @param unit
     * @return
     */

    private static CompilationUnit parse(ICompilationUnit unit) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(unit);
        parser.setResolveBindings(true);
        return (CompilationUnit) parser.createAST(null); // parse
    }

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getMetodos () {
		// SIRVE
		 //for (int i = 0; i< this.nombreMetodos.size(); i++) 
         	//System.out.println(i + " LISTA POR FAVOR: " + this.nombreMetodos.get(i) + " CLASE: " + this.nombreClases.get(i));
	}
	
	public Object getMethodDescriptorByIndex(int index, int descriptorId) {
		if (index >= this.nombreMetodos.size()) {
			return null;
		}
		switch (descriptorId) {
		case METHOD_NAME:
			return this.nombreMetodos.get(index);
		case METHOD_CLASS:
			return this.nombreClases.get(index);
		case METHOD_NODE:
			return this.nodos.get(index);
		default:
			return null;
		}
		
	}
	
	public List<Object> getMethodDescriptorByClass(String className, int descriptorId) {
		List<Object> descriptorList = new ArrayList<Object>();
		if (! this.nombreClases.contains(className)) {
			return null;
		}
		switch (descriptorId) {
		case METHOD_NAME:
			for (int i = 0; i < this.nombreClases.size(); i ++) {
				if (this.nombreClases.get(i).equals(className)) {
					descriptorList.add(this.nombreMetodos.get(i));
				}
			}
			break;
		case METHOD_CLASS:
			return null;
		case METHOD_NODE:
			for (int i = 0; i < this.nombreClases.size(); i ++) {
				if (this.nombreClases.get(i).equals(className)) {
					descriptorList.add(this.nodos.get(i));
				}
			}
			break;
		default:
			return null;
		}
		return descriptorList;
		
	}
	
	
	
}
