
package net.sf.otrcutmp4.model.xml.otr;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.otrcutmp4.model.xml.cut.CutList;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}otrId"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}format"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}link"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}cutList"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "otrId",
    "format",
    "link",
    "cutList"
})
@XmlRootElement(name = "recording")
public class Recording
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected OtrId otrId;
    @XmlElement(required = true)
    protected Format format;
    @XmlElement(required = true)
    protected Link link;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/cut", required = true)
    protected CutList cutList;
    @XmlAttribute(name = "id")
    protected Long id;

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
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link Link }
     *     
     */
    public Link getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link Link }
     *     
     */
    public void setLink(Link value) {
        this.link = value;
    }

    public boolean isSetLink() {
        return (this.link!= null);
    }

    /**
     * Gets the value of the cutList property.
     * 
     * @return
     *     possible object is
     *     {@link CutList }
     *     
     */
    public CutList getCutList() {
        return cutList;
    }

    /**
     * Sets the value of the cutList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CutList }
     *     
     */
    public void setCutList(CutList value) {
        this.cutList = value;
    }

    public boolean isSetCutList() {
        return (this.cutList!= null);
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

}
