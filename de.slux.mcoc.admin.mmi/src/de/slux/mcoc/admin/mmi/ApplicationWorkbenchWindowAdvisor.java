package de.slux.mcoc.admin.mmi;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import de.slux.mcoc.admin.ui.ApplicationActionBarAdvisor;
import de.slux.mcoc.admin.ui.McocAdminUiPlugin;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{

    private static final Image APP_ICON;

    static
    {
        APP_ICON = McocAdminUiPlugin.getImageDescriptor("icons/icon_48.png").createImage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#dispose()
     */
    @Override
    public void dispose()
    {
        if (APP_ICON != null)
            APP_ICON.dispose();

        super.dispose();
    }

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.application.WorkbenchWindowAdvisor#createActionBarAdvisor(
     * org.eclipse.ui.application.IActionBarConfigurer)
     */
    @Override
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
    {
        ApplicationActionBarAdvisor.initInstance(configurer);

        return ApplicationActionBarAdvisor.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#preWindowOpen()
     */
    @Override
    public void preWindowOpen()
    {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();

        Point windowSize = new Point(1400, 900);

        configurer.setInitialSize(windowSize);

        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(true);
        configurer.setTitle("MCOC - Administration Tool");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#postWindowOpen()
     */
    @Override
    public void postWindowOpen()
    {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();

        configurer.getWindow().getShell().setMaximized(false);
        configurer.getWindow().getShell().setImage(APP_ICON);
    }
}
