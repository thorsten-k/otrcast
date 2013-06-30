
package net.sf.otrcutmp4.model.xml.cut;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.sf.otrcutmp4.model.xml.cut package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.sf.otrcutmp4.model.xml.cut
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CutList }
     * 
     */
    public CutList createCutList() {
        return new CutList();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link Comment }
     * 
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link Author }
     * 
     */
    public Author createAuthor() {
        return new Author();
    }

    /**
     * Create an instance of {@link Cut }
     * 
     */
    public Cut createCut() {
        return new Cut();
    }

    /**
     * Create an instance of {@link FileName }
     * 
     */
    public FileName createFileName() {
        return new FileName();
    }

    /**
     * Create an instance of {@link VideoFiles }
     * 
     */
    public VideoFiles createVideoFiles() {
        return new VideoFiles();
    }

    /**
     * Create an instance of {@link VideoFile }
     * 
     */
    public VideoFile createVideoFile() {
        return new VideoFile();
    }

    /**
     * Create an instance of {@link CutLists }
     * 
     */
    public CutLists createCutLists() {
        return new CutLists();
    }

}
