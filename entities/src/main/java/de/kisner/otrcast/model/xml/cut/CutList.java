
package de.kisner.otrcast.model.xml.cut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import de.kisner.otrcast.model.xml.video.Video;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}name"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}comment"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}author"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}cut" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}fileName"/&gt;
 *         &lt;element ref="{http://otrcast.kisner.de/video}video" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="rating" type="{http://www.w3.org/2001/XMLSchema}double" /&gt;
 *       &lt;attribute name="ratingCount" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "comment",
    "author",
    "cut",
    "fileName",
    "video"
})
@XmlRootElement(name = "cutList")
public class CutList
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Name name;
    @XmlElement(required = true)
    protected Comment comment;
    @XmlElement(required = true)
    protected Author author;
    @XmlElement(required = true)
    protected List<Cut> cut;
    @XmlElement(required = true)
    protected FileName fileName;
    @XmlElement(namespace = "http://otrcast.kisner.de/video", required = true)
    protected List<Video> video;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "rating")
    protected Double rating;
    @XmlAttribute(name = "ratingCount")
    protected Integer ratingCount;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    public boolean isSetName() {
        return (this.name!= null);
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link Comment }
     *     
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comment }
     *     
     */
    public void setComment(Comment value) {
        this.comment = value;
    }

    public boolean isSetComment() {
        return (this.comment!= null);
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link Author }
     *     
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link Author }
     *     
     */
    public void setAuthor(Author value) {
        this.author = value;
    }

    public boolean isSetAuthor() {
        return (this.author!= null);
    }

    /**
     * Gets the value of the cut property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cut property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCut().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cut }
     * 
     * 
     */
    public List<Cut> getCut() {
        if (cut == null) {
            cut = new ArrayList<Cut>();
        }
        return this.cut;
    }

    public boolean isSetCut() {
        return ((this.cut!= null)&&(!this.cut.isEmpty()));
    }

    public void unsetCut() {
        this.cut = null;
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
     * Gets the value of the video property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the video property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVideo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Video }
     * 
     * 
     */
    public List<Video> getVideo() {
        if (video == null) {
            video = new ArrayList<Video>();
        }
        return this.video;
    }

    public boolean isSetVideo() {
        return ((this.video!= null)&&(!this.video.isEmpty()));
    }

    public void unsetVideo() {
        this.video = null;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    /**
     * Gets the value of the rating property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRating(double value) {
        this.rating = value;
    }

    public boolean isSetRating() {
        return (this.rating!= null);
    }

    public void unsetRating() {
        this.rating = null;
    }

    /**
     * Gets the value of the ratingCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getRatingCount() {
        return ratingCount;
    }

    /**
     * Sets the value of the ratingCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRatingCount(int value) {
        this.ratingCount = value;
    }

    public boolean isSetRatingCount() {
        return (this.ratingCount!= null);
    }

    public void unsetRatingCount() {
        this.ratingCount = null;
    }

}
