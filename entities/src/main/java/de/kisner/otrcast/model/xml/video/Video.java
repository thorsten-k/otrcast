
package de.kisner.otrcast.model.xml.video;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import de.kisner.otrcast.model.xml.video.tv.Episode;
import de.kisner.otrcast.model.xml.video.tv.Movie;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}episode"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}movie"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}videoFiles"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/video}file"/&gt;
 *         &lt;element ref="{http://otrcutmp4.sf.net/video}tag"/&gt;
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
    "episode",
    "movie",
    "videoFiles",
    "file",
    "tag"
})
@XmlRootElement(name = "video")
public class Video
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected Episode episode;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected Movie movie;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/cut", required = true)
    protected VideoFiles videoFiles;
    @XmlElement(required = true)
    protected File file;
    @XmlElement(required = true)
    protected Tag tag;

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
     * Gets the value of the movie property.
     * 
     * @return
     *     possible object is
     *     {@link Movie }
     *     
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Sets the value of the movie property.
     * 
     * @param value
     *     allowed object is
     *     {@link Movie }
     *     
     */
    public void setMovie(Movie value) {
        this.movie = value;
    }

    public boolean isSetMovie() {
        return (this.movie!= null);
    }

    /**
     * Gets the value of the videoFiles property.
     * 
     * @return
     *     possible object is
     *     {@link VideoFiles }
     *     
     */
    public VideoFiles getVideoFiles() {
        return videoFiles;
    }

    /**
     * Sets the value of the videoFiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link VideoFiles }
     *     
     */
    public void setVideoFiles(VideoFiles value) {
        this.videoFiles = value;
    }

    public boolean isSetVideoFiles() {
        return (this.videoFiles!= null);
    }

    /**
     * Gets the value of the file property.
     * 
     * @return
     *     possible object is
     *     {@link File }
     *     
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets the value of the file property.
     * 
     * @param value
     *     allowed object is
     *     {@link File }
     *     
     */
    public void setFile(File value) {
        this.file = value;
    }

    public boolean isSetFile() {
        return (this.file!= null);
    }

    /**
     * Gets the value of the tag property.
     * 
     * @return
     *     possible object is
     *     {@link Tag }
     *     
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tag }
     *     
     */
    public void setTag(Tag value) {
        this.tag = value;
    }

    public boolean isSetTag() {
        return (this.tag!= null);
    }

}
