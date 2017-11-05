package org.tec.datos1.eclipse.grapher;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.tec.datos1.eclipse.grapher.data.ASTData;
import org.tec.datos1.eclipse.grapher.handlers.*;

public class CodeParser {

    //parsea el codigo de la clase abierta en momento que se llama el metodo con un IC UNIT como parametro
    public static void executeSingular(ICompilationUnit unit) throws ExecutionException {
        if (ASTData.getRoot() != null) {
            ASTData.getRoot().deleteChildren();
        }
        try {
            createAST(unit);
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera el arbol AST
     *
     * @param IcUnit Clase que se va a analizar
     * @throws JavaModelException
     */
    private static void createAST(ICompilationUnit icUnit) throws JavaModelException {

        CompilationUnit parse = parse(icUnit);
        ASTData.setCompilationUnit(parse);
        MethodVisitor visitor = new MethodVisitor();
        parse.accept(visitor);

    }

    /**
     * Reads a ICompilationUnit and creates the AST DOM for manipulating the
     * Java source file
     *
     * @param unit
     * @return
     */
    public static CompilationUnit parse(ICompilationUnit unit) {

        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(unit);
        parser.setResolveBindings(true);

        return (CompilationUnit) parser.createAST(null);
    }

    ////parsea el codigo de la clase abierta en momento que se llama el metodo sin necesitar un IC UNIT

    /**
     * Se encarga de parsear el codigo de la
     * clase abierta en el momento en que se llama
     *
     * @throws ExecutionException
     */
    public static void execute() throws ExecutionException {
        if (ASTData.getRoot() != null) {
            ASTData.getRoot().deleteChildren();
        }
        IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
        ICompilationUnit icUnit = null;

        try {
            IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
            icUnit = (ICompilationUnit) JavaCore.create(file);
        } catch (Exception e) {
            System.err.println("Require an open class");
        }
        try {
            createAST(icUnit);

        } catch (JavaModelException exeption) {
        }

    }

}
