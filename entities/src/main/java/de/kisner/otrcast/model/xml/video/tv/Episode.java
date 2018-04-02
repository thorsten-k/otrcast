
package de.kisner.otrcast.model.xml.video.tv;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import de.kisner.otrcast.model.xml.mc.File;
import de.kisner.otrcast.model.xml.mc.Image;
import de.kisner.otrcast.model.xml.tvdb.Sync;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/tv}season"/&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/mc}image"/&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/mc}file"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/tvdb}sync"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="nr" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "season",
    "image",
    "file",
    "sync"
})
@XmlRootElement(name = "episode")
public class Episode
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Season season;
    @XmlElement(namespace = "http://otrcast.kisner.de/mc", required = true)
    protected Image image;
    @XmlElement(namespace = "http://otrcast.kisner.de/mc", required = true)
    protected File file;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/tvdb", required = true)
    protected Sync sync;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "nr")
    protected Long nr;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the season property.
     * 
     * @return
     *     possible object is
     *     {@link Season }
     *     
     */
    public Season getSeason() {
        return season;
    }

    /**
     * Sets the value of the season property.
     * 
     * @param value
     *     allowed object is
     *     {@link Season }
     *     
     */
    public void setSeason(Season value) {
        this.season = value;
    }

    public boolean isSetSeason() {
        return (this.season!= null);
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link Image }
     *     
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link Image }
     *     
     */
    public void setImage(Image value) {
        this.image = value;
    }

    public boolean isSetImage() {
        return (this.image!= null);
    }

    /**
     * Gets the value of the file property.
     * 
     * @return
     *     possible object is
     *     {@link File }
     *     
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets the value of the file property.
     * 
     * @param value
     *     allowed object is
     *     {@link File }
     *     
     */
    public void setFile(File value) {
        this.file = value;
    }

    public boolean isSetFile() {
        return (this.file!= null);
    }

    /**
     * Gets the value of the sync property.
     * 
     * @return
     *     possible object is
     *     {@link Sync }
     *     
     */
    public Sync getSync() {
        return sync;
    }

    /**
     * Sets the value of the sync property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sync }
     *     
     */
    public void setSync(Sync value) {
        this.sync = value;
    }

    public boolean isSetSync() {
        return (this.sync!= null);
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(long value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    public void unsetId() {
        this.id = null;
    }

    /**
     * Gets the value of the nr property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getNr() {
        return nr;
    }

    /**
     * Sets the value of the nr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNr(long value) {
        this.nr = value;
    }

    public boolean isSetNr() {
        return (this.nr!= null);
    }

    public void unsetNr() {
        this.nr = null;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

}
