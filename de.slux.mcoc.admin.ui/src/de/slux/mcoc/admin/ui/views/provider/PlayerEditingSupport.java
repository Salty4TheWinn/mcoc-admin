package de.slux.mcoc.admin.ui.views.provider;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;

import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.model.PlayerChampion;
import de.slux.mcoc.admin.ui.views.TeamExplorerView;

/**
 *
 */
public class PlayerEditingSupport extends EditingSupport
{
    private final TreeViewer viewer;
    private final CellEditor playerCellEditor;
    private final CellEditor sigLevelCellEditor;
    private final ComboBoxCellEditor starsCellEditor;
    private final ComboBoxCellEditor champCellEditor;
    private ComboBoxCellEditor rankCellEditor;
    private List<String> champModel;
    private List<String> starModel;
    private List<String> rankModel;
    private TreeViewerColumn column;

    /**
     * @param viewer
     * @param treeViewerColumn
     */
    public PlayerEditingSupport(TreeViewer viewer, TreeViewerColumn treeViewerColumn) {
        super(viewer);
        this.viewer = viewer;
        this.champModel = Arrays
                .asList(AWDDataUIModelManager.getInstance().getChampionModel().keySet().toArray(new String[0]));
        this.starModel = Arrays.asList("1", "2", "3", "4", "5", "6");
        this.rankModel = Arrays.asList("1", "2", "3", "4", "5");
        this.playerCellEditor = new TextCellEditor(viewer.getTree());
        this.champCellEditor = new ComboBoxCellEditor(viewer.getTree(), champModel.toArray(new String[0]));
        this.starsCellEditor = new ComboBoxCellEditor(viewer.getTree(), starModel.toArray(new String[0]));
        this.rankCellEditor = new ComboBoxCellEditor(viewer.getTree(), rankModel.toArray(new String[0]));
        this.sigLevelCellEditor = new TextCellEditor(viewer.getTree());

        this.column = treeViewerColumn;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
     */
    @Override
    protected CellEditor getCellEditor(Object element)
    {
        if (element instanceof Player)
            return this.playerCellEditor;
        if (element instanceof PlayerChampion)
        {
            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_NAME))
            {
                return this.champCellEditor;
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_STARS))
            {
                return this.starsCellEditor;
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_RANK))
            {
                return this.rankCellEditor;
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_SIG_LEV))
            {
                return this.sigLevelCellEditor;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
     */
    @Override
    protected boolean canEdit(Object element)
    {
        if (element instanceof Player && !column.getColumn().getText().equals(TeamExplorerView.COLUMN_NAME))
            return false;

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
     */
    @Override
    protected Object getValue(Object element)
    {
        if (element instanceof Player)
        {
            return ((Player) element).getName();
        }

        if (element instanceof PlayerChampion)
        {
            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_NAME))
            {
                return this.champModel.indexOf(((PlayerChampion) element).getId());
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_STARS))
            {
                return this.starModel.indexOf(String.valueOf(((PlayerChampion) element).getStars()));
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_RANK))
            {
                return this.rankModel.indexOf(String.valueOf(((PlayerChampion) element).getRank()));
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_SIG_LEV))
            {
                return ((PlayerChampion) element).getSigLevel();
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    protected void setValue(Object element, Object value)
    {
        if (element instanceof Player)
        {
            ((Player) element).setName(String.valueOf(value));
            this.viewer.update(element, null);
        }
        if (element instanceof PlayerChampion)
        {
            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_NAME))
            {
                if (((Integer) value) != -1)
                {
                    PlayerChampion pc = ((PlayerChampion) element);
                    pc.setId(this.champModel.get((Integer) value));
                    pc.setName(AWDDataUIModelManager.getInstance().getChampionModel().get(pc.getId()).getName());
                    this.viewer.update(element, null);
                }
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_STARS))
            {
                if (((Integer) value) != -1)
                {
                    PlayerChampion pc = ((PlayerChampion) element);
                    pc.setStars(this.starModel.get((Integer) value));
                    this.viewer.update(element, null);
                }
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_RANK))
            {
                PlayerChampion pc = ((PlayerChampion) element);
                pc.setRank(this.rankModel.get((Integer) value));
                this.viewer.update(element, null);
            }

            if (column.getColumn().getText().equals(TeamExplorerView.COLUMN_SIG_LEV))
            {
                PlayerChampion pc = ((PlayerChampion) element);
                String sigLevInput = String.valueOf(value).trim();
                try
                {
                    int sigLevValue = Integer.parseInt(sigLevInput);
                    if (sigLevValue < 0 || sigLevValue > 200)
                    {
                        // TODO: error in the gui somewhere
                    }
                    else
                    {
                        // We accept the edit
                        pc.setSigLevel(sigLevInput);
                        this.viewer.update(element, null);
                    }
                }
                catch (NumberFormatException e)
                {

                }
            }
        }
    }

    /**
     * Close all editors
     */
    public void closeAll()
    {
        this.champCellEditor.deactivate();
        this.playerCellEditor.deactivate();
        this.rankCellEditor.deactivate();
        this.starsCellEditor.deactivate();
    }

}
