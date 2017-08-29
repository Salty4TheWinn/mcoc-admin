package de.slux.mcoc.admin.ui.views.provider;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.graphics.Image;

import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.model.PlayerChampion;
import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.views.TeamExplorerView;

public class TeamExplorerLabelProvider extends ColumnLabelProvider
{
    private static final Image HOME_ICON = McocAdminUiPlugin.getImageDescriptor("icons/home.png").createImage();
    private static final Image SYSTEM_ICON = McocAdminUiPlugin.getImageDescriptor("icons/system.png").createImage();
    private static final Image SYSTEM_16_ICON = McocAdminUiPlugin.getImageDescriptor("icons/icon_16.png")
            .createImage();
    private static final Image BOSS_ICON = McocAdminUiPlugin.getImageDescriptor("icons/agent_boss.png").createImage();
    private static final Image OBSERVER_ICON = McocAdminUiPlugin.getImageDescriptor("icons/agent_observer.png")
            .createImage();
    private static final Image YP_ICON = McocAdminUiPlugin.getImageDescriptor("icons/agent_yellow_pages.png")
            .createImage();
    private static final Image NOTIF_ICON = McocAdminUiPlugin.getImageDescriptor("icons/agent_notification.png")
            .createImage();
    private static final Image WARNING_ICON = McocAdminUiPlugin.getImageDescriptor("icons/agent_warning.png")
            .createImage();
    private static final Image ACTIVE_ICON = McocAdminUiPlugin.getImageDescriptor("icons/agent.png").createImage();
    private static final Image SUSPEND_ICON = McocAdminUiPlugin.getImageDescriptor("icons/agent_susp.png")
            .createImage();

    private TreeViewerColumn column;

    /**
     * Constructor
     * 
     * @param column
     */
    public TeamExplorerLabelProvider(TreeViewerColumn column) {
        this.column = column;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
     */
    @Override
    public void dispose()
    {
        HOME_ICON.dispose();
        SYSTEM_ICON.dispose();
        SYSTEM_16_ICON.dispose();
        BOSS_ICON.dispose();
        OBSERVER_ICON.dispose();
        YP_ICON.dispose();
        NOTIF_ICON.dispose();
        WARNING_ICON.dispose();
        ACTIVE_ICON.dispose();
        SUSPEND_ICON.dispose();

        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object item)
    {
        if (item instanceof Player)
        {
            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_NAME))
                return ((Player) item).getName();
            else
                return "";
        }

        if (item instanceof PlayerChampion)
        {
            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_NAME))
                return ((PlayerChampion) item).getName();
            else if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_STARS))
                return Integer.toString(((PlayerChampion) item).getStars());
            else if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_RANK))
                return ((PlayerChampion) item).getRank().name();
            else if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_SIG_LEV))
                return Integer.toString(((PlayerChampion) item).getSigLevel());
            else
                return "";
        }

        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object item)
    {
        /*
         * if (!column.getColumn().getText().equals(TeamExplorerView.
         * COLUMN_SYSTEM_AGENT)) return null;
         * 
         * if (item instanceof SubSystemModelItem) { SubSystemModelItem ss =
         * (SubSystemModelItem) item; if (ss.getType() == SubSystemType.MAIN)
         * return HOME_ICON; else return SYSTEM_ICON; }
         * 
         * if (item instanceof RootAgentElement) return SYSTEM_16_ICON;
         * 
         * if (item instanceof AgentModelItem) { AgentModelItem a =
         * (AgentModelItem) item;
         * 
         * if (a.getType() == Type.JADE_MANAGER) return BOSS_ICON;
         * 
         * if (a.getType() == Type.JADE_UI) return OBSERVER_ICON;
         * 
         * if (a.getType() == Type.JADE_YELLOW_PAGES) return YP_ICON;
         * 
         * if (a.hasErrorAlarms()) return WARNING_ICON;
         * 
         * if (a.hasWarningAlarms()) return NOTIF_ICON; //
         * if(a.getStatus().equals(AMSAgentDescription.ACTIVE)) return
         * ACTIVE_ICON; // else // return SUSPEND_ICON;
         * 
         * }
         * 
         */
        return null;
    }
}