/**
 * 
 */
package de.slux.mcoc.admin.ui.views.figure;

import java.util.Objects;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.model.EmptyNode;

/**
 * @author Slux
 *
 */
public class EmptyNodeFigure extends ImageFigure
{
    private static final Image EMPTY_NODE_IMAGE = McocAdminUiPlugin.getImageDescriptor("icons/generic/empty_node.png")
            .createImage();
    private static final int WIDTH = 32;
    private static final int HEIGHT = 30;
    private static final Image EMPTY_IMAGE;

    private EmptyNode node;

    static
    {
        // Create base image
        Image src = new Image(null, WIDTH, HEIGHT);
        ImageData imageData = src.getImageData();
        imageData.transparentPixel = imageData.getPixel(0, 0);
        src.dispose();
        EMPTY_IMAGE = new Image(null, imageData);
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

        Rectangle area = getBounds().getShrinked(getInsets());

        // Background image every time. This will print it scaled correctly to
        // the zoom level
        graphics.drawImage(EMPTY_NODE_IMAGE, EMPTY_NODE_IMAGE.getBounds().x, EMPTY_NODE_IMAGE.getBounds().y,
                EMPTY_NODE_IMAGE.getBounds().width, EMPTY_NODE_IMAGE.getBounds().height, area.x, area.y, WIDTH, HEIGHT);

    }

    /**
     * Ctor
     * 
     * @param node
     */
    public EmptyNodeFigure(EmptyNode node) {
        super(EMPTY_IMAGE);
        this.setNode(Objects.requireNonNull(node));
    }

    /**
     * @return the node
     */
    public EmptyNode getNode()
    {
        return node;
    }

    /**
     * @param node
     *            the node to set
     */
    public void setNode(EmptyNode node)
    {
        this.node = node;
    }

}
