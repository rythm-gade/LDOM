import rit.ldom.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Part of LDOM project.
 * Private post-submission test for all document elements.
 * Created 10 2015
 *
 * @author James Heliotis
 */
public class Test3 {
    private static SimpleText space = new SimpleText( " " );
    private static Document doc;
    private static Header head;
    private static Paragraph para;
    private static Listing lst1;
    private static Listing lst2;
    private static boolean browser = false;

    public static void main( String[] args ) {
        browser = args.length > 0;
        Scanner userIn = null;
        if ( browser ) {
            userIn = new Scanner( System.in );
        }
        makeParagraph();
        makeHeader();
        makeListings();
        doc = new Document( "Project Instructions" );
        doc.addChild( head );
        doc.addChild( para );
        doc.addChild(
                new Header( 21, 2, new TextSequence(){{
                    this.addChild( new SimpleText( "How to do it:" ) );
                }} )
        );
        doc.addChild( lst1 );
        doc.addChild(
                new Header( 21, 2, new TextSequence(){{
                    this.addChild( new SimpleText( "How NOT to do it:" ) );
                }} )
        );
        doc.addChild( lst2 );

        BrowserUtil.displayDocTree( doc, "|" );
        System.out.println( doc.characterCount() + " characters" );
        viewResult( true, userIn );

        doc.replace( "project", "whole project" );
        System.out.println( "\nAfter Modification\n==================" );
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

    private static void makeParagraph() {
        Text simple = new SimpleText( "simple" );
        Text bold = new StyledText( Style.bold, new SimpleText( "bold" ) );
        Text italics =
                new StyledText( Style.italic, new SimpleText( "italic" ) );
        Text underlined = new StyledText(
                Style.underline, new SimpleText( "underlined" )
        );
        Text boldItalics =
            new StyledText( Style.bold,
                            new StyledText( Style.italic,
                                            new SimpleText( "boldItalics" )
                            )
            );
        Text underlinedBold =
            new StyledText( Style.underline,
                            new StyledText( Style.bold,
                                            new SimpleText( "underlinedBold" )
                            )
            );
        Text italBoldUnder =
            new StyledText( Style.italic,
                    new StyledText( Style.bold,
                            new StyledText( Style.underline,
                                    new SimpleText( "italBoldUnder" )
                            )
                    )
            );
        TextSequence paraSeq = new TextSequence();
        for ( Text t: new Text[]{
                simple, bold, italics, underlined, boldItalics,
                underlinedBold, italBoldUnder }
        ) {
            paraSeq.addChild( t );
        }
        para = new Paragraph( 20, paraSeq );
    }

    private static void makeHeader() {
        Text prefix = new StyledText( Style.bold, new SimpleText( "An" ) );
        Text suffix = new StyledText( Style.bold, new SimpleText( "Header" ) );
        Text underlined = new StyledText(
                Style.underline,
                new StyledText( Style.italic, new SimpleText( "Italicized" ) )
        );
        TextSequence headerSeq = new TextSequence();
        Arrays.stream( new Text[]{ prefix, space, underlined, space, suffix } )
                .forEach( headerSeq::addChild );
        head = new Header( 10, 1, headerSeq );
    }

    private static void makeListings() {
        Text pre1 = new StyledText(
                Style.italic,
                new StyledText( Style.underline, new SimpleText( "Read" ) )
        );
        Text pre2 = new StyledText( Style.bold, new SimpleText( "Plan" ) );
        Text pre3 = new SimpleText( "Implement" );
        Text object = new SimpleText( "the project." );
        TextSequence step1 = new TextSequence();
        step1.addChild( pre1 );
        step1.addChild( space );
        step1.addChild( object );
        TextSequence step2 = new TextSequence();
        step2.addChild( pre2 );
        step2.addChild( space );
        step2.addChild( object );
        TextSequence step3 = new TextSequence();
        step3.addChild( pre3 );
        step3.addChild( space );
        step3.addChild( object );
        lst1 = new Listing(
                30, true, Arrays.asList( step1, step2, step3 ) );
        object = new SimpleText( "Implement the project." );
        step1 = new TextSequence();
        step1.addChild( object );
        lst2 = new Listing( 30, false, Collections.singletonList( step1 ) );
    }
}
