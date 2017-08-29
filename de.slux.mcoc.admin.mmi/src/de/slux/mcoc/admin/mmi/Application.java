package de.slux.mcoc.admin.mmi;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import de.slux.mcoc.admin.mmi.utils.MmiLogFormatter;
import de.slux.mcoc.admin.ui.McocAdminUiPlugin;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication
{

    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    private static final String CONFIG_ARGUMENT_NAME = "conf";
    private static final String JADE_PREFIX = "jade.";
    private Properties properties;

    /**
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    public Object start(IApplicationContext context)
    {
        String configFilePath = null;

        try
        {
            configFilePath = getCommandLineArgument(CONFIG_ARGUMENT_NAME);
        }
        catch (IllegalArgumentException e)
        {
            System.err.println("FATAL Error. Aborting...");
            e.printStackTrace();
            return -1;
        }

        // Set the configuration file for logging
        try
        {
            LogManager.getLogManager().readConfiguration(new FileInputStream(configFilePath));

            // Thanks for this bug, we have to set manually the formatter
            // BUG --> https://bugs.eclipse.org/bugs/show_bug.cgi?id=319484
            MmiLogFormatter formatter = new MmiLogFormatter();
            Enumeration<String> names = LogManager.getLogManager().getLoggerNames();
            while (names.hasMoreElements())
            {
                String logName = names.nextElement();
                Logger l = LogManager.getLogManager().getLogger(logName);
                for (Handler handler : l.getHandlers())
                    handler.setFormatter(formatter);
            }
        }
        catch (Exception e)
        {
            System.err.println("FATAL Error. Cannot read configuration file '" + configFilePath + "'. Aborting...");
            e.printStackTrace();
            return -1;
        }

        LOG.info("### MCOC Administration Tool ###");
        LOG.config("Reading configuration from file: " + configFilePath);

        final String settingsFolder = System.getProperty("user.home") + File.separator + ".mmi-settings";
        File settingFileFolder = new File(settingsFolder);

        try
        {
            if (!settingFileFolder.exists())
                settingFileFolder.mkdirs();

            Location instanceLoc = Platform.getInstanceLocation();
            // FIXME instanceLoc.set(new URL("file", null, settingsFolder),
            // false);
        }
        catch (Exception e)
        {
            LOG.log(Level.SEVERE, "Failed to access settings folder " + settingsFolder, e);
            return -1;
        }

        try
        {
            initConfigurationManager(configFilePath);
        }
        catch (Exception e)
        {
            LOG.log(Level.SEVERE, "Failed loading configuration file " + configFilePath, e);
            return -1;
        }

        Display display = PlatformUI.createDisplay();
        try
        {
            ApplicationWorkbenchAdvisor applicationWorkbenchAdvisor = new ApplicationWorkbenchAdvisor();
            waitForWorkbanchReady();

            int returnCode = PlatformUI.createAndRunWorkbench(display, applicationWorkbenchAdvisor);

            if (returnCode == PlatformUI.RETURN_RESTART)
            {
                return IApplication.EXIT_RESTART;
            }

            return IApplication.EXIT_OK;
        }
        finally
        {
            display.dispose();
        }
    }

    private void initConfigurationManager(String configFilePath) throws Exception
    {
        this.properties = new Properties();
        this.properties.load(new FileInputStream(new File(configFilePath)));

        McocAdminUiPlugin.getDefault().setProperties(this.properties);

        for (final Entry<Object, Object> entry : this.properties.entrySet())
        {
            LOG.config(entry.getKey() + "=" + entry.getValue());
            if (entry.getKey().toString().startsWith(JADE_PREFIX))
                System.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }

        // Add few extra JADE properties
        System.setProperty("jade.container", "true");
        System.setProperty("jade.main", "false");
        System.setProperty("jade.gui", "false");

    }

    private void waitForWorkbanchReady()
    {
        Display.getDefault().asyncExec(new Runnable()
        {
            @Override
            public void run()
            {
                if (PlatformUI.getWorkbench().isStarting())
                {
                    Display.getDefault().timerExec(1000, this);
                    return;
                }

                LOG.info("Workbench has been started: " + Boolean.toString(!PlatformUI.getWorkbench().isStarting()));

                // the workbench finished the initialisation process
                // IWorkbenchWindow workbenchWindow =
                // PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                // ConfigurationPerspectiveAdapter perspectiveListener = new
                // ConfigurationPerspectiveAdapter();
                // workbenchWindow.addPerspectiveListener(perspectiveListener);

                // update the PerspectiveListener with the current perspective
                // we need to perform this update by hand as no event is
                // sent by the platform at
                // platform initialisation and we need to have the current
                // perspective set
                // perspectiveListener.perspectiveActivated(PlatformUI
                // .getWorkbench().getActiveWorkbenchWindow()
                // .getActivePage(), PlatformUI.getWorkbench()
                // .getActiveWorkbenchWindow().getActivePage()
                // .getPerspective());

            }
        });
    }

    /**
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    public void stop()
    {
        if (!PlatformUI.isWorkbenchRunning())
            return;

        LOG.info("Application shutdown");

        final IWorkbench workbench = PlatformUI.getWorkbench();
        final Display display = workbench.getDisplay();

        display.syncExec(new Runnable()
        {
            public void run()
            {
                if (!display.isDisposed())
                    workbench.close();
            }
        });
    }

    /**
     * Get the VALUE of an argument matching the pattern
     * -&lt;KEY&gt;=&lt;VALUE&gt;
     * 
     * @param argumentName
     *            the KEY
     * @return the VALUE
     * @throws IllegalArgumentException
     *             if the KEY can't be retrieved
     */
    private String getCommandLineArgument(String argumentName) throws IllegalArgumentException
    {
        String[] args = Platform.getCommandLineArgs();
        String value = null;

        for (int i = 0; i < args.length; ++i)
        {
            // Argument: -<KEY>=<VALUE>
            if (args[i].matches("\\-" + argumentName + "\\=.+"))
            {
                String[] parts = args[i].split("=");
                if (parts.length == 2)
                    value = parts[1];
            }
        }

        if (value == null)
            throw new IllegalArgumentException("Cannot find gommand line argument -" + argumentName);

        return value;
    }
}
