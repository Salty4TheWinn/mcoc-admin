/**
 * 
 */
package de.slux.mcoc.admin.data.model;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Map;

/**
 * @author slux
 *
 */
public class McocDataManager
{
    public static final String CHAMPIONS_DATA_JSON_URL = "https://api.github.com/repos/hook/champions/contents/src/images/champions";
    public static final String CHAMPIONS_NAMES_JSON_URL = "https://raw.githubusercontent.com/hook/champions/master/src/data/lang/en.json";
    public static final String CHAMPIONS_INFO_JSON_URL = "https://raw.githubusercontent.com/hook/champions/master/src/data/ids/champions.js";
    public static final String CHAMPIONS_DB = "data/champions.dat";
    public static final String CHAMPIONS_IMG_DIR = "data/images/champions";

    public static final String BOOSTS_NODES_URL = "http://mcoc.dyndns.org/global/ui/js/booster.js";
    public static final String BOOST_IMAGES_BASE_DIR_URL = "http://mcoc.dyndns.org/global/ui/images/booster/";
    public static final String BOOSTS_LIST_DB = "data/boosts.dat";
    public static final String BOOSTS_IMG_DIR = "data/images/boosts";

    public static final String BUNDLE_ID = "de.slux.mcoc.admin.data";

    private Map<String, Champion> championData;
    private Map<String, Boost> boostData;

    /**
     * Constructor
     * 
     * @throws Exception
     */
    public McocDataManager() throws Exception {
        championData = deserializeChampionData();
        boostData = deserializeBoostData();
    }

    private Map<String, Champion> deserializeChampionData() throws Exception
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
    
    private Map<String, Boost> deserializeBoostData() throws Exception
    {
        ObjectInputStream objectinputstream = null;
        try
        {
            URL url = new URL("platform:/plugin/" + BUNDLE_ID + "/" + BOOSTS_LIST_DB);
            InputStream inputStream = url.openConnection().getInputStream();

            objectinputstream = new ObjectInputStream(inputStream);
            @SuppressWarnings("unchecked")
            Map<String, Boost> boostData = (Map<String, Boost>) objectinputstream.readObject();

            return boostData;
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

    /**
     * @return the boostData
     */
    public Map<String, Boost> getBoostData()
    {
        return boostData;
    }

}
