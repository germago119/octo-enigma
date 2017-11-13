package org.tec.datos1.eclipse.grapher.assets;

import org.eclipse.swt.graphics.GC;


public interface Illustrator {
    public void sketch(GC gc, int line);

    public void fix(int x);
}
