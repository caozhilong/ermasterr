package org.insightech.er.editor.view.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
/**
 * 轻量级图形组件系统Draw2d
 * https://wenku.baidu.com/view/21529e97dd88d0d233d46ab8.html
 * @author caozhilong1
 *
 */
public class CategoryFigure extends RectangleFigure {

    private final Label label;

    public CategoryFigure(final String name) {
        setOpaque(true);

        final ToolbarLayout layout = new ToolbarLayout();
        setLayoutManager(layout);

        label = new Label();
        label.setText(name);
        label.setBorder(new MarginBorder(7));
        this.add(label);
    }

    public void setName(final String name) {
        label.setText(name);
    }

    @Override
    protected void fillShape(final Graphics graphics) {
        graphics.setAlpha(100);
        super.fillShape(graphics);
    }

}
