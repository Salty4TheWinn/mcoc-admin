/**
 * 
 */
package de.slux.mcoc.admin.data.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import de.slux.mcoc.admin.data.model.Champion;
import de.slux.mcoc.admin.data.model.Champion.ChampionClass;
import de.slux.mcoc.admin.data.model.ChampionManager;

/**
 *
 */
public class SyncDataTool
{
    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        System.out.println("<I> Getting data.");

        String championsJsonText = getUrlContentText(ChampionManager.CHAMPIONS_NAMES_JSON_URL);
        Gson gson = new Gson();

        Type type = new TypeToken<Map<String, String>>()
        {
        }.getType();

        Map<String, String> champions = gson.fromJson(championsJsonText, type);
        String championsImgJsonText = getUrlContentText(ChampionManager.CHAMPIONS_DATA_JSON_URL);
        String championClassesText = getUrlContentText(ChampionManager.CHAMPIONS_INFO_JSON_URL);
        Object championsData = gson.fromJson(championsImgJsonText, Object.class);

        System.out.println("<I> Got all the data.");

        System.out.println("<I> Extracting chapions VS classes");

        Map<String, String> champClasses = extractChampionClasses(championClassesText);

        System.out.println("<I> Extracted " + champClasses.size() + " champion(s) with classes");

        ArrayList<LinkedTreeMap<String, Object>> data = (ArrayList<LinkedTreeMap<String, Object>>) championsData;
        Map<String, Champion> championsDatabase = new TreeMap<>();

        System.out.println("<I> Downloading data and database creation");

        for (LinkedTreeMap<String, Object> dataItem : data)
        {
            if (((String) dataItem.get("name")).startsWith("portrait_"))
            {
                String champId = ((String) dataItem.get("name")).replace(".png", "").replace("portrait_", "");
                String downloadPortraitUrl = ((String) dataItem.get("download_url"));
                String champName = null;

                // Get the champ full name
                if (champions.containsKey("champion-" + champId + "-name"))
                {
                    champName = champions.get("champion-" + champId + "-name");
                }
                else
                {
                    System.err.println("<E> Missing champion name for ID: " + champId);
                    continue;
                }

                if (!champClasses.containsKey(champId))
                {
                    System.err.println("<E> Missing key " + champId + " in classes map");
                    continue;
                }

                saveUrl2File(downloadPortraitUrl, champId + ".png");

                /*
                 * System.out.println(champId); System.out.println(champName);
                 * System.out.println(downloadPortraitUrl);
                 * System.out.println(champClasses.get(champId));
                 * System.out.println();
                 */

                championsDatabase.put(champId,
                        new Champion(champId, champName, ChampionClass.valueOf(champClasses.get(champId))));

            }
        }

        // Serialise champions database
        System.out.println("<I> Serialising champions database to " + ChampionManager.CHAMPIONS_DB);
        FileOutputStream fout = new FileOutputStream(ChampionManager.CHAMPIONS_DB);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(championsDatabase);
        oos.close();

        System.out.println("<I> All done. Parsed " + championsDatabase.size() + " champion(s)");
    }

    public static String getUrlContentText(String url) throws Exception
    {
        URL theUrl = new URL(url);
        InputStream is = theUrl.openStream();
        int buffer = 0;
        StringBuilder sb = new StringBuilder();
        while ((buffer = is.read()) != -1)
        {
            sb.append((char) buffer);
        }

        return sb.toString();
    }

    public static void saveUrl2File(String url, String outputFileName) throws Exception
    {
        FileOutputStream fos = null;
        try
        {
            URL fileUrl = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(fileUrl.openStream());
            fos = new FileOutputStream(ChampionManager.CHAMPIONS_IMG_DIR + File.separator + outputFileName);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        }
        finally
        {
            if (fos != null)
            {
                fos.close();
            }
        }
    }

    public static Map<String, String> extractChampionClasses(String data)
    {
        Map<String, String> champClasses = new HashMap<>();

        String lines[] = data.split("\\r?\\n");
        String currentClass = null;

        for (String line : lines)
        {
            if (line.startsWith("//"))
            {
                // We found some class
                if (line.contains(ChampionClass.Cosmic.name()))
                {
                    // Cosmic champs next
                    currentClass = ChampionClass.Cosmic.name();
                }
                if (line.contains(ChampionClass.Tech.name()))
                {
                    // Tech champs next
                    currentClass = ChampionClass.Tech.name();
                }
                if (line.contains(ChampionClass.Science.name()))
                {
                    // Science champs next
                    currentClass = ChampionClass.Science.name();
                }
                if (line.contains(ChampionClass.Skill.name()))
                {
                    // Skill champs next
                    currentClass = ChampionClass.Skill.name();
                }
                if (line.contains(ChampionClass.Mystic.name()))
                {
                    // Mystic champs next
                    currentClass = ChampionClass.Mystic.name();
                }
                if (line.contains(ChampionClass.Mutant.name()))
                {
                    // Mutant champs next
                    currentClass = ChampionClass.Mutant.name();
                }
                if (line.contains(ChampionClass.Universal.name()))
                {
                    // Universal champs next
                    currentClass = ChampionClass.Universal.name();
                }

            }
            else
            {
                final String regexChampName = "'([a-z0-9]+)'";
                final Pattern pattern = Pattern.compile(regexChampName);
                final Matcher matcher = pattern.matcher(line);

                if (!matcher.find())
                    System.err.println("Cannot find class for data line: " + line);
                else
                {
                    String champName = matcher.group(1);
                    champClasses.put(champName, currentClass);
                }
            }
        }

        return champClasses;
    }
}