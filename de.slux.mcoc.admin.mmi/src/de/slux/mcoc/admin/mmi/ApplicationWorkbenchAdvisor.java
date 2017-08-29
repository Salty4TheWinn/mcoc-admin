package de.slux.mcoc.admin.mmi;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import de.slux.mcoc.admin.mmi.perspective.AllianceWarPlanningPerspective;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor
{

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
    {
        configurer.setShowPerspectiveBar(true);

        ApplicationWorkbenchWindowAdvisor awa = new ApplicationWorkbenchWindowAdvisor(configurer);
        return awa;
    }

    public String getInitialWindowPerspectiveId()
    {
        return AllianceWarPlanningPerspective.ID;
    }

    public void initialize(IWorkbenchConfigurer configurer)
    {
        super.initialize(configurer);
        configurer.setSaveAndRestore(true);
    }

}
