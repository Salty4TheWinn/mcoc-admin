/**
 * 
 */
package de.slux.mcoc.admin.ui.views.figure;

import java.util.Objects;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
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
import de.slux.mcoc.admin.ui.model.ChampionNode;
import de.slux.mcoc.admin.ui.model.PortalNode;

/**
 * @author Slux
 *
 */
public class ChampionNodeFigure extends ImageFigure
{
    private static final Image CHAMPION_NODE_IMAGE = McocAdminUiPlugin
            .getImageDescriptor("icons/generic/champion_node.png").createImage();
    private static final Color PORTAL_NUMBER_BG_COLOR = new Color(Display.getCurrent(), 255, 171, 15);
    private static final Color PORTAL_NUMBER_FG_COLOR = new Color(Display.getCurrent(), 0, 0, 0);
    private static final Font LABEL_FONT;

    static
    {
        Font currentFont = Display.getCurrent().getSystemFont();
        FontDescriptor boldDescriptor = FontDescriptor.createFrom(currentFont).setStyle(SWT.BOLD).setHeight(5);
        LABEL_FONT = boldDescriptor.createFont(Display.getDefault());
    }

    private ChampionNode node;

    /**
     * Ctor
     * 
     * @param node
     */
    public ChampionNodeFigure(ChampionNode node) {
        super(CHAMPION_NODE_IMAGE);
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

        graphics.setFont(LABEL_FONT);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int avgCharWidth = fontMetrics.getAverageCharWidth();
        int fontWidth = avgCharWidth * this.node.getNodeId().getNodeNumber().toString().length();

        graphics.fillText(this.node.getNodeId().getNodeNumber().toString(), area.x + (area.width / 2) - (fontWidth / 2),
                area.y + area.height - fontMetrics.getHeight());

    }

    /**
     * @return the node
     */
    public ChampionNode getNode()
    {
        return node;
    }

}
