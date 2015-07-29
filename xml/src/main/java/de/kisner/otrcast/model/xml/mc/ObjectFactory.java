
package de.kisner.otrcast.model.xml.mc;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.sf.otrcutmp4.model.xml.mc package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.sf.otrcutmp4.model.xml.mc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Image }
     * 
     */
    public Image createImage() {
        return new Image();
    }

    /**
     * Create an instance of {@link Storage }
     * 
     */
    public Storage createStorage() {
        return new Storage();
    }

    /**
     * Create an instance of {@link ServerStatus }
     * 
     */
    public ServerStatus createServerStatus() {
        return new ServerStatus();
    }

}
