package org.insightech.er.editor.controller.command.edit;

import java.util.ArrayList;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.insightech.er.editor.ERDiagramEditor;
import org.insightech.er.editor.controller.command.AbstractCommand;
import org.insightech.er.editor.model.ERDiagram;
import org.insightech.er.editor.model.diagram_contents.element.connection.Bendpoint;
import org.insightech.er.editor.model.diagram_contents.element.connection.ConnectionElement;
import org.insightech.er.editor.model.diagram_contents.element.node.Location;
import org.insightech.er.editor.model.diagram_contents.element.node.NodeElement;
import org.insightech.er.editor.model.diagram_contents.element.node.NodeSet;
import org.insightech.er.editor.model.diagram_contents.element.node.category.Category;
import org.insightech.er.editor.model.diagram_contents.element.node.table.ERTable;
import org.insightech.er.editor.model.diagram_contents.element.node.table.column.Column;
import org.insightech.er.editor.model.diagram_contents.element.node.table.column.NormalColumn;
import org.insightech.er.editor.model.diagram_contents.not_element.group.ColumnGroup;
import org.insightech.er.editor.model.diagram_contents.not_element.group.GroupSet;

public class PasteCommand extends AbstractCommand {

    private final ERDiagram diagram;

    private final GraphicalViewer viewer;

    // 粘贴目标列表
    private final NodeSet nodeElements;

    // 在粘贴时添加的组列的列表
    private final GroupSet columnGroups;

    private final Category category;

    /**
     * 创建粘贴命令。
     * 
     * @param editor
     * @param nodeElements
     */
    public PasteCommand(final ERDiagramEditor editor, final NodeSet nodeElements, final int x, final int y) {
        viewer = editor.getGraphicalViewer();
        diagram = (ERDiagram) viewer.getContents().getModel();
        category = diagram.getCurrentCategory();

        this.nodeElements = nodeElements;

        columnGroups = new GroupSet();

        final GroupSet groupSet = diagram.getDiagramContents().getGroups();

        // 重复处理粘贴目标
        for (final NodeElement nodeElement : nodeElements) {
            nodeElement.setLocation(new Location(nodeElement.getX() + x, nodeElement.getY() + y, nodeElement.getWidth(), nodeElement.getHeight()));

            for (final ConnectionElement connection : nodeElement.getIncomings()) {
                for (final Bendpoint bendpoint : connection.getBendpoints()) {
                    bendpoint.transform(x, y);
                }
            }

            // 重复处理粘贴目标
            if (nodeElement instanceof ERTable) {

                final ERTable table = (ERTable) nodeElement;

                // 迭代列
                for (final Column column : new ArrayList<Column>(table.getColumns())) {

                    // 当列是组列时
                    if (column instanceof ColumnGroup) {
                        final ColumnGroup group = (ColumnGroup) column;

                        // 当它不是这个图的组列时
                        if (!groupSet.contains(group)) {
                            // 将其添加到目标组列。
                            columnGroups.add(group);

                        } else {
                            if (groupSet.findSame(group) == null) {
                                final ColumnGroup equalColumnGroup = groupSet.find(group);

                                table.replaceColumnGroup(group, equalColumnGroup);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 执行粘贴过程
     */
    @Override
    protected void doExecute() {
        final GroupSet columnGroupSet = diagram.getDiagramContents().getGroups();

        // 在图中添加一个节点。
        for (final NodeElement nodeElement : nodeElements) {
            if (category != null) {
                category.add(nodeElement);
            }
            diagram.addContent(nodeElement);
        }

        // 添加组列。
        for (final ColumnGroup columnGroup : columnGroups) {
            columnGroupSet.add(columnGroup);

            for (final NormalColumn normalColumn : columnGroup.getColumns()) {
                diagram.getDiagramContents().getDictionary().add(normalColumn);
            }
        }

        diagram.refreshChildren();

        // 将粘贴的表格置于选定状态。
        setFocus();
    }

    /**
     * 恢复粘贴处理
     */
    @Override
    protected void doUndo() {
        final GroupSet columnGroupSet = diagram.getDiagramContents().getGroups();

        // 从图中删除节点。
        for (final NodeElement nodeElement : nodeElements) {
            if (category != null) {
                category.remove(nodeElement);
            }
            diagram.removeContent(nodeElement);
        }

        // 删除组列。
        for (final ColumnGroup columnGroup : columnGroups) {
            columnGroupSet.remove(columnGroup);

            for (final NormalColumn normalColumn : columnGroup.getColumns()) {
                diagram.getDiagramContents().getDictionary().remove(normalColumn);
            }
        }

        diagram.refreshChildren();
    }

    /**
     * 将粘贴的表格置于选定状态。
     */
    private void setFocus() {
        // 将粘贴的表格置于选定状态。
        for (final NodeElement nodeElement : nodeElements) {
            final EditPart editPart = (EditPart) viewer.getEditPartRegistry().get(nodeElement);

            viewer.getSelectionManager().appendSelection(editPart);
        }
    }
}
