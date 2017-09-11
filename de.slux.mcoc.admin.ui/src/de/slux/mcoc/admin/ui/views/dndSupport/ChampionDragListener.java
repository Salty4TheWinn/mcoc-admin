/**
 * 
 */
package de.slux.mcoc.admin.ui.views.dndSupport;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;

import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.model.PlayerChampion;
import de.slux.mcoc.admin.ui.views.provider.PlayerEditingSupport;

/**
 * @author Slux
 *
 */
public class ChampionDragListener implements DragSourceListener
{
    private final TreeViewer viewer;
    private List<PlayerEditingSupport> editors;

    /**
     * Ctor
     * 
     * @param viewer
     * @param editingSupports
     */
    public ChampionDragListener(TreeViewer viewer, List<PlayerEditingSupport> editingSupports) {
        this.viewer = viewer;
        this.editors = editingSupports;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.dnd.DragSourceListener#dragStart(org.eclipse.swt.dnd.
     * DragSourceEvent)
     */
    @Override
    public void dragStart(DragSourceEvent event)
    {

        IStructuredSelection selection = this.viewer.getStructuredSelection();
        if (selection.getFirstElement() instanceof Player)
        {
            // Can't drag this element
            event.doit = false;
        }

        if (selection.getFirstElement() instanceof PlayerChampion)
        {
            PlayerChampion pc = ((PlayerChampion) selection.getFirstElement());

            if (pc.getId() == null)
            {
                // Can't drag this element because its champion is undefined
                event.doit = false;
            }
        }

        // Close all the editors
        for (PlayerEditingSupport pes : this.editors)
        {
            pes.closeAll();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.dnd.DragSourceListener#dragSetData(org.eclipse.swt.dnd.
     * DragSourceEvent)
     */
    @Override
    public void dragSetData(DragSourceEvent event)
    {
        System.out.println("Setting Data...");
        IStructuredSelection selection = this.viewer.getStructuredSelection();
        PlayerChampion pc = null;
        if (selection.getFirstElement() instanceof PlayerChampion)
        {
            pc = (PlayerChampion) selection.getFirstElement();

        }

        if (TextTransfer.getInstance().isSupportedType(event.dataType))
        {
            // The drop listener will get the real Champion object using this
            // unique ID
            if (pc != null)
                event.data = pc.getUniqueId();
            else
                event.data = "invalid";
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.dnd.DragSourceListener#dragFinished(org.eclipse.swt.dnd.
     * DragSourceEvent)
     */
    @Override
    public void dragFinished(DragSourceEvent event)
    {
        System.out.println("Drag finished " + event.getSource());

    }

}
