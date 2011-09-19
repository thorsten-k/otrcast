//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.2-hudson-jaxb-ri-2.2-63- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.19 at 12:34:20 PM MESZ 
//


package net.sf.otrcutmp4.model.xml.otr;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.otrcutmp4.model.xml.cut.Cut;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}cut"/>
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
    "cut"
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
    protected Cut cut;
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
     * Gets the value of the cut property.
     * 
     * @return
     *     possible object is
     *     {@link Cut }
     *     
     */
    public Cut getCut() {
        return cut;
    }

    /**
     * Sets the value of the cut property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cut }
     *     
     */
    public void setCut(Cut value) {
        this.cut = value;
    }

    public boolean isSetCut() {
        return (this.cut!= null);
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
