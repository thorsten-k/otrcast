
package de.kisner.otrcast.model.xml.otr;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}recording" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}otrId" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "recording",
    "otrId"
})
@XmlRootElement(name = "download")
public class Download
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Recording> recording;
    @XmlElement(required = true)
    protected List<OtrId> otrId;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "type")
    protected String type;

    /**
     * Gets the value of the recording property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recording property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecording().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Recording }
     * 
     * 
     */
    public List<Recording> getRecording() {
        if (recording == null) {
            recording = new ArrayList<Recording>();
        }
        return this.recording;
    }

    public boolean isSetRecording() {
        return ((this.recording!= null)&&(!this.recording.isEmpty()));
    }

    public void unsetRecording() {
        this.recording = null;
    }

    /**
     * Gets the value of the otrId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otrId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtrId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtrId }
     * 
     * 
     */
    public List<OtrId> getOtrId() {
        if (otrId == null) {
            otrId = new ArrayList<OtrId>();
        }
        return this.otrId;
    }

    public boolean isSetOtrId() {
        return ((this.otrId!= null)&&(!this.otrId.isEmpty()));
    }

    public void unsetOtrId() {
        this.otrId = null;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    public boolean isSetType() {
        return (this.type!= null);
    }

}
