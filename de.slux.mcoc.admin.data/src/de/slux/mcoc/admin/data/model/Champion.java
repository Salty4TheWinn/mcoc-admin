/**
 * 
 */
package de.slux.mcoc.admin.data.model;

import java.io.Serializable;

public class Champion implements Serializable
{
    private static final long serialVersionUID = -2801027615654162753L;

    public enum ChampionClass
    {
        Cosmic, Tech, Mutant, Skill, Science, Mystic, Universal
    }

    private String id;
    private String name;
    private ChampionClass champClass;

    /**
     * Constructor
     * 
     * @param id
     * @param name
     * @param champClass
     */
    public Champion(String id, String name, ChampionClass champClass) {
        super();
        this.id = id;
        this.name = name;
        this.champClass = champClass;
    }

    /**
     * Constructor
     */
    public Champion() {
        super();
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the champClass
     */
    public ChampionClass getChampClass()
    {
        return champClass;
    }

    /**
     * @param champClass
     *            the champClass to set
     */
    public void setChampClass(ChampionClass champClass)
    {
        this.champClass = champClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Champion [id=" + id + ", name=" + name + ", champClass=" + champClass + "]";
    }

}
