package de.slux.mcoc.admin.ui.views.provider;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import de.slux.mcoc.admin.ui.model.PlayerChampion;
import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.model.Team;
import de.slux.mcoc.admin.ui.model.listener.DataModelChangedListener;

/**
 * @author slux
 *
 */
public class TeamExplorerContentProvider implements ITreeContentProvider, DataModelChangedListener
{
    private TreeViewer viewer;

    /**
     * @param treeViewer
     */
    public TeamExplorerContentProvider(TreeViewer treeViewer) {
        this.viewer = treeViewer;
        AWDDataUIModelManager.getInstance().registerListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
     * .viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.
     * Object)
     */
    @Override
    public Object[] getElements(Object inputElement)
    {
        return ((AWDDataUIModelManager) inputElement).getTeamModel().getPlayers().toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
     * Object)
     */
    @Override
    public Object[] getChildren(Object parentElement)
    {
        if (parentElement instanceof PlayerChampion)
            return new Object[0];

        if (parentElement instanceof Player)
            return ((Player) parentElement).getChampions().toArray();

        return ((Team) parentElement).getPlayers().toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.
     * Object)
     */
    @Override
    public Object getParent(Object element)
    {
        if (element instanceof Player)
            return ((Player) element).getTeam();

        if (element instanceof PlayerChampion)
            return ((PlayerChampion) element).getPlayer();

        if (element instanceof Team) // The root element has no parent
            return null;

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
     * Object)
     */
    @Override
    public boolean hasChildren(Object element)
    {
        if (element instanceof Team)
            return !((Team) element).getPlayers().isEmpty();

        if (element instanceof Player)
            return !((Player) element).getChampions().isEmpty();

        return false;
    }

    /**
     * @return the initial input model
     */
    public static Object getInitialInput()
    {
        return AWDDataUIModelManager.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose()
    {
    }

    @Override
    public void modelChanged()
    {
        Display.getDefault().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                if (!TeamExplorerContentProvider.this.viewer.getControl().isDisposed())
                {
                    TeamExplorerContentProvider.this.viewer.setInput(getInitialInput());
                    TeamExplorerContentProvider.this.viewer.expandAll();
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.slux.mcoc.admin.ui.model.listener.DataModelChangedListener#newElement(
     * java.lang.Object, java.lang.Object)
     */
    @Override
    public void newElement(Object parent, Object child)
    {
        Display.getDefault().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                if (!TeamExplorerContentProvider.this.viewer.getControl().isDisposed())
                {
                    TeamExplorerContentProvider.this.viewer.add(parent, child);
                    TeamExplorerContentProvider.this.viewer.expandAll();
                }
            }
        });

    }

    @Override
    public void updateView()
    {
    }

    /*
     * @Override public void newObjectAdded(final SubSystemModelItem parent,
     * final AgentModelItem newChild) { Display.getDefault().asyncExec(new
     * Runnable() {
     * 
     * @Override public void run() { if
     * (!TeamExplorerContentProvider.this.viewer.getControl().isDisposed()) {
     * TeamExplorerContentProvider.this.viewer.add(parent, newChild);
     * TeamExplorerContentProvider.this.viewer.expandAll(); } } }); }
     */
    /*
     * @Override public void dataRemoved(SubSystemModelItem parent, final
     * AgentModelItem deletedChild) { Display.getDefault().asyncExec(new
     * Runnable() {
     * 
     * @Override public void run() { if
     * (!TeamExplorerContentProvider.this.viewer.getControl().isDisposed()) {
     * TeamExplorerContentProvider.this.viewer.remove(new
     * AgentModelItem[]{deletedChild});
     * TeamExplorerContentProvider.this.viewer.expandAll(); } } }); }
     */
    /*
     * @Override public void modelItemChanged(final AgentModelItem agent) {
     * Display.getDefault().asyncExec(new Runnable() {
     * 
     * @Override public void run() { if
     * (!TeamExplorerContentProvider.this.viewer.getControl().isDisposed()) {
     * TeamExplorerContentProvider.this.viewer.update(agent, null);
     * TeamExplorerContentProvider.this.viewer.expandAll(); } } }); }
     */

}
