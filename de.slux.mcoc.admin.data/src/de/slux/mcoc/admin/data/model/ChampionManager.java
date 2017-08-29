/**
 * 
 */
package de.slux.mcoc.admin.data.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * @author slux
 *
 */
public class ChampionManager
{
    public static final String CHAMPIONS_DATA_JSON_URL = "https://api.github.com/repos/hook/champions/contents/src/images/champions";
    public static final String CHAMPIONS_NAMES_JSON_URL = "https://raw.githubusercontent.com/hook/champions/master/src/data/lang/en.json";
    public static final String CHAMPIONS_INFO_JSON_URL = "https://raw.githubusercontent.com/hook/champions/master/src/data/ids/champions.js";
    public static final String CHAMPIONS_DB = "data/champions.dat";
    public static final String CHAMPIONS_IMG_DIR = "data/images/champions";

    public static final String BUNDLE_ID = "de.slux.mcoc.admin.data";

    private Map<String, Champion> championData;

    /**
     * Constructor
     * 
     * @throws Exception
     */
    public ChampionManager() throws Exception {
        championData = deserializeData();
    }

    private Map<String, Champion> deserializeData() throws Exception
    {
        ObjectInputStream objectinputstream = null;
        try
        {
            URL url = new URL("platform:/plugin/" + BUNDLE_ID + "/" + CHAMPIONS_DB);
            InputStream inputStream = url.openConnection().getInputStream();

            objectinputstream = new ObjectInputStream(inputStream);
            @SuppressWarnings("unchecked")
            Map<String, Champion> champData = (Map<String, Champion>) objectinputstream.readObject();

            return champData;
        }
        finally
        {
            if (objectinputstream != null)
            {
                objectinputstream.close();
            }
        }
    }

    /**
     * @return the championData
     */
    public Map<String, Champion> getChampionData()
    {
        return championData;
    }
}
