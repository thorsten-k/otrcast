
package net.sf.otrcutmp4.model.xml.otr;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.otrcutmp4.model.xml.cut.VideoFile;
import net.sf.otrcutmp4.model.xml.mc.Cover;
import net.sf.otrcutmp4.model.xml.series.Episode;
import net.sf.otrcutmp4.model.xml.series.Season;
import net.sf.otrcutmp4.model.xml.series.Series;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/cut}videoFile"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}series"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}season"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}episode"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/mc}cover"/>
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
    "videoFile",
    "series",
    "season",
    "episode",
    "cover"
})
@XmlRootElement(name = "query")
public class Query
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/cut", required = true)
    protected VideoFile videoFile;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected Series series;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected Season season;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected Episode episode;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/mc", required = true)
    protected Cover cover;

    /**
     * Gets the value of the videoFile property.
     * 
     * @return
     *     possible object is
     *     {@link VideoFile }
     *     
     */
    public VideoFile getVideoFile() {
        return videoFile;
    }

    /**
     * Sets the value of the videoFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link VideoFile }
     *     
     */
    public void setVideoFile(VideoFile value) {
        this.videoFile = value;
    }

    public boolean isSetVideoFile() {
        return (this.videoFile!= null);
    }

    /**
     * Gets the value of the series property.
     * 
     * @return
     *     possible object is
     *     {@link Series }
     *     
     */
    public Series getSeries() {
        return series;
    }

    /**
     * Sets the value of the series property.
     * 
     * @param value
     *     allowed object is
     *     {@link Series }
     *     
     */
    public void setSeries(Series value) {
        this.series = value;
    }

    public boolean isSetSeries() {
        return (this.series!= null);
    }

    /**
     * Gets the value of the season property.
     * 
     * @return
     *     possible object is
     *     {@link Season }
     *     
     */
    public Season getSeason() {
        return season;
    }

    /**
     * Sets the value of the season property.
     * 
     * @param value
     *     allowed object is
     *     {@link Season }
     *     
     */
    public void setSeason(Season value) {
        this.season = value;
    }

    public boolean isSetSeason() {
        return (this.season!= null);
    }

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
     * Gets the value of the cover property.
     * 
     * @return
     *     possible object is
     *     {@link Cover }
     *     
     */
    public Cover getCover() {
        return cover;
    }

    /**
     * Sets the value of the cover property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cover }
     *     
     */
    public void setCover(Cover value) {
        this.cover = value;
    }

    public boolean isSetCover() {
        return (this.cover!= null);
    }

}
