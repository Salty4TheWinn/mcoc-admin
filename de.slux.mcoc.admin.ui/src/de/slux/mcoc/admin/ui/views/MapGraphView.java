/**
 * 
 */
package de.slux.mcoc.admin.ui.views;

import java.util.logging.Logger;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;

import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.views.provider.MapGraphContentProvider;
import de.slux.mcoc.admin.ui.views.provider.MapGraphLabelProvider;

/**
 * @author slux 
 *
 */
public class MapGraphView extends ViewPart implements IZoomableWorkbenchPart
{
    private static final Logger LOG = Logger.getLogger(MapGraphView.class.getName());

    private static final ImageDescriptor REFRESH_ICON = McocAdminUiPlugin.getImageDescriptor("icons/refresh.png");

    private GraphViewer graphViewer;
    private ISelectionListener listener;
    private Action actionAcknowledgeAll;

    /**
     * Constructor
     */
    public MapGraphView() {
    }

    @Override
    public void createPartControl(Composite parent)
    {
        graphViewer = new GraphViewer(parent, SWT.BORDER);
        graphViewer.setContentProvider(new MapGraphContentProvider(graphViewer));
        graphViewer.setLabelProvider(new MapGraphLabelProvider());
        graphViewer.setNodeStyle(ZestStyles.NODES_CACHE_LABEL | ZestStyles.NODES_NO_ANIMATION);
        graphViewer.setInput(MapGraphContentProvider.getInitialInput());
        graphViewer.getControl().setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

        getSite().setSelectionProvider(graphViewer);

        graphViewer.addDoubleClickListener(new IDoubleClickListener()
        {
            @Override
            public void doubleClick(DoubleClickEvent event)
            {
                // force the detail view to be shown
                /*
                try
                {
                    if (getSite() != null && getSite().getPage() != null)
                        getSite().getPage().showView(AgentDetailView.VIEW_ID);
                }
                catch (PartInitException e)
                {
                    LOG.log(Level.SEVERE, "Unexpected error: " + e, e);
                }
                */
            }
        });

        fillToolbar();
        applyLayout();

        registerSelectionListener();

        initialiseToolBar();

    }

    private void initialiseToolBar()
    {
        // Acknowledge all
        this.actionAcknowledgeAll = new Action()
        {
            public void run()
            {
            }
        };

        this.actionAcknowledgeAll.setText("Acknowledge alarms of every agent");
        this.actionAcknowledgeAll.setToolTipText("Acknowledge alarms of every agent");
        this.actionAcknowledgeAll.setImageDescriptor(McocAdminUiPlugin.getImageDescriptor("icons/acknowledge_all.png"));

        IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(this.actionAcknowledgeAll);

    }

    /**
     * Register selection listener
     */
    private void registerSelectionListener()
    {
        this.listener = new ISelectionListener()
        {
            @Override
            public void selectionChanged(IWorkbenchPart part, ISelection selection)
            {
                onSelectionChanged(part, selection);
            }
        };

        getSite().getPage().addSelectionListener(this.listener);
    }

    /**
     * Selection provider changed in some other view
     * 
     * @param part
     * @param selection
     */
    private void onSelectionChanged(IWorkbenchPart part, ISelection selection)
    {
        if (part instanceof MapGraphView)
            return; // Avoid recursion

        this.graphViewer.setSelection(selection, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose()
    {
        if (listener != null)
        {
            ISelectionService selService = getSite().getWorkbenchWindow().getSelectionService();
            selService.removeSelectionListener(listener);
        }

        super.dispose();
    }

    /**
     * Apply a layout to the graph view
     */
    private void applyLayout()
    {
        // SpringLayoutAlgorithm springAlgorithm = new
        // SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
        RadialLayoutAlgorithm radialAlgorithm = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
        // GridLayoutAlgorithm gridAlgorithm = new
        // GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
        // TreeLayoutAlgorithm treeAlgorithm = new
        // TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
        /*
         * CompositeLayoutAlgorithm compositeAlgorithm = new
         * CompositeLayoutAlgorithm( LayoutStyles.NO_LAYOUT_NODE_RESIZING, new
         * LayoutAlgorithm[] { new
         * DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
         * new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) });
         */
        graphViewer.setLayoutAlgorithm(radialAlgorithm, true);
    }

    /**
     * Fill tool bar
     */
    private void fillToolbar()
    {
        ZoomContributionViewItem toolbarZoomContribution = new ZoomContributionViewItem(this);

        IActionBars bars = getViewSite().getActionBars();
        bars.getMenuManager().add(toolbarZoomContribution);

        Action refresh = new Action()
        {
            @Override
            public void run()
            {
                applyLayout();
            }
        };

        refresh.setText("Refresh");
        refresh.setToolTipText("Refresh the Map view");
        refresh.setImageDescriptor(REFRESH_ICON);

        bars.getMenuManager().add(refresh);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus()
    {
        //notifyStatusChanged(McocAdminUiPlugin.getDefault().getCurrentConnectionStatus());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.zest.core.viewers.IZoomableWorkbenchPart#getZoomableViewer()
     */
    @Override
    public AbstractZoomableViewer getZoomableViewer()
    {
        return this.graphViewer;
    }

}
