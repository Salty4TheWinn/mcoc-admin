package de.slux.mcoc.admin.ui.views.provider;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;

import de.slux.mcoc.admin.ui.McocAdminUiPlugin;

/**
 * @author slux
 *
 */
public class MapGraphLabelProvider extends LabelProvider implements IEntityStyleProvider
{

    private static final Image INFO_ICON = McocAdminUiPlugin.getImageDescriptor("icons/info.png").createImage();

    private Color agentColor, systemColor;
    private Color agentWarningBorderColor, agentErrorBorderColor, agentNormalBorderColor;
    private Color highlightColor;
    private Color rootColor;
    private Color agentColorInactive;

    /**
     * Constructor
     */
    public MapGraphLabelProvider() {
        this.agentColor = new Color(Display.getCurrent(), 227, 246, 255);
        this.agentColorInactive = new Color(Display.getCurrent(), 240, 240, 240);
        this.systemColor = new Color(Display.getCurrent(), 255, 244, 227);
        this.agentWarningBorderColor = new Color(Display.getCurrent(), 250, 246, 0);
        this.agentErrorBorderColor = new Color(Display.getCurrent(), 214, 0, 0);
        this.agentNormalBorderColor = new Color(Display.getCurrent(), 0, 0, 0);
        this.highlightColor = new Color(Display.getCurrent(), 255, 221, 0);
        this.rootColor = new Color(Display.getCurrent(), 182, 250, 187);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
     */
    @Override
    public void dispose()
    {
        this.agentColor.dispose();
        this.systemColor.dispose();
        this.agentErrorBorderColor.dispose();
        this.agentWarningBorderColor.dispose();
        this.agentNormalBorderColor.dispose();
        this.highlightColor.dispose();
        this.rootColor.dispose();
        this.agentColorInactive.dispose();
        INFO_ICON.dispose();

        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element)
    {
        return super.getImage(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element)
    {

        if (element instanceof EntityConnectionData)
            return ""; // No label on the connection

        /*
        if (element instanceof AgentModelItem)
            return ((AgentModelItem) element).getName();

        if (element instanceof SubSystemModelItem)
            return ((SubSystemModelItem) element).getName();

        if (element instanceof RootAgentElement)
            return ((RootAgentElement) element).getName();
        */
        return "FOO.BAR"; // FIXME
    }

    @Override
    public Color getNodeHighlightColor(Object entity)
    {
        return this.highlightColor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderColor(java.
     * lang.Object)
     */
    @Override
    public Color getBorderColor(Object entity)
    {
        /*
        if (!(entity instanceof AgentModelItem))
            return this.agentNormalBorderColor;

        AgentModelItem item = (AgentModelItem) entity;

        if (item.hasErrorAlarms())
            return this.agentErrorBorderColor;

        if (item.hasWarningAlarms())
            return this.agentWarningBorderColor;
*/
        return this.agentNormalBorderColor;
    }

    @Override
    public Color getBorderHighlightColor(Object entity)
    {
        return getBorderColor(entity);
    }

    @Override
    public int getBorderWidth(Object entity)
    {
        /*
        if (!(entity instanceof AgentModelItem))
            return 1;

        if (((AgentModelItem) entity).getAlarms().isEmpty())
            return 1;
*/
        return 3;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.zest.core.viewers.IEntityStyleProvider#getBackgroundColour(
     * java.lang.Object)
     */
    @Override
    public Color getBackgroundColour(Object entity)
    {
        /*
        if (entity instanceof AgentModelItem)
        {
            // if
            // (((AgentModelItem)entity).getStatus().equals(AMSAgentDescription.ACTIVE))
            return this.agentColor;
            // else
            // return this.agentColorInactive;
        }

        if (entity instanceof SubSystemModelItem)
            return this.systemColor;

        if (entity instanceof RootAgentElement)
            return this.rootColor;
*/
        return null;
    }

    @Override
    public Color getForegroundColour(Object entity)
    {
        return null;
    }

    @Override
    public IFigure getTooltip(Object entity)
    {
        /*
        if (!(entity instanceof AgentModelItem))
            return null;

        AgentModelItem item = (AgentModelItem) entity;

        if (!item.getAlarms().isEmpty())
            return new Label(String.format("%d alarm(s)", item.getAlarms().size()), INFO_ICON);
*/
        return null;
    }

    @Override
    public boolean fisheyeNode(Object entity)
    {
        return false;
    }

}
