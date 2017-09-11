package de.slux.mcoc.admin.ui.views;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import de.slux.mcoc.admin.ui.McocAdminUiPlugin;
import de.slux.mcoc.admin.ui.model.AWDDataUIModelManager;
import de.slux.mcoc.admin.ui.views.dndSupport.ChampionDragListener;
import de.slux.mcoc.admin.ui.views.provider.PlayerEditingSupport;
import de.slux.mcoc.admin.ui.views.provider.TeamExplorerContentProvider;
import de.slux.mcoc.admin.ui.views.provider.TeamExplorerLabelProvider;

/**
 * @author slux
 *
 */
public class TeamExplorerView extends ViewPart
{
    private static final Logger LOG = Logger.getLogger(TeamExplorerView.class.getName());

    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_STARS = "Stars";
    public static final String COLUMN_RANK = "Rank";
    public static final String COLUMN_SIG_LEV = "Sig Level";

    private Tree tree;
    private TreeViewer treeViewer;
    private ISelectionListener listener;

    private Action addNewPlayer;

    public TeamExplorerView() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
     * widgets.Composite)
     */
    @Override
    public void createPartControl(Composite parent)
    {

        Composite composite = new Composite(parent, SWT.NONE);
        TreeColumnLayout tcl_composite = new TreeColumnLayout();
        composite.setLayout(tcl_composite);

        treeViewer = new TreeViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        tree = treeViewer.getTree();
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);
        treeViewer.setAutoExpandLevel(3);

        List<PlayerEditingSupport> editingSupports = new ArrayList<>();

        // Set columns
        TreeViewerColumn treeViewerColumn_1 = new TreeViewerColumn(treeViewer, SWT.LEFT);
        treeViewerColumn_1.setLabelProvider(new TeamExplorerLabelProvider(treeViewerColumn_1));
        TreeColumn treeColumn = treeViewerColumn_1.getColumn();
        treeColumn.setText(COLUMN_NAME);
        tcl_composite.setColumnData(treeColumn, new ColumnWeightData(40, 120, true));
        PlayerEditingSupport editingSupport = new PlayerEditingSupport(this.treeViewer, treeViewerColumn_1);
        editingSupports.add(editingSupport);
        treeViewerColumn_1.setEditingSupport(editingSupport);

        TreeViewerColumn treeViewerColumn_2 = new TreeViewerColumn(treeViewer, SWT.NONE);
        TreeColumn trclmnNewColumn_2 = treeViewerColumn_2.getColumn();
        tcl_composite.setColumnData(trclmnNewColumn_2, new ColumnWeightData(20, 50, true));
        trclmnNewColumn_2.setText(COLUMN_STARS);
        treeViewerColumn_2.setLabelProvider(new TeamExplorerLabelProvider(treeViewerColumn_2));
        editingSupport = new PlayerEditingSupport(this.treeViewer, treeViewerColumn_2);
        editingSupports.add(editingSupport);
        treeViewerColumn_2.setEditingSupport(editingSupport);

        TreeViewerColumn treeViewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
        TreeColumn trclmnNewColumn = treeViewerColumn.getColumn();
        tcl_composite.setColumnData(trclmnNewColumn, new ColumnWeightData(20, 20, true));
        trclmnNewColumn.setText(COLUMN_RANK);
        treeViewerColumn.setLabelProvider(new TeamExplorerLabelProvider(treeViewerColumn));
        editingSupport = new PlayerEditingSupport(this.treeViewer, treeViewerColumn);
        editingSupports.add(editingSupport);
        treeViewerColumn.setEditingSupport(editingSupport);

        TreeViewerColumn treeViewerColumn_3 = new TreeViewerColumn(treeViewer, SWT.NONE);
        treeViewerColumn_3.setLabelProvider(new TeamExplorerLabelProvider(treeViewerColumn_3));
        TreeColumn trclmnSignature = treeViewerColumn_3.getColumn();
        trclmnSignature.setText(COLUMN_SIG_LEV);
        tcl_composite.setColumnData(trclmnSignature, new ColumnWeightData(20, 5, true));
        treeViewerColumn_3.setLabelProvider(new TeamExplorerLabelProvider(treeViewerColumn_3));
        editingSupport = new PlayerEditingSupport(this.treeViewer, treeViewerColumn_3);
        editingSupports.add(editingSupport);
        treeViewerColumn_3.setEditingSupport(editingSupport);

        TeamExplorerContentProvider contentProvider = new TeamExplorerContentProvider(treeViewer);
        treeViewer.setContentProvider(contentProvider);
        treeViewer.setInput(TeamExplorerContentProvider.getInitialInput());
        // Add selection provider
        // getSite().setSelectionProvider(treeViewer);

        // registerSelectionListener();

        // D&D Support
        int operations = DND.DROP_COPY | DND.DROP_MOVE;
        Transfer[] transferTypes = new Transfer[] { TextTransfer.getInstance() };
        this.treeViewer.addDragSupport(operations, transferTypes, new ChampionDragListener(this.treeViewer, editingSupports));

        initialiseToolBar();
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

        if (part instanceof TeamExplorerView)
            return; // Avoid recursion

        this.treeViewer.setSelection(selection, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus()
    {
        // notifyStatusChanged(McocAdminUiPlugin.getDefault().getCurrentConnectionStatus());
    }

    private void initialiseToolBar()
    {
        // Acknowledge all
        this.addNewPlayer = new Action()
        {
            public void run()
            {
                TeamExplorerView.this.acknowledgeAllAlarmsOfAgents();
            }
        };

        this.addNewPlayer.setText("Add a new player in the team");
        this.addNewPlayer.setToolTipText("Add a new player in the team");
        this.addNewPlayer.setImageDescriptor(McocAdminUiPlugin.getImageDescriptor("icons/generic/new_player.png"));

        IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        toolbarManager.add(this.addNewPlayer);

    }

    /**
     * Acknowledge all the alarm of this
     */
    protected void acknowledgeAllAlarmsOfAgents()
    {
        AWDDataUIModelManager.getInstance().addNewPlayer();

    }

}
