/**
 * 
 */
package de.slux.mcoc.admin.data.model;

import java.io.Serializable;

/**
 * @author Slux
 */
public class Boost implements Serializable
{
    private static final long serialVersionUID = 8200031240181381802L;

    private String boostId;
    private String imageId;
    private String title;
    private String description;

    /**
     * @param boostId
     * @param imageId
     * @param title
     * @param description
     */
    public Boost(String boostId, String imageId, String title, String description) {
        super();
        this.boostId = boostId;
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    /**
     * @return the boostId
     */
    public String getBoostId()
    {
        return boostId;
    }

    /**
     * @param boostId
     *            the boostId to set
     */
    public void setBoostId(String boostId)
    {
        this.boostId = boostId;
    }

    /**
     * @return the imageId
     */
    public String getImageId()
    {
        return imageId;
    }

    /**
     * @param imageId
     *            the imageId to set
     */
    public void setImageId(String imageId)
    {
        this.imageId = imageId;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

}
