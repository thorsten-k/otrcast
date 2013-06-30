
package net.sf.otrcutmp4.model.xml.series;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.otrcutmp4.model.xml.cut.VideoFiles;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}episode"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}movie"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}videoFiles"/>
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
    "episode",
    "movie",
    "videoFiles"
})
@XmlRootElement(name = "video")
public class Video
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Episode episode;
    @XmlElement(required = true)
    protected Movie movie;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/cut", required = true)
    protected VideoFiles videoFiles;

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

}
