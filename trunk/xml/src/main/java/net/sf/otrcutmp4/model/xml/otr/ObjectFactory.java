
package net.sf.otrcutmp4.model.xml.otr;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.sf.otrcutmp4.model.xml.otr package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.sf.otrcutmp4.model.xml.otr
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Format }
     * 
     */
    public Format createFormat() {
        return new Format();
    }

    /**
     * Create an instance of {@link Quality }
     * 
     */
    public Quality createQuality() {
        return new Quality();
    }

    /**
     * Create an instance of {@link Recording }
     * 
     */
    public Recording createRecording() {
        return new Recording();
    }

    /**
     * Create an instance of {@link OtrId }
     * 
     */
    public OtrId createOtrId() {
        return new OtrId();
    }

    /**
     * Create an instance of {@link Tv }
     * 
     */
    public Tv createTv() {
        return new Tv();
    }

    /**
     * Create an instance of {@link Link }
     * 
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link Query }
     * 
     */
    public Query createQuery() {
        return new Query();
    }

    /**
     * Create an instance of {@link Linklist }
     * 
     */
    public Linklist createLinklist() {
        return new Linklist();
    }

    /**
     * Create an instance of {@link Download }
     * 
     */
    public Download createDownload() {
        return new Download();
    }

}
