//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.14 at 11:57:07 AM MESZ 
//


package net.sf.otrcutmp4.data.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://otr.hekit.de}avi"/>
 *         &lt;element ref="{http://otr.hekit.de}cutListsAvailable"/>
 *         &lt;element ref="{http://otr.hekit.de}cutListsSelected"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "avi",
    "cutListsAvailable",
    "cutListsSelected"
})
@XmlRootElement(name = "videoFile")
public class VideoFile
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Avi avi;
    @XmlElement(required = true)
    protected CutListsAvailable cutListsAvailable;
    @XmlElement(required = true)
    protected CutListsSelected cutListsSelected;

    /**
     * Gets the value of the avi property.
     * 
     * @return
     *     possible object is
     *     {@link Avi }
     *     
     */
    public Avi getAvi() {
        return avi;
    }

    /**
     * Sets the value of the avi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Avi }
     *     
     */
    public void setAvi(Avi value) {
        this.avi = value;
    }

    public boolean isSetAvi() {
        return (this.avi!= null);
    }

    /**
     * Gets the value of the cutListsAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link CutListsAvailable }
     *     
     */
    public CutListsAvailable getCutListsAvailable() {
        return cutListsAvailable;
    }

    /**
     * Sets the value of the cutListsAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link CutListsAvailable }
     *     
     */
    public void setCutListsAvailable(CutListsAvailable value) {
        this.cutListsAvailable = value;
    }

    public boolean isSetCutListsAvailable() {
        return (this.cutListsAvailable!= null);
    }

    /**
     * Gets the value of the cutListsSelected property.
     * 
     * @return
     *     possible object is
     *     {@link CutListsSelected }
     *     
     */
    public CutListsSelected getCutListsSelected() {
        return cutListsSelected;
    }

    /**
     * Sets the value of the cutListsSelected property.
     * 
     * @param value
     *     allowed object is
     *     {@link CutListsSelected }
     *     
     */
    public void setCutListsSelected(CutListsSelected value) {
        this.cutListsSelected = value;
    }

    public boolean isSetCutListsSelected() {
        return (this.cutListsSelected!= null);
    }

}
