package de.slux.mcoc.admin.ui.views.provider;

import java.io.File;
import java.util.logging.Logger;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import de.slux.mcoc.admin.data.model.McocDataManager;
import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.model.PlayerChampion;
import de.slux.mcoc.admin.ui.views.TeamExplorerView;

public class TeamExplorerLabelProvider extends ColumnLabelProvider
{
    private static final Logger LOG = Logger.getLogger(TeamExplorerLabelProvider.class.getName());

    private static final ImageDescriptor UNKNOWN_CHAMP_IMG = McocAdminUiPlugin
            .getImageDescriptor("icons/generic/unknown_champ.png");

    private static final Image SYSTEM_ICON = McocAdminUiPlugin.getImageDescriptor("icons/system.png").createImage();
    private static final Image SYSTEM_16_ICON = McocAdminUiPlugin.getImageDescriptor("icons/icon_16.png").createImage();
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
     * @see
     * org.eclipse.jface.viewers.ColumnLabelProvider#getFont(java.lang.Object)
     */
    @Override
    public Font getFont(Object element)
    {

        if (element instanceof Player)

        {
            Font f = Display.getDefault().getSystemFont();

            if (f == null)
            {
                LOG.severe("Cannot retrieve the system default font");
            }
            else
            {
                FontDescriptor boldDescriptor = FontDescriptor.createFrom(f).setStyle(SWT.BOLD);
                Font playerFont = boldDescriptor.createFont(Display.getDefault());

                return playerFont;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
     */
    @Override
    public void dispose()
    {
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
                return ((PlayerChampion) item).getStars();
            else if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_RANK))
                return ((PlayerChampion) item).getRank();
            else if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_SIG_LEV))
                return ((PlayerChampion) item).getSigLevel();
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
        // Player champion decoration
        if (item instanceof PlayerChampion)
        {
            PlayerChampion pc = (PlayerChampion) item;

            // Column Name
            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_NAME))
            {
                return getChampionPortraitImage(pc, 24, 24);
            }

            // Column Stars
            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_STARS))
            {
                return getChampionStarsImage(pc);
                // return null;
            }

        }
        return null;
    }

    private Image getChampionStarsImage(PlayerChampion pc)
    {
        return null;
        /*
         * int stars = 0; if (pc.getStars() > 0) { stars = pc.getStars(); }
         * 
         * if (stars == 0) return null;
         * 
         * Image image = new Image(STAR_STANDARD.getDevice(), 16 * stars, 16);
         * GC gc = new GC(image); gc.setAntialias(SWT.ON);
         * gc.setInterpolation(SWT.HIGH);
         * 
         * for (int i = 1; i <= stars; ++i) { gc.drawImage(STAR_STANDARD, 0, 0,
         * STAR_STANDARD.getBounds().width, STAR_STANDARD.getBounds().height, i
         * * STAR_STANDARD.getBounds().width, 0, 16, 16); }
         * 
         * gc.dispose();
         * 
         * return image;
         */
    }

    private Image getChampionPortraitImage(PlayerChampion pc, int width, int height)
    {
        Image image = new Image(Display.getDefault(), width, height);
        GC gc = new GC(image);
        gc.setAntialias(SWT.ON);
        gc.setInterpolation(SWT.HIGH);
        if (pc.getId() != null)
        {
            Image champImg = McocAdminUiPlugin.getImageDescriptor(McocDataManager.BUNDLE_ID,
                    McocDataManager.CHAMPIONS_IMG_DIR + File.separator + pc.getId() + ".png").createImage();
            gc.drawImage(champImg, 0, 0, champImg.getBounds().width, champImg.getBounds().height, 0, 0, width, height);
            champImg.dispose();
        }
        else
        {
            Image unknownChampImg = UNKNOWN_CHAMP_IMG.createImage();
            // Empty image
            gc.drawImage(unknownChampImg, 0, 0, unknownChampImg.getBounds().width, unknownChampImg.getBounds().height,
                    0, 0, width, height);
        }
        gc.dispose();
        return image;
    }
}