/*
 * Test1.java
 */

import rit.ldom.*;

import java.util.ArrayList;
import java.util.List;

/**
 * "Hello world" document generator.
 * @author James Heliotis
 * @version $Revision: 1.8 $
 */
public class Test1 {

    /**
     * Create and display a document that shows, "Hello, world!"
     * 
     * @param args unused
     */
    public static void main( String[] args ) {
        Document doc = test();
        BrowserUtil.render( doc.generateHTML() );
    }

    public static Document test() {
        Document document = new Document( "Test 1" );

        TextSequence sentenceText = new TextSequence();
        Text hello = new SimpleText( "Hello, " );
        sentenceText.addChild( hello );
        Text world = new SimpleText( "world" );
        Text worldBold = new StyledText( Style.bold, world );
        sentenceText.addChild( worldBold );
        Text exclaim = new SimpleText( "!" );
        sentenceText.addChild( exclaim );
        TextSequence poop = new TextSequence();
        Text p1 = new SimpleText( "poop" );
        poop.addChild(p1);
        TextSequence poop2 = new TextSequence();
        Text p2 = new SimpleText( "poop2" );
        poop2.addChild(p2);
        List<TextSequence> pooplst = new ArrayList<>();
        pooplst.add(poop);
        pooplst.add(poop2);
        DocUnit sentence = new Paragraph( 142, sentenceText );
        Listing poopLst = new Listing(144, true, pooplst);

        document.addChild( sentence );
        document.addChild(poopLst);


        System.out.println( document.characterCount() +
                            " non white-space characters in the document text."
        );

        System.out.println( "Replacing \"Hello\" with \"He lo\"." );
        document.replace( "Hello", "bool" );

        /*TextSequence boolio = new TextSequence();
        Text bool = new SimpleText("bool");
        boolio.addChild(bool);
        DocUnit newBool = new Paragraph(20, boolio);
        document.replace(142, newBool);*/

        document.replace("bool", "Hello");


        System.out.println( document.characterCount() +
                            " non white-space characters in the document text."
        );

        BrowserUtil.displayDocTree( document, "" );
        return document;
    }

}
