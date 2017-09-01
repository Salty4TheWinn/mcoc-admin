package de.slux.mcoc.admin.ui.views.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;

import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.model.PlayerChampion;

/**
 *
 */
public class PlayerEditingSupport extends EditingSupport
{
    private final TreeViewer viewer;
    private final CellEditor playerCellEditor;
    private final ComboBoxCellEditor champCellEditor;
    private List<String> champModel;

    /**
     * @param viewer
     */
    public PlayerEditingSupport(TreeViewer viewer) {
        super(viewer);
        this.champModel = new ArrayList<>();
        this.champModel.addAll(
                Arrays.asList(AWDDataUIModelManager.getInstance().getChampionModel().keySet().toArray(new String[0])));
        this.viewer = viewer;
        this.playerCellEditor = new TextCellEditor(viewer.getTree());
        this.champCellEditor = new ComboBoxCellEditor(viewer.getTree(), champModel.toArray(new String[0]));
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
            return this.champCellEditor;

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
            return this.champModel.indexOf(((PlayerChampion) element).getId());
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
            ((PlayerChampion) element).setId(this.champModel.get((Integer)value));
            // TODO: update champ name
            this.viewer.update(element, null);
        }
    }

}
