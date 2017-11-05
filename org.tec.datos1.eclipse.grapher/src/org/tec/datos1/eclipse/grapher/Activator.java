package org.tec.datos1.eclipse.grapher;

import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.tec.datos1.eclipse.grapher.debugger.DebugListener;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        JDIDebugModel.addJavaBreakpointListener(new DebugListener());
        Activator.context = bundleContext;

    }

    /*
     * (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

}
