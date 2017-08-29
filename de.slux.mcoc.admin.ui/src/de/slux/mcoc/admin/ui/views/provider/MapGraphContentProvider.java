/**
 * 
 */
package de.slux.mcoc.admin.ui.views.provider;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.model.Player;
import de.slux.mcoc.admin.ui.model.listener.DataModelChangedListener;

/**
 * @author slux
 *
 */
public class MapGraphContentProvider implements IGraphEntityContentProvider, IContentProvider, DataModelChangedListener
{
	
    private GraphViewer viewer;

    public MapGraphContentProvider(GraphViewer graphViewer)
    {
        this.viewer = graphViewer;
        AWDDataUIModelManager.getInstance().registerListener(this);
    }

    /* (non-Javadoc)
	 * @see org.eclipse.zest.core.viewers.IGraphEntityContentProvider#getConnectedTo(java.lang.Object)
	 */
	@Override
	public Object[] getConnectedTo(Object entity)
	{
	    /*
		if (entity instanceof AgentModelItem)
			return new Object[0];
		
		if (entity instanceof RootAgentElement)
		    return ((RootAgentElement) entity).getSubSystems().values().toArray();
		
		return ((SubSystemModelItem) entity).getAgents().toArray();
		*/
	    return null; // FIXME
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose()
	{
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.core.viewers.IGraphEntityContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement)
	{
	    //We need to provide all the nodes in the graph
	    
	    Collection<Object> nodes = new HashSet<Object>();
	    /*
	    AWDDataUIModelManager input = (AWDDataUIModelManager) inputElement;
	    nodes.add(input.getRootAgentModel());
	    nodes.addAll(input.getRootAgentModel().getSubSystems().values());
	    
	    for (SubSystemModelItem ss : input.getRootAgentModel().getSubSystems().values())
	        nodes.addAll(ss.getAgents());
	    */
	    return nodes.toArray();
	}
	
	/**
	 * @return the initial input model
	 */
	public static Object getInitialInput()
	{
		return AWDDataUIModelManager.getInstance();
	}

    @Override
    public void modelChanged()
    {
        Display.getDefault().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                if (!MapGraphContentProvider.this.viewer.getControl().isDisposed())
                    MapGraphContentProvider.this.viewer.setInput(getInitialInput());
            }
        });
    }
/*
    @Override
    public void newObjectAdded(final SubSystemModelItem newItem)
    {
        modelChanged(DataModelType.DataModelTypeAgentNetwork);
    }

    @Override
    public void newObjectsAdded(Map<? extends String, ? extends SubSystemModelItem> newItems)
    {
        modelChanged(DataModelType.DataModelTypeAgentNetwork);
    }

    @Override
    public void dataRemoved(Collection<? extends LogItem> removedItems)
    {        
    }

    @Override
    public void dataRemoved(LogItem item)
    {        
    }

    @Override
    public void newObjectsAdded(Collection<? extends LogItem> newItems)
    {        
    }

    @Override
    public void newObjectAdded(LogItem item)
    {        
    }

    @Override
    public void newObjectAdded(SubSystemModelItem parent, AgentModelItem newChild)
    {
        modelChanged(DataModelType.DataModelTypeAgentNetwork);
    }

    @Override
    public void dataRemoved(SubSystemModelItem parent, AgentModelItem deletedChild)
    {
        modelChanged(DataModelType.DataModelTypeAgentNetwork);
    }

    @Override
    public void modelItemChanged(AgentModelItem agent)
    {
        modelChanged(DataModelType.DataModelTypeAgentNetwork);
    }

    @Override
    public void newObjectAdded(File file)
    {    
        //not used here
    }

    @Override
    public void dataRemoved(File file)
    {    
        //not used here
    }
    */

    @Override
    public void newElement(Object parent, Object child)
    {
        // TODO Auto-generated method stub
        
    }
}
