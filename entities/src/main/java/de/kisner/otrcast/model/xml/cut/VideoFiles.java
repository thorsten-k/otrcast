
package de.kisner.otrcast.model.xml.cut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}videoFile" maxOccurs="unbounded"/&gt;
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
    "videoFile"
})
@XmlRootElement(name = "videoFiles")
public class VideoFiles
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<VideoFile> videoFile;

    /**
     * Gets the value of the videoFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the videoFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVideoFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VideoFile }
     * 
     * 
     */
    public List<VideoFile> getVideoFile() {
        if (videoFile == null) {
            videoFile = new ArrayList<VideoFile>();
        }
        return this.videoFile;
    }

    public boolean isSetVideoFile() {
        return ((this.videoFile!= null)&&(!this.videoFile.isEmpty()));
    }

    public void unsetVideoFile() {
        this.videoFile = null;
    }

}
