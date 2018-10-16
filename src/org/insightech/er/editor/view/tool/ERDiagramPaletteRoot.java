package org.insightech.er.editor.view.tool;

import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.insightech.er.ERDiagramActivator;
import org.insightech.er.ImageKey;
import org.insightech.er.ResourceString;
import org.insightech.er.editor.model.diagram_contents.element.connection.CommentConnection;
import org.insightech.er.editor.model.diagram_contents.element.connection.RelatedTable;
import org.insightech.er.editor.model.diagram_contents.element.connection.Relation;
import org.insightech.er.editor.model.diagram_contents.element.connection.RelationByExistingColumns;
import org.insightech.er.editor.model.diagram_contents.element.connection.SelfRelation;
import org.insightech.er.editor.model.diagram_contents.element.node.category.Category;
import org.insightech.er.editor.model.diagram_contents.element.node.note.Note;
import org.insightech.er.editor.model.diagram_contents.element.node.table.ERTable;
import org.insightech.er.editor.model.diagram_contents.element.node.view.View;
/**
 * ER图调色板
 * ERM编辑器左侧选项卡
 * @author caozhilong1
 *
 */
public class ERDiagramPaletteRoot extends PaletteRoot {

    public ERDiagramPaletteRoot() {
        final PaletteGroup group = new PaletteGroup("");

        // when tooltip equals to label, tooltip is not displayed.
        final PanningSelectionToolEntry selectionTool = new PanningSelectionToolEntry(ResourceString.getResourceString("label.select"));
        selectionTool.setToolClass(MovablePanningSelectionTool.class);
        selectionTool.setLargeIcon(ERDiagramActivator.getImageDescriptor(ImageKey.ARROW));
        selectionTool.setSmallIcon(ERDiagramActivator.getImageDescriptor(ImageKey.ARROW));

        group.add(selectionTool);
        // group.add(new MarqueeToolEntry());

        // 表Table选项
        group.add(new CreationToolEntry(ResourceString.getResourceString("label.table"), ResourceString.getResourceString("label.create.table"), new SimpleFactory(ERTable.class), ERDiagramActivator.getImageDescriptor(ImageKey.TABLE_NEW), ERDiagramActivator.getImageDescriptor(ImageKey.TABLE_NEW)));

        // 视图View选项
        group.add(new CreationToolEntry(ResourceString.getResourceString("label.view"), ResourceString.getResourceString("label.create.view"), new SimpleFactory(View.class), ERDiagramActivator.getImageDescriptor(ImageKey.VIEW), ERDiagramActivator.getImageDescriptor(ImageKey.VIEW)));

        // 一对多关联关系
        final ConnectionCreationToolEntry oneToManyTool = new ConnectionCreationToolEntry(ResourceString.getResourceString("label.relation.one.to.many"), ResourceString.getResourceString("label.create.relation.one.to.many"), new SimpleFactory(Relation.class), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_1_N), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_1_N));
        oneToManyTool.setToolClass(RelationCreationTool.class);
        group.add(oneToManyTool);

        // 根据现有字段进行关联关系
        final ConnectionCreationToolEntry relationByExistingTool = new ConnectionCreationToolEntry(ResourceString.getResourceString("label.relation.by.existing.columns"), ResourceString.getResourceString("label.create.relation.by.existing.columns"), new SimpleFactory(RelationByExistingColumns.class), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_1_N), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_1_N));
        relationByExistingTool.setToolClass(RelationByExistingColumnsCreationTool.class);
        group.add(relationByExistingTool);

        // 多对多关联关系
        final ConnectionCreationToolEntry manyToManyTool = new ConnectionCreationToolEntry(ResourceString.getResourceString("label.relation.many.to.many"), ResourceString.getResourceString("label.create.relation.many.to.many"), new SimpleFactory(RelatedTable.class), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_N_N), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_N_N));
        manyToManyTool.setToolClass(RelatedTableCreationTool.class);
        group.add(manyToManyTool);

        // 自相关关联关系
        final ConnectionCreationToolEntry selfRelationTool = new ConnectionCreationToolEntry(ResourceString.getResourceString("label.relation.self"), ResourceString.getResourceString("label.create.relation.self"), new SimpleFactory(SelfRelation.class), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_SELF), ERDiagramActivator.getImageDescriptor(ImageKey.RELATION_SELF));
        selfRelationTool.setToolClass(SelfRelationCreationTool.class);
        group.add(selfRelationTool);
        
        // 菜单分隔符
        group.add(new PaletteSeparator());

        // 增加note
        final CreationToolEntry noteTool = new CreationToolEntry(ResourceString.getResourceString("label.note"), ResourceString.getResourceString("label.create.note"), new SimpleFactory(Note.class), ERDiagramActivator.getImageDescriptor(ImageKey.NOTE), ERDiagramActivator.getImageDescriptor(ImageKey.NOTE));
        group.add(noteTool);

        // 增加note和实体的关联
        final ConnectionCreationToolEntry commentConnectionTool = new ConnectionCreationToolEntry(ResourceString.getResourceString("label.relation.note"), ResourceString.getResourceString("label.create.relation.note"), new SimpleFactory(CommentConnection.class), ERDiagramActivator.getImageDescriptor(ImageKey.COMMENT_CONNECTION), ERDiagramActivator.getImageDescriptor(ImageKey.COMMENT_CONNECTION));
        group.add(commentConnectionTool);

        // 菜单分隔符
        group.add(new PaletteSeparator());

        // 分组操作
        group.add(new CreationToolEntry(ResourceString.getResourceString("label.category"), ResourceString.getResourceString("label.create.category"), new SimpleFactory(Category.class), ERDiagramActivator.getImageDescriptor(ImageKey.CATEGORY), ERDiagramActivator.getImageDescriptor(ImageKey.CATEGORY)));

        group.add(new PaletteSeparator());
        
        // 插入图片工具
        group.add(new InsertImageTool());

        this.add(group);

        setDefaultEntry(selectionTool);
    }

}
