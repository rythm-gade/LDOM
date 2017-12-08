package rit.ldom;

import java.util.List;

public class Listing extends DocUnit {

    boolean ordered;

    //List of textsequences
    List<TextSequence> tSeqs;

    //Initialize listing with list, ordered and superID
    public Listing(int id, boolean ordered, List<TextSequence> tSeqs){
        super(id);
        this.ordered = ordered;
        this.tSeqs = tSeqs;
    }


    public Iterable<TextSequence> getChildren(){
        return tSeqs;
    }

    //genHTML for each textsequence
    @Override
    public String generateHTML() {
        String s = " ";
        if (ordered == true){
            s+= "<ol>";
            for (TextSequence text : tSeqs){
                s +=  "<li>" + " " + text.generateHTML() + "</li>";
            }
            s+= "</ol>";
        }

        else {
            s += "<ul style=\"list-style-type:disc\">";
            for (TextSequence text : tSeqs){
                s+=  "<li>" + " " + text.generateHTML() + "</li>";
            }
            s+= "</ul>";
        }

        return s;
    }

    @Override
    public long characterCount() {
        long count = 0;
        for (TextSequence text : tSeqs){
            count += text.characterCount();
        }
        return count;
    }

    @Override
    public void replace(String oldS, String newS) {
    }
}
