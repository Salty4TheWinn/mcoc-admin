/**
 * 
 */
package de.slux.mcoc.admin.ui.views.figure;

import java.util.Objects;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.swt.graphics.Image;

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

    private EmptyNode node;

    /**
     * Ctor
     * 
     * @param node
     */
    public EmptyNodeFigure(EmptyNode node) {
        super(EMPTY_NODE_IMAGE);
        this.node = Objects.requireNonNull(node);
    }

}
