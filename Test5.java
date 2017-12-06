import rit.ldom.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Part of LDOM project.
 * Private post-submission test for empty Document.
 * Created 10 2015
 *
 * @author James Heliotis
 */
public class Test5 {

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
        DocUnit p2 = new Paragraph( 2, new TextSequence(){{
            this.addChild( new SimpleText( "Paragraph 2" ) );
        }} );

        doc = new Document( "empty document" );
        System.out.println( "\nEmpty\n==================" );
        BrowserUtil.displayDocTree( doc, "|" );
        System.out.println( doc.characterCount() + " characters" );
        viewResult( true, userIn );

        doc.addChild( p1 );
        System.out.println( "\nOne paragraph\n==================" );
        BrowserUtil.displayDocTree( doc, "|" );
        System.out.println( doc.characterCount() + " characters" );
        viewResult( true, userIn );

        doc.addChild( p2 );
        System.out.println( "\nTwo paragraphs\n==================" );
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
