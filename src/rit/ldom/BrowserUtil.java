package rit.ldom;

import javax.print.Doc;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Utilities for displaying data in a web browser
 * 
 * @author James Heliotis
 * @version $Id: BrowserUtil.java,v 1.5 2015/10/09 18:05:47 csci142 Exp $
 *
 */
public class BrowserUtil {

    public static String defaultFileName = "";
    private static boolean fileDirFail = false;

    /**
     * WARNING: change the default target file name if it will override
     * a file of importance to you!
     */
    static {
        try {
            defaultFileName = System.getProperty( "user.home" );
        }
        catch( Exception e ) {
            fileDirFail = true;
        }
        defaultFileName += File.separatorChar + "temp.html";
    }

    public static final String HTML_HEADER =
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"" +
            "\"http://www.w3.org/TR/html4/strict.dtd\">\n";

    /**
     * This class should not be instantiated.
     * It contains static utility functions.
     */
    private BrowserUtil() {}

    /**
     * Save the provided string as the entire contents of a file, then
     * have this desktop's default browser display the file as HTML.
     * 
     * @param html the string containing the HTML text.
     * 
     * Preconditions:
     *   The string should contain legal HTML.
     *   The string's outermost tag should be
     *             "&lt;html&gt;...&lt;/html&gt;".
     */
    public static void render( String html ) {
        if ( fileDirFail ) {
            System.err.println( "Warning: creating temp.html in this dir!" );
        }
        if ( Desktop.isDesktopSupported() ) {
            try {
                FileWriter fw = new FileWriter( defaultFileName );
                fw.write( HTML_HEADER );
                fw.write( html );
                fw.close();
            }
            catch ( IOException ioe ) {
                System.err.println( ioe );
                ioe.printStackTrace();
            }
            Desktop dt = Desktop.getDesktop();
            URI uri;
            try {
                uri = new File( defaultFileName ).toURI();
                dt.browse( uri );
            }
            catch ( IOException e ) {
                System.err.println(
                        "Error: cannot open file://" + defaultFileName + '.'
                      );
            }
        }
        else {
            System.err.println(
              "Error: unable to open browser in this environment."
            );
        }
    }

    private static Class< ? > C_DOCUMENT = Document.class;
    private static Class< ? > C_SIMPLEDOCUNIT = SimpleDocUnit.class;
    private static Class< ? > C_LISTING = Listing.class;
    private static Class< ? > C_TEXTSEQUENCE = TextSequence.class;
    private static Class< ? > C_STYLEDTEXT = StyledText.class;
    private static Class< ? > C_SIMPLETEXT = SimpleText.class;

    /**
     * Print on standard output a multi-line, indented display of the
     * document tree.
     *
     * @param node the root of the subtree
     * @param indent the initial indentation of the parent node's type
     */
    public static void displayDocTree( Component node, String indent ) {
        System.out.println( indent + node.display() );
        Class< ? > nodeType = node.getClass();
        if ( C_DOCUMENT.isAssignableFrom( nodeType ) ) {
            Document doc = (Document)node;
            doc.getChildren().forEach(
                    docUnit -> displayDocTree( docUnit, indent + "  " )
            );
        }
        else if ( C_SIMPLEDOCUNIT.isAssignableFrom( nodeType ) ) {
            SimpleDocUnit sdu = (SimpleDocUnit)node;
            displayDocTree( sdu.getTextSeq(), indent + "  " );
        }
        else if ( C_LISTING.isAssignableFrom( nodeType ) ) {
            Listing lst = (Listing)node;
            lst.getChildren().forEach(
                    textSeq -> displayDocTree( textSeq, indent + "  " )
            );
        }
        else if ( C_TEXTSEQUENCE.isAssignableFrom( nodeType ) ) {
            TextSequence tSeq = (TextSequence)node;
            tSeq.getChildren().forEach(
                    text -> displayDocTree( text, indent + "  " )
            );
        }
        else if ( C_STYLEDTEXT.isAssignableFrom( nodeType ) ) {
            StyledText styt = (StyledText)node;
            displayDocTree( styt.getText(), indent + "  " );
        }
        // For any other case, toString() in top line ought to do it.
    }

    /**
     * SIMPLE TEST.
     * Make sure this code can cause the system's browser to come up
     * with the contents of the HTML string generated.
     * @param args not used
     */
    public static void main( String[] args ) {
        BrowserUtil.render(
          "<html><head></head><body><h1>It works!</h1></body></html>"
        );
    }
}
