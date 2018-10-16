package org.insightech.er.editor.view;

import org.eclipse.core.resources.IMarker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.ide.IGotoMarker;
import org.insightech.er.editor.ERDiagramMultiPageEditor;

/**
 * 编辑器的适配器接口，允许编辑器显示给定标记的位置。
 * @author caozhilong1
 *
 */
public class ERDiagramGotoMarker implements IGotoMarker {

    private final ERDiagramMultiPageEditor editor;

    public ERDiagramGotoMarker(final ERDiagramMultiPageEditor editor) {
        this.editor = editor;
    }

    @Override
    public void gotoMarker(final IMarker marker) {
        focus(editor.getMarkedObject(marker));
    }

    private void focus(final Object object) {
        final GraphicalViewer viewer = editor.getActiveEditor().getGraphicalViewer();
        final EditPart editPart = (EditPart) viewer.getEditPartRegistry().get(object);

        if (editPart != null) {
            viewer.select(editPart);
            viewer.reveal(editPart);
        }
    }
}
