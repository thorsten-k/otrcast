
package net.sf.otrcutmp4.model.xml.otr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}format"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}tv"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}quality" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="otrCl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "format",
    "tv",
    "quality"
})
@XmlRootElement(name = "otrId")
public class OtrId
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Format format;
    @XmlElement(required = true)
    protected Tv tv;
    @XmlElement(required = true)
    protected List<Quality> quality;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "key")
    protected String key;
    @XmlAttribute(name = "otrCl")
    protected String otrCl;

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link Format }
     *     
     */
    public Format getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link Format }
     *     
     */
    public void setFormat(Format value) {
        this.format = value;
    }

    public boolean isSetFormat() {
        return (this.format!= null);
    }

    /**
     * Gets the value of the tv property.
     * 
     * @return
     *     possible object is
     *     {@link Tv }
     *     
     */
    public Tv getTv() {
        return tv;
    }

    /**
     * Sets the value of the tv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tv }
     *     
     */
    public void setTv(Tv value) {
        this.tv = value;
    }

    public boolean isSetTv() {
        return (this.tv!= null);
    }

    /**
     * Gets the value of the quality property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quality property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuality().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Quality }
     * 
     * 
     */
    public List<Quality> getQuality() {
        if (quality == null) {
            quality = new ArrayList<Quality>();
        }
        return this.quality;
    }

    public boolean isSetQuality() {
        return ((this.quality!= null)&&(!this.quality.isEmpty()));
    }

    public void unsetQuality() {
        this.quality = null;
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
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    public boolean isSetKey() {
        return (this.key!= null);
    }

    /**
     * Gets the value of the otrCl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtrCl() {
        return otrCl;
    }

    /**
     * Sets the value of the otrCl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtrCl(String value) {
        this.otrCl = value;
    }

    public boolean isSetOtrCl() {
        return (this.otrCl!= null);
    }

}
