package de.slux.mcoc.admin.ui;

import java.util.Properties;
import java.util.logging.Logger;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class McocAdminUiPlugin extends AbstractUIPlugin
{
    private static final Logger LOG = Logger.getLogger(McocAdminUiPlugin.class.getName());

    // The plug-in ID
    public static final String PLUGIN_ID = "de.slux.mcoc.admin.ui"; //$NON-NLS-1$

    // The shared instance
    private static McocAdminUiPlugin plugin;

    private Properties properties;

    /**
     * The constructor
     */
    public McocAdminUiPlugin() {
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        if (plugin == null)
            plugin = this;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception
    {
        // super.stop(context);
        // plugin = null;
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static McocAdminUiPlugin getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     *
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path)
    {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     *
     * @param pluginId
     *            the plugin where to find the path
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String pluginId, String path)
    {
        return imageDescriptorFromPlugin(pluginId, path);
    }

    /**
     * Set properties
     * 
     * @param properties
     */
    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }

    /**
     * Get the system properties
     * 
     * @return
     */
    public Properties getProperties()
    {
        if (this.properties == null)
            throw new NullPointerException("Null properties field");

        return this.properties;
    }

    /**
     * Set or append text to the window title
     * 
     * @param text
     * @param append
     */
    public void setWindowTitle(final String text, final boolean append)
    {
        // Notify the connection status to all views
        Display.getDefault().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                String oldTitle = "";

                if (append)
                    oldTitle = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getText();

                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setText(oldTitle + text);
            }
        });
    }
}
