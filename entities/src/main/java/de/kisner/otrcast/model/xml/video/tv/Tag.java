
package de.kisner.otrcast.model.xml.video.tv;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import de.kisner.otrcast.model.xml.otr.OtrId;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}otrId"/&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/tv}episode"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="withBegin" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="withEnd" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "otrId",
    "episode"
})
@XmlRootElement(name = "tag")
public class Tag
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/otr", required = true)
    protected OtrId otrId;
    @XmlElement(required = true)
    protected Episode episode;
    @XmlAttribute(name = "withBegin")
    protected Boolean withBegin;
    @XmlAttribute(name = "withEnd")
    protected Boolean withEnd;

    /**
     * Gets the value of the otrId property.
     * 
     * @return
     *     possible object is
     *     {@link OtrId }
     *     
     */
    public OtrId getOtrId() {
        return otrId;
    }

    /**
     * Sets the value of the otrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtrId }
     *     
     */
    public void setOtrId(OtrId value) {
        this.otrId = value;
    }

    public boolean isSetOtrId() {
        return (this.otrId!= null);
    }

    /**
     * Gets the value of the episode property.
     * 
     * @return
     *     possible object is
     *     {@link Episode }
     *     
     */
    public Episode getEpisode() {
        return episode;
    }

    /**
     * Sets the value of the episode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Episode }
     *     
     */
    public void setEpisode(Episode value) {
        this.episode = value;
    }

    public boolean isSetEpisode() {
        return (this.episode!= null);
    }

    /**
     * Gets the value of the withBegin property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isWithBegin() {
        return withBegin;
    }

    /**
     * Sets the value of the withBegin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWithBegin(boolean value) {
        this.withBegin = value;
    }

    public boolean isSetWithBegin() {
        return (this.withBegin!= null);
    }

    public void unsetWithBegin() {
        this.withBegin = null;
    }

    /**
     * Gets the value of the withEnd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isWithEnd() {
        return withEnd;
    }

    /**
     * Sets the value of the withEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWithEnd(boolean value) {
        this.withEnd = value;
    }

    public boolean isSetWithEnd() {
        return (this.withEnd!= null);
    }

    public void unsetWithEnd() {
        this.withEnd = null;
    }

}
