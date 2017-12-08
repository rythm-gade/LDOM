package rit.ldom;

import java.util.ArrayList;
import java.util.List;

public class TextSequence implements Component {

    //ArrayList to hold each Text object
    ArrayList<Text> textLst;

    public TextSequence(){
        this.textLst = new ArrayList<>();
    }

    public void addChild(Text text){
        textLst.add(text);
    }

    public Iterable<Text> getChildren(){
        return textLst;
    }

    //genHTML for all text in textlst
    @Override
    public String generateHTML() {
        String str = "";
        for (Text txt : textLst){
            str += txt.generateHTML();
        }

        return str;
    }

    @Override
    public long characterCount() {
        long count = 0;
        for (Text text : textLst){
            count += text.characterCount();
        }
        return count;
    }

    @Override
    public void replace(String oldS, String newS) {
        for (Text text : textLst){
            text.replace(oldS, newS);
        }

    }

    @Override
    public String display() {
        return this.getClass().getSimpleName() + "  " + textLst.toString();
    }
}
