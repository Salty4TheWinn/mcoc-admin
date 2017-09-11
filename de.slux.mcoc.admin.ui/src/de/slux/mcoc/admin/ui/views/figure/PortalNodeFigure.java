/**
 * 
 */
package de.slux.mcoc.admin.ui.views.figure;

import java.util.Objects;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.model.PortalNode;

/**
 * @author Slux
 *
 */
public class PortalNodeFigure extends ImageFigure
{
    private static final Image PORTAL_NODE_IMAGE = McocAdminUiPlugin.getImageDescriptor("icons/generic/portal_node.png")
            .createImage();
    private static final Color PORTAL_NUMBER_BG_COLOR = new Color(Display.getCurrent(), 0, 150, 181);
    private static final Color PORTAL_NUMBER_FG_COLOR = new Color(Display.getCurrent(), 255, 255, 255);

    private PortalNode node;

    /**
     * Ctor
     * 
     * @param node
     */
    public PortalNodeFigure(PortalNode node) {
        super(PORTAL_NODE_IMAGE);
        this.node = Objects.requireNonNull(node);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.draw2d.ImageFigure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    @Override
    protected void paintFigure(Graphics graphics)
    {
        super.paintFigure(graphics);

        // We add the portal number
        Rectangle area = getBounds().getShrinked(getInsets());

        graphics.setBackgroundColor(PORTAL_NUMBER_BG_COLOR);
        graphics.setForegroundColor(PORTAL_NUMBER_FG_COLOR);

        Font currentFont = graphics.getFont();
        FontDescriptor boldDescriptor = FontDescriptor.createFrom(currentFont).setStyle(SWT.BOLD).setHeight(6);
        Font numberFont = boldDescriptor.createFont(Display.getDefault());
        graphics.setFont(numberFont);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int avgCharWidth = fontMetrics.getAverageCharWidth();
        int fontWidth = avgCharWidth * this.node.getNodeId().getNodeNumber().toString().length();

        graphics.fillText(this.node.getNodeId().getNodeNumber().toString(), area.x + area.width - fontWidth, area.y);

    }

}
