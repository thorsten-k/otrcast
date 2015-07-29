
package de.kisner.otrcast.model.xml.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.kisner.otrcast.model.xml.otr.Format;
import de.kisner.otrcast.model.xml.otr.Quality;
import de.kisner.otrcast.model.xml.series.Category;
import de.kisner.otrcast.model.xml.series.Episode;
import de.kisner.otrcast.model.xml.series.Movie;
import de.kisner.otrcast.model.xml.series.Series;
import de.kisner.otrcast.model.xml.tvdb.Banners;


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
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}episode" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}series" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/tvdb}banners" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}category" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/series}movie" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}format" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://otrcutmp4.sf.net/otr}quality" maxOccurs="unbounded"/>
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
    "series",
    "banners",
    "category",
    "movie",
    "format",
    "quality"
})
@XmlRootElement(name = "otr")
public class Otr
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected List<Episode> episode;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected List<Series> series;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/tvdb", required = true)
    protected List<Banners> banners;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected List<Category> category;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/series", required = true)
    protected List<Movie> movie;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/otr", required = true)
    protected List<Format> format;
    @XmlElement(namespace = "http://otrcutmp4.sf.net/otr", required = true)
    protected List<Quality> quality;

    /**
     * Gets the value of the episode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the episode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEpisode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Episode }
     * 
     * 
     */
    public List<Episode> getEpisode() {
        if (episode == null) {
            episode = new ArrayList<Episode>();
        }
        return this.episode;
    }

    public boolean isSetEpisode() {
        return ((this.episode!= null)&&(!this.episode.isEmpty()));
    }

    public void unsetEpisode() {
        this.episode = null;
    }

    /**
     * Gets the value of the series property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the series property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Series }
     * 
     * 
     */
    public List<Series> getSeries() {
        if (series == null) {
            series = new ArrayList<Series>();
        }
        return this.series;
    }

    public boolean isSetSeries() {
        return ((this.series!= null)&&(!this.series.isEmpty()));
    }

    public void unsetSeries() {
        this.series = null;
    }

    /**
     * Gets the value of the banners property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the banners property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBanners().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Banners }
     * 
     * 
     */
    public List<Banners> getBanners() {
        if (banners == null) {
            banners = new ArrayList<Banners>();
        }
        return this.banners;
    }

    public boolean isSetBanners() {
        return ((this.banners!= null)&&(!this.banners.isEmpty()));
    }

    public void unsetBanners() {
        this.banners = null;
    }

    /**
     * Gets the value of the category property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the category property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Category }
     * 
     * 
     */
    public List<Category> getCategory() {
        if (category == null) {
            category = new ArrayList<Category>();
        }
        return this.category;
    }

    public boolean isSetCategory() {
        return ((this.category!= null)&&(!this.category.isEmpty()));
    }

    public void unsetCategory() {
        this.category = null;
    }

    /**
     * Gets the value of the movie property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the movie property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMovie().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Movie }
     * 
     * 
     */
    public List<Movie> getMovie() {
        if (movie == null) {
            movie = new ArrayList<Movie>();
        }
        return this.movie;
    }

    public boolean isSetMovie() {
        return ((this.movie!= null)&&(!this.movie.isEmpty()));
    }

    public void unsetMovie() {
        this.movie = null;
    }

    /**
     * Gets the value of the format property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the format property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Format }
     * 
     * 
     */
    public List<Format> getFormat() {
        if (format == null) {
            format = new ArrayList<Format>();
        }
        return this.format;
    }

    public boolean isSetFormat() {
        return ((this.format!= null)&&(!this.format.isEmpty()));
    }

    public void unsetFormat() {
        this.format = null;
    }

    /**
     * Gets the value of the quality property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quality property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuality().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Quality }
     * 
     * 
     */
    public List<Quality> getQuality() {
        if (quality == null) {
            quality = new ArrayList<Quality>();
        }
        return this.quality;
    }

    public boolean isSetQuality() {
        return ((this.quality!= null)&&(!this.quality.isEmpty()));
    }

    public void unsetQuality() {
        this.quality = null;
    }

}
