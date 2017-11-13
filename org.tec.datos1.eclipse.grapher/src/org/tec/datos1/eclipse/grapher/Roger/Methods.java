package org.tec.datos1.eclipse.grapher.Roger;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.jdt.core.dom.ASTVisitor;

public class Methods extends ASTVisitor{
	static List<ICompilationUnit> classes = new ArrayList<ICompilationUnit>();
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";

	
	
	public static ICompilationUnit findClass(String clase) {
		clase = "src." + clase + ".java";
		for(ICompilationUnit current : classes) {
			
			String name = current.getResource().getProjectRelativePath().toString().replace("/", ".");
			if (clase.equals(name)) {
				return current;
			}
		}
		return null;
	}
	
	
	public static void load() {
		if (classes.size() != 0) return;
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

    private static void analyseMethods(IProject project) throws JavaModelException {
        IPackageFragment[] packages = JavaCore.create(project)
                .getPackageFragments();
        for (IPackageFragment mypackage : packages) {
            if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
                createAST(mypackage);
            }

        }
    }

    private static void createAST(IPackageFragment mypackage)
            throws JavaModelException {
        for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
        	classes.add(unit);
        }
    }
}