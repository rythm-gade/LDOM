import rit.ldom.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 * A test program that allows multiple tests via its input file. Here is
 * an example of the input format.
 * 
 * root{
 *   p{
 *     text{ hello }
 *     style bold{ text{ world } }
 *     text{ I exclaim }
 *   }
 * }
 * @author James Heliotis
 */
public class TestParser {

    public static final String INFILE = "doc.txt";

    public static final String HEADER = "h";
    public static final String ORDERED_LIST = "ol";
    public static final String UNORDERED_LIST = "ul";
    public static final String PARAGRAPH = "p";
    public static final String DOC = "doc";
    public static final String STYLE = "style";
    public static final String TEXT = "text";
    public static final String STYLE_OF_TEXT = "style of text";

    private static StreamTokenizer st;
    public static final String NOT_TEXT = "expecting text element";
    public static final String DOC_HDR_ERR = "document header";

    /**
     * Parse the test file and generate calls to the DocObject classes to
     * create the HTML file.
     */
    private static DocUnit readDocUnit()
                        throws FormatException, IOException {
        
        if ( st.nextToken() == StreamTokenizer.TT_WORD ) {

            switch ( st.sval ) {
                case HEADER:
                    return readHeader();
                case ORDERED_LIST:
                    return readListing( true );
                case UNORDERED_LIST:
                    return readListing( false );
                case PARAGRAPH:
                    return readParagraph();
                default:
                    ensure( true, "illegal DocUnit type" );
                    return null;
            }
        }
        else {
            ensure( st.ttype == '}', "extra tokens" );
            return null;
        }
    }

    /**
     * Read a single piece of text.
     */
    private static Text readText() throws IOException, FormatException {

        int tok = st.nextToken();
        if ( tok == StreamTokenizer.TT_WORD ) {
            switch ( st.sval ) {
                case STYLE:
                    return readStyle();
                case TEXT:
                    return readSimpleText();
                default:
                    ensure( false, NOT_TEXT );
                    return null;
            }
        }
        else {
            ensure( tok == '}', NOT_TEXT );
            return null;
        }
    }

    private static Text readSimpleText() throws IOException, FormatException {
        int tokType = st.nextToken();
        ensure( tokType == '{', NOT_TEXT );

        StringBuffer text = new StringBuffer();
        tokType = st.nextToken();
        while ( tokType != '}' ) {
            if ( tokType == StreamTokenizer.TT_WORD ||
                 tokType == '"' || tokType == '\'' ) {
                text.append( st.sval );
                text.append( ' ' );
            }
            else {
                if ( tokType == '<' ) {
                    text.append( "&lt; " );
                }
                else if ( tokType == '>' ) {
                    text.append( "&gt; " );
                }
                else {
                    text.append( (char)tokType );
                }
            }
            tokType = st.nextToken();
        }
        return new SimpleText( new String( text ) );
    }

    private static StyledText readStyle() throws IOException, FormatException {
        ensure( st.nextToken() == StreamTokenizer.TT_WORD, STYLE_OF_TEXT );
        Style style = null;
        try {
            style = Style.valueOf( st.sval );
        }
        catch( IllegalArgumentException e ) {
            ensure( true, STYLE_OF_TEXT );
        }
        ensure( st.nextToken() == '{', NOT_TEXT );
        Text body = readText();
        ensure( st.nextToken() == '}', NOT_TEXT );
        return new StyledText( style, body );
    }

    private static Paragraph readParagraph()
                            throws IOException, FormatException {
        TextSequence body = readTextSequence();
        return new Paragraph( 0, body );
    }

    private static Listing readListing( boolean b )
                        throws IOException, FormatException {
        List< TextSequence > body = readItemSequence();
        return new Listing( 0, b, body );
    }

    private static Header readHeader() throws IOException, FormatException {
        int tokType = st.nextToken();
        ensure( tokType == StreamTokenizer.TT_NUMBER, "header level" );
        int level = (int)st.nval;
        TextSequence body = readTextSequence();
        return new Header( level, body );
    }

    private static TextSequence readTextSequence()
                                throws IOException, FormatException {
        int tokType = st.nextToken();
        if ( tokType == '}' ) return null;

        TextSequence result = new TextSequence();
        ensure( tokType == '{', "text sequence" );
        Text segment = readText();
        while ( segment != null ) {
            result.addChild( segment );
            segment = readText();
        }
        return result;
    }

    private static List< TextSequence > readItemSequence()
                                throws IOException, FormatException {
        ArrayList< TextSequence > result = new ArrayList<>();
        ensure( st.nextToken() == '{', "readTextSequence" );
        TextSequence seq = readTextSequence();
        while ( seq != null ) {
            result.add( seq );
            seq = readTextSequence();
        }
        return result;
    }

    private static class FormatException extends Exception {
        public FormatException( String reason ) {
            super( "Input, line " + st.lineno() + ": " + reason );
        }
    }

    private static void ensure( boolean valid, String errMsg )
                                                    throws FormatException {
        if ( !valid ) throw new FormatException( errMsg );
    }

    /**
     * Parse the test file and generate calls to the DocObject classes to
     * create the HTML file.
     * 
     * @param args the input file name (default in variable INFILE)
     */
    public static void main( String[] args ) {
        Document doc = test( args );
        BrowserUtil.render( doc.generateHTML() );
    }

    public static Document test( String[] args ) {
        Document doc = new Document( "BUSTED" );
        String fName = INFILE;
        switch ( args.length ) {
        case 0:
            break;
        case 1:
            fName = args[ 0 ];
            break;
        default:
            System.err.println( "Usage: java TestParser [infile]" );
            System.exit( 1 );
        }

        try ( FileReader fr = new FileReader( fName ) ) {
            st = new StreamTokenizer( fr );
            st.eolIsSignificant( false );
            st.slashSlashComments( false );
            st.slashStarComments( false );

            int tokType = st.nextToken();
            ensure( tokType == StreamTokenizer.TT_WORD &&
                    st.sval.equals( DOC ), DOC_HDR_ERR
            );
            ensure( st.nextToken() == StreamTokenizer.TT_WORD,
                    DOC_HDR_ERR
            );
            String title = st.sval;
            ensure( st.nextToken() == '{', DOC_HDR_ERR );
            doc = new Document( title );
            DocUnit dUnit = readDocUnit();
            while( dUnit != null ) {
                doc.addChild( dUnit );
                dUnit = readDocUnit();
            }

            System.out.println( doc.characterCount() + " non-ws chars" );
            int count = 0;
            for ( DocUnit d: doc.getChildren() ) count += 1;
            System.out.println( count + " document units in document" );

            BrowserUtil.displayDocTree( doc, "" );
        }
        catch( FormatException fe ) {
            System.err.println( "Document formatting error:" );
            System.err.println( fe.getMessage() );
        }
        catch ( FileNotFoundException e ) {
            System.err.println( "File " + fName + " could not be opened." );
        }
        catch ( IOException e ) {
            System.err.println( "Error reading input file." );
        }
        finally {
            st = null;
        }
        return doc;
    }
}
