package rit.ldom;
import java.util.ArrayList;
import java.util.List;

public class StyledText implements Text {

    Style style;
    Text text;

    public StyledText(Style style, Text text){
        this.style = style;
        this.text = text;
    }

    public Text getText() {
        return text;
    }

    //genHTML for each text with added style
    @Override
    public String generateHTML() {
        if (style == Style.bold){
            return "<b>" + getText().generateHTML() + "</b>";
        }

        else if (style == Style.italic){
            return "<i>" + getText().generateHTML() + "</i>";
        }

        else{
            return "<u>" + getText().generateHTML() + "</u>";
        }
    }

    @Override
    public long characterCount() {
        String[] str = text.generateHTML().split("");
        return str.length;
    }

    @Override
    public void replace(String oldS, String newS) {
        String replaceString = text.generateHTML().replaceAll(oldS, newS);
        System.out.println(replaceString);

    }

    @Override
    public String display() {
        return this.getClass().getSimpleName() + "  " + style;
    }

    @Override
    public String toString() {
        return text.generateHTML();
    }
}
