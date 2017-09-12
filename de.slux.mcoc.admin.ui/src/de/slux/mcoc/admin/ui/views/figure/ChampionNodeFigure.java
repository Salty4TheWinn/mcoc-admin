/**
 * 
 */
package de.slux.mcoc.admin.ui.views.figure;

import java.io.File;
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
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import de.slux.mcoc.admin.data.model.ChampionManager;
import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.model.ChampionNode;
import de.slux.mcoc.admin.ui.model.PlayerChampion;

/**
 * @author Slux
 *
 */
public class ChampionNodeFigure extends ImageFigure
{
    private static final Image CHAMPION_NODE_IMAGE = McocAdminUiPlugin
            .getImageDescriptor("icons/generic/champion_node.png").createImage();
    private static final int WIDTH = 46;
    private static final int HEIGHT = 58;
    private static final Image EMPTY_IMAGE;
    private static final Color NODE_NUMBER_BG_COLOR = new Color(Display.getCurrent(), 255, 171, 15);
    private static final Color NODE_NUMBER_FG_COLOR = new Color(Display.getCurrent(), 0, 0, 0);
    private static final Font LABEL_FONT;

    static
    {
        Font currentFont = Display.getCurrent().getSystemFont();
        FontDescriptor boldDescriptor = FontDescriptor.createFrom(currentFont).setStyle(SWT.BOLD).setHeight(5);
        LABEL_FONT = boldDescriptor.createFont(Display.getDefault());

        // Create base image
        Image src = new Image(null, WIDTH, HEIGHT);
        ImageData imageData = src.getImageData();
        imageData.transparentPixel = imageData.getPixel(0, 0);
        src.dispose();
        EMPTY_IMAGE = new Image(null, imageData);
    }

    private ChampionNode node;

    /**
     * Ctor
     * 
     * @param node
     */
    public ChampionNodeFigure(ChampionNode node) {
        super(EMPTY_IMAGE);
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
        graphics.setAntialias(SWT.ON);
        graphics.setTextAntialias(SWT.ON);
        graphics.setAdvanced(true);
        graphics.setInterpolation(SWT.HIGH);

        super.paintFigure(graphics);

        // We add the portal number
        Rectangle area = getBounds().getShrinked(getInsets());

        // Background image every time. This will print it scaled correctly to
        // the zoom level
        graphics.drawImage(CHAMPION_NODE_IMAGE, CHAMPION_NODE_IMAGE.getBounds().x, CHAMPION_NODE_IMAGE.getBounds().y,
                CHAMPION_NODE_IMAGE.getBounds().width, CHAMPION_NODE_IMAGE.getBounds().height, area.x, area.y, WIDTH, HEIGHT);

        // Add the champion if any
        if (this.node.getPlayerChampion() != null)
        {
            PlayerChampion pc = this.node.getPlayerChampion();
            Image champImg = McocAdminUiPlugin.getImageDescriptor(ChampionManager.BUNDLE_ID,
                    ChampionManager.CHAMPIONS_IMG_DIR + File.separator + pc.getId() + ".png").createImage();

            graphics.drawImage(champImg, champImg.getBounds().x, champImg.getBounds().y, champImg.getBounds().width,
                    champImg.getBounds().height, area.x + 8, area.y + 5, 28, 28);

            champImg.dispose();
        }

        graphics.setBackgroundColor(NODE_NUMBER_BG_COLOR);
        graphics.setForegroundColor(NODE_NUMBER_FG_COLOR);

        graphics.setFont(LABEL_FONT);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int avgCharWidth = fontMetrics.getAverageCharWidth();
        int fontWidth = avgCharWidth * this.node.getNodeId().getNodeNumber().toString().length();

        graphics.fillText(this.node.getNodeId().getNodeNumber().toString(), area.x + area.width - fontWidth,
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
