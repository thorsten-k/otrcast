
package de.kisner.otrcast.model.xml.video.tv;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.kisner.otrcast.model.xml.video.tv package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.kisner.otrcast.model.xml.video.tv
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Episode }
     * 
     */
    public Episode createEpisode() {
        return new Episode();
    }

    /**
     * Create an instance of {@link Season }
     * 
     */
    public Season createSeason() {
        return new Season();
    }

    /**
     * Create an instance of {@link Series }
     * 
     */
    public Series createSeries() {
        return new Series();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link Movie }
     * 
     */
    public Movie createMovie() {
        return new Movie();
    }

    /**
     * Create an instance of {@link Movies }
     * 
     */
    public Movies createMovies() {
        return new Movies();
    }

    /**
     * Create an instance of {@link Tags }
     * 
     */
    public Tags createTags() {
        return new Tags();
    }

    /**
     * Create an instance of {@link Tag }
     * 
     */
    public Tag createTag() {
        return new Tag();
    }

}
