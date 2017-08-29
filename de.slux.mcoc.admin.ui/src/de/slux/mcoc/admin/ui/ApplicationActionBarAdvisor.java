
package de.slux.mcoc.admin.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{
    public enum ImageType
    {
        INFO,
        ERROR,
        CONNECTED,
        DISCONNECTED,
        NONE
    }
    
    private static final Map<ImageType, Image> ICONS;
    
    static 
    {
        ICONS = new HashMap<ApplicationActionBarAdvisor.ImageType, Image>();
        ICONS.put(ImageType.INFO,           McocAdminUiPlugin.getImageDescriptor("icons/info.png").createImage());
        ICONS.put(ImageType.CONNECTED,      McocAdminUiPlugin.getImageDescriptor("icons/network-connect.png").createImage());
        ICONS.put(ImageType.DISCONNECTED,   McocAdminUiPlugin.getImageDescriptor("icons/network-disconnect.png").createImage());
        ICONS.put(ImageType.ERROR,          McocAdminUiPlugin.getImageDescriptor("icons/error.png").createImage());
    }
    
    // Actions - important to allocate these only in makeActions, and then use
    // them
    // in the fill methods. This ensures that the actions aren't recreated
    // when fillActionBars is called with FILL_PROXY.
    
    private static ApplicationActionBarAdvisor INSTANCE = null;

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.application.ActionBarAdvisor#dispose()
     */
    @Override
    public void dispose()
    {
        for(Image i : ICONS.values())
            i.dispose();
        
        super.dispose();
    }
    
    private ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
    {
        super(configurer);
    }
    
    /**
     * Init instance
     * @param configurer
     * @throws IllegalStateException if the {@link ApplicationActionBarAdvisor#INSTANCE} has been already initialised
     */
    public static void initInstance(IActionBarConfigurer configurer)
    {
        if (INSTANCE != null)
            throw new IllegalStateException("The singleton has been already initialised.");

        INSTANCE = new ApplicationActionBarAdvisor(configurer);
    }

    /**
     * Get singleton instance
     * @return the instance
     * @throws NullPointerException if {@link ApplicationActionBarAdvisor#INSTANCE} is null
     * @see ApplicationActionBarAdvisor#initInstance(IActionBarConfigurer)
     */
    public static ApplicationActionBarAdvisor getInstance()
    {
        if (INSTANCE == null)
            throw new NullPointerException("INSTANCE is null");

        return INSTANCE;
    }

    /**
     * Set message
     * @param message
     * @param imageType
     */
    public void setStatusMessage(final String message, final ImageType imageType)
    {
        Display.getDefault().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                if (getActionBarConfigurer() == null || getActionBarConfigurer().getStatusLineManager() == null)    //probably exiting...
                    return;
                
                if (imageType.equals(ImageType.NONE))
                    getActionBarConfigurer().getStatusLineManager().setMessage(message);
                else
                    getActionBarConfigurer().getStatusLineManager().setMessage(ICONS.get(imageType), message);
            }
        });
    }
}
