package org.tec.datos1.graph.eclipse.Greibus;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.JDIDebugModel;

public class Caso2Handler extends AbstractHandler {

    private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	JDIDebugModel.addJavaBreakpointListener(new DebugBreakpointListener());

    	return null;
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
        JDIDebugModel.addJavaBreakpointListener(new DebugBreakpointListener());
        
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
}