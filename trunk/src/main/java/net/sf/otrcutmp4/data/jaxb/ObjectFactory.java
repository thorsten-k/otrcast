//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.14 at 12:47:46 PM MESZ 
//


package net.sf.otrcutmp4.data.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.hekit.otr.data.jaxb package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.hekit.otr.data.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FileName }
     * 
     */
    public FileName createFileName() {
        return new FileName();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link VideoFiles }
     * 
     */
    public VideoFiles createVideoFiles() {
        return new VideoFiles();
    }

    /**
     * Create an instance of {@link Comment }
     * 
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link CutListsAvailable }
     * 
     */
    public CutListsAvailable createCutListsAvailable() {
        return new CutListsAvailable();
    }

    /**
     * Create an instance of {@link CutListsSelected }
     * 
     */
    public CutListsSelected createCutListsSelected() {
        return new CutListsSelected();
    }

    /**
     * Create an instance of {@link CutList }
     * 
     */
    public CutList createCutList() {
        return new CutList();
    }

    /**
     * Create an instance of {@link Avi }
     * 
     */
    public Avi createAvi() {
        return new Avi();
    }

    /**
     * Create an instance of {@link Author }
     * 
     */
    public Author createAuthor() {
        return new Author();
    }

    /**
     * Create an instance of {@link VideoFile }
     * 
     */
    public VideoFile createVideoFile() {
        return new VideoFile();
    }

    /**
     * Create an instance of {@link Cut }
     * 
     */
    public Cut createCut() {
        return new Cut();
    }

}