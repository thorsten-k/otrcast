
package net.sf.otrcutmp4.model.xml.mc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="lastRestart" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "serverStatus")
public class ServerStatus
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "lastRestart")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastRestart;

    /**
     * Gets the value of the lastRestart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastRestart() {
        return lastRestart;
    }

    /**
     * Sets the value of the lastRestart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastRestart(XMLGregorianCalendar value) {
        this.lastRestart = value;
    }

    public boolean isSetLastRestart() {
        return (this.lastRestart!= null);
    }

}
