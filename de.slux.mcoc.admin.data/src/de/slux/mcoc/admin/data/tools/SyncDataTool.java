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
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import de.slux.mcoc.admin.data.model.Boost;
import de.slux.mcoc.admin.data.model.Champion;
import de.slux.mcoc.admin.data.model.Champion.ChampionClass;
import de.slux.mcoc.admin.data.model.McocDataManager;

/**
 * @author Slux
 */
public class SyncDataTool
{
    // TODO: deal with boosts and map using the following
    // http://mcoc.dyndns.org/aw/js/aw_s2_advanced_9path.json
    // http://mcoc.dyndns.org/global/ui/js/booster.js

    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        System.out.println("<I> Getting data.");

        String championsJsonText = getUrlContentText(McocDataManager.CHAMPIONS_NAMES_JSON_URL);
        Gson gson = new Gson();

        // We retrieve the node boost list
        System.out.println("<I> Extracting boost node data");
        String boostsJsonText = getUrlContentText(McocDataManager.BOOSTS_NODES_URL);
        // Cleanup
        boostsJsonText = boostsJsonText.replace("var booster = ", "");

        Object boostsData = gson.fromJson(boostsJsonText, Object.class);

        LinkedTreeMap<String, Object> boosts = (LinkedTreeMap<String, Object>) boostsData;
        Map<String, Boost> boostModel = new HashMap<>();
        for (Entry<String, Object> entry : boosts.entrySet())
        {
            String boostId = entry.getKey();
            LinkedTreeMap<String, String> entryDetails = (LinkedTreeMap<String, String>) entry.getValue();
            String boostImage = entryDetails.get("img");
            String boostTitle = entryDetails.get("title");
            String boostDescription = entryDetails.get("text");

            System.out.println("ID=" + boostId + "(" + boostImage + ")");
            System.out.println("    TITLE=" + boostTitle);
            System.out.println("    TEXT= " + boostDescription);
            System.out.println();
            boostModel.put(boostId, new Boost(boostId, boostImage, boostTitle, boostDescription));

            // Download the boost icon
            // http://mcoc.dyndns.org/global/ui/images/booster/
            try
            {
                saveUrl2File(McocDataManager.BOOST_IMAGES_BASE_DIR_URL + boostImage + ".png", boostImage + ".png",
                        McocDataManager.BOOSTS_IMG_DIR);
            }
            catch (Exception e)
            {
                System.err.println("<E> Boost image not found for ID " + boostImage);
            }

        }

        // Serialise champions database
        System.out.println("<I> Serialising boost database to " + McocDataManager.BOOSTS_LIST_DB);
        FileOutputStream fout = new FileOutputStream(McocDataManager.BOOSTS_LIST_DB);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(boostModel);
        oos.close();

        System.out.println("<I> Boost node data extracted.");

        if (1 == 1)
            System.exit(0);
        Type type = new TypeToken<Map<String, String>>()
        {
        }.getType();

        Map<String, String> champions = gson.fromJson(championsJsonText, type);
        String championsImgJsonText = getUrlContentText(McocDataManager.CHAMPIONS_DATA_JSON_URL);
        String championClassesText = getUrlContentText(McocDataManager.CHAMPIONS_INFO_JSON_URL);
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

                saveUrl2File(downloadPortraitUrl, champId + ".png", McocDataManager.CHAMPIONS_IMG_DIR);

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
        System.out.println("<I> Serialising champions database to " + McocDataManager.CHAMPIONS_DB);
        fout = new FileOutputStream(McocDataManager.CHAMPIONS_DB);
        oos = new ObjectOutputStream(fout);
        oos.writeObject(championsDatabase);
        oos.close();

        System.out.println("<I> Champion database built done. Parsed " + championsDatabase.size() + " champion(s)");
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

    public static void saveUrl2File(String url, String outputFileName, String baseFolder) throws Exception
    {
        FileOutputStream fos = null;
        try
        {
            URL fileUrl = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(fileUrl.openStream());
            fos = new FileOutputStream(baseFolder + File.separator + outputFileName);
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