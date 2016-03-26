
package de.kisner.otrcast.model.xml.cut;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}fileName"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}cutList"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}cutLists"/&gt;
 *       &lt;/sequence&gt;
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
    "fileName",
    "cutList",
    "cutLists"
})
@XmlRootElement(name = "videoFile")
public class VideoFile
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/otr", required = true)
    protected OtrId otrId;
    @XmlElement(required = true)
    protected FileName fileName;
    @XmlElement(required = true)
    protected CutList cutList;
    @XmlElement(required = true)
    protected CutLists cutLists;

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
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link FileName }
     *     
     */
    public FileName getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileName }
     *     
     */
    public void setFileName(FileName value) {
        this.fileName = value;
    }

    public boolean isSetFileName() {
        return (this.fileName!= null);
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
     * Gets the value of the cutLists property.
     * 
     * @return
     *     possible object is
     *     {@link CutLists }
     *     
     */
    public CutLists getCutLists() {
        return cutLists;
    }

    /**
     * Sets the value of the cutLists property.
     * 
     * @param value
     *     allowed object is
     *     {@link CutLists }
     *     
     */
    public void setCutLists(CutLists value) {
        this.cutLists = value;
    }

    public boolean isSetCutLists() {
        return (this.cutLists!= null);
    }

}
