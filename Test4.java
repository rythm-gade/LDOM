import rit.ldom.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Part of LDOM project.
 * Private post-submission test for DocUnit manipulations.
 * Created 10 2015
 *
 * @author James Heliotis
 */
public class Test4 {

    private static Document doc = null;
    private static boolean browser = false;

    public static void main( String[] args ) {
        browser = args.length > 0;
        Scanner userIn = null;
        if ( browser ) {
            userIn = new Scanner( System.in );
        }

        DocUnit p1 = new Paragraph( 1, new TextSequence(){{
            this.addChild( new SimpleText( "Paragraph 1" ) );
        }} );
        DocUnit p3 = new Paragraph( 3, new TextSequence(){{
            this.addChild( new SimpleText( "Paragraph 3" ) );
        }} );
        DocUnit p5 = new Paragraph( 5, new TextSequence(){{
            this.addChild( new SimpleText( "Paragraph 5" ) );
        }} );
        DocUnit h2 = new Header( 2, 3, new TextSequence(){{
            this.addChild( new SimpleText( "Header 2" ) );
        }} );
        DocUnit h4 = new Header( 4, 3, new TextSequence(){{
            this.addChild( new SimpleText( "Header 4" ) );
        }} );
        DocUnit h6 = new Header( 6, 3, new TextSequence(){{
            this.addChild( new SimpleText( "Header 6" ) );
        }} );

        doc = new Document( "switcheroo" );
        for ( DocUnit du: new DocUnit[]{ p1, h2, p3 } ) {
            doc.addChild( du );
        }
        System.out.println( "\nOriginal\n==================" );
        BrowserUtil.displayDocTree( doc, "|" );
        System.out.println( doc.characterCount() + " characters" );
        viewResult( true, userIn );

        doc.replace( 1, h4 );
        System.out.println( "\nReplace paragraph 1\n==================" );
        BrowserUtil.displayDocTree( doc, "|" );
        System.out.println( doc.characterCount() + " characters" );
        viewResult( true, userIn );

        doc.replace( 2, p5 );
        System.out.println( "\nReplace header 2\n==================" );
        BrowserUtil.displayDocTree( doc, "|" );
        System.out.println( doc.characterCount() + " characters" );
        viewResult( true, userIn );

        doc.replace( 3, h6 );
        System.out.println( "\nReplace paragraph 3\n==================" );
        BrowserUtil.displayDocTree( doc, "|" );
        System.out.println( doc.characterCount() + " characters" );
        viewResult( false, userIn );

        if ( userIn != null ) userIn.close();
    }

    private static void viewResult( boolean postPrompt, Scanner userIn ) {
        if ( browser ) {
            BrowserUtil.render( doc.generateHTML() );
            if ( postPrompt ) {
                System.out.print( "Hit enter to continue:" );
                userIn.nextLine();
            }
        }
    }

}
