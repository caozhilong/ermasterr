package org.insightech.er.preference.page.root;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.insightech.er.ResourceString;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.model.diagram_contents.not_element.group.CopyGroup;
import org.insightech.er.editor.model.diagram_contents.not_element.group.GlobalGroupSet;
import org.insightech.er.editor.model.diagram_contents.not_element.group.GroupSet;
import org.insightech.er.editor.view.dialog.group.GroupManageDialog;

public class RootPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    @Override
    protected Control createContents(final Composite parent) {
        noDefaultAndApplyButton();

        final Composite composite = new Composite(parent, SWT.NONE);

        composite.setLayout(new GridLayout());

        initialize(composite);

        return composite;
    }

    private void initialize(final Composite parent) {
        final Button button = new Button(parent, SWT.NONE);
        button.setText(ResourceString.getResourceString("action.title.manage.global.group"));
        button.addSelectionListener(new SelectionAdapter() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void widgetSelected(final SelectionEvent e) {
                final GroupSet columnGroups = GlobalGroupSet.load();
                final ERDiagram diagram = new ERDiagram(columnGroups.getDatabase());

                final GroupManageDialog dialog = new GroupManageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), columnGroups, diagram, true, -1);

                if (dialog.open() == IDialogConstants.OK_ID) {
                    final List<CopyGroup> newColumnGroups = dialog.getCopyColumnGroups();

                    columnGroups.clear();

                    for (final CopyGroup copyColumnGroup : newColumnGroups) {
                        columnGroups.add(copyColumnGroup.restructure(null));
                    }

                    GlobalGroupSet.save(columnGroups);
                }
            }
        });
    }

    @Override
    public void init(final IWorkbench workbench) {}

}