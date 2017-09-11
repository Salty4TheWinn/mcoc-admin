/**
 * 
 */
package de.slux.mcoc.admin.ui.views.provider;

import java.util.logging.Logger;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.model.MapNode;
import de.slux.mcoc.admin.ui.model.listener.DataModelChangedListener;

/**
 * @author slux
 *
 */
public class MapGraphContentProvider implements IGraphEntityContentProvider, IContentProvider, DataModelChangedListener
{
    private static final Logger LOG = Logger.getLogger(MapGraphContentProvider.class.getName());

    private GraphViewer viewer;

    public MapGraphContentProvider(GraphViewer graphViewer) {
        this.viewer = graphViewer;
        AWDDataUIModelManager.getInstance().registerListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.zest.core.viewers.IGraphEntityContentProvider#getConnectedTo(
     * java.lang.Object)
     */
    @Override
    public Object[] getConnectedTo(Object entity)
    {
        if (entity instanceof MapNode)
        {
            MapNode node = (MapNode) entity;
            return node.getConnections().toArray();
        }

        return null;
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
     * org.eclipse.zest.core.viewers.IGraphEntityContentProvider#getElements(
     * java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement)
    {
        // We need to provide all the nodes in the graph
        AWDDataUIModelManager input = (AWDDataUIModelManager) inputElement;
        return input.getAwMapNodes().toArray();

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

    @Override
    public void newElement(Object parent, Object child)
    {
        // TODO Auto-generated method stub

    }
}
