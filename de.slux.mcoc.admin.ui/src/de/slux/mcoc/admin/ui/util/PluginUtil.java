/**
 * 
 */
package de.slux.mcoc.admin.ui.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.Bundle;

/**
 * @author slux
 *
 */
public class PluginUtil
{
	private static final Logger logger = Logger.getLogger(PluginUtil.class.getName());
	
	public static String makePathFor(Plugin p, String file) {
		return makePathFor(p.getBundle(), file);
	}

	public static String makePathFor(Bundle bundle, String fileName) {
		try {
			URL url = FileLocator.find(bundle, new Path(fileName), null);
			
			if(url != null)
				return FileLocator.toFileURL(url).getPath();
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		
		logger.log(Level.SEVERE, "Cannot find file " + fileName + " in bundle "
				+ bundle.getSymbolicName() + ".");

		return null;
	}
	
	/**
	 * 
	 * @param pluginName
	 * @param internalPath files/myFile.txt
	 * @return File on success, null otherwise
	 */
	public static File getPluginResource(String pluginName, String internalPath)
	{
		File f = null;
		
		try
		{
			Bundle bundle = Platform.getBundle(pluginName);
			String path = makePathFor(bundle, internalPath);

			if (path == null)
			{
				logger.log(Level.SEVERE, "getPluginResource(pluginName='" + pluginName +
								"', '" + internalPath + "'): file not found");
				return null;
			}
			
			f = new File(path);
			
			if (!f.exists())
			{
				logger.log(Level.SEVERE, "getPluginResource(pluginName='" + pluginName +
								"', '" + internalPath + "'): file does not exists " + f.getAbsolutePath());
				f = null;
			}
		} catch (Exception e)
		{
			logger.log(Level.SEVERE, "getPluginResource(pluginName='" + pluginName +
							"', '" + internalPath + "')", e);
		}
		
		return f;
	}
}
