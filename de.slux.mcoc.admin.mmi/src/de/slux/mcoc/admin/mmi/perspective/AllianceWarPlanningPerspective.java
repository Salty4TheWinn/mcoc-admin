
package de.slux.mcoc.admin.mmi.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class AllianceWarPlanningPerspective implements IPerspectiveFactory
{

    public static final String ID = "de.slux.mcoc.admin.mmi.perspective.AllianceWarPlanningPerspective";

    /**
     * Creates the initial layout for a page.
     */
    public void createInitialLayout(IPageLayout layout)
    {
        layout.setEditorAreaVisible(false);

        /*
         * Defined in plugin.xml String editorArea = layout.getEditorArea();
         * layout.addView(ReportingView.ID, IPageLayout.LEFT, 1.0f, editorArea);
         */

        addFastViews(layout);
        addViewShortcuts(layout);
        addPerspectiveShortcuts(layout);
    }

    /**
     * Add fast views to the perspective.
     */
    private void addFastViews(IPageLayout layout)
    {
    }

    /**
     * Add view shortcuts to the perspective.
     */
    private void addViewShortcuts(IPageLayout layout)
    {
    }

    /**
     * Add perspective shortcuts to the perspective.
     */
    private void addPerspectiveShortcuts(IPageLayout layout)
    {
    }

}
