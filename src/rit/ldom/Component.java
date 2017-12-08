package rit.ldom;

/**
 * The top-level type for all parts of an LDOM document.
 * All implementers of this interface must be able to execute
 * the methods descried herein.
 * @author James Heliotis
 */
public interface Component {

    /**
     * Generate legal HTML for this construct.
     * @return a string containing the HTML equivalent of what the contents
     *         of this object represents
     */
    public abstract String generateHTML();

    /**
     * How many characters are represented by this LDOM component?
     * @return the number of non-whitespace characters contained in the
     *         LDOM (sub)tree rooted at this component node
     */
    public abstract long characterCount();

    /**
     * Find all occurrences of the old string <em>within single
     * SimpleText objects</em> and replace them with the new string.
     * If pieces of the old string can be found in SimpleText objects,
     * even if when concatenated form the entire old string, they should
     * <em>not</em> be replaced.
     * @param oldS the old string to be replaced
     * @param newS the new string to replace the old one
     */
    public abstract void replace( String oldS, String newS );

    /**
     * OPTIONAL -- For testing purposes, you may override this method in every
     * class that implements this interface so that the method provides
     * more information.
     * It is useful if you debug with displayDocTree.
     * This method is not technically part of the assignment and will
     * not be graded.
     * <br>
     * The default behavior provided here is to just return the object's
     * class's name.
     * @see BrowserUtil#displayDocTree(Component, String)
     * @return any string representation of this object useful for debugging
     */
    public default String display() {
        return this.getClass().getSimpleName();
    }
}
