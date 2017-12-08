package rit.ldom;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleText implements Text {

    //The base text
    String text;

    public SimpleText(String realText){
        this.text = realText;
    }

    @Override
    public String generateHTML() {
        return text;
    }

    //Count each character
    @Override
    public long characterCount() {
        long count = 0;
        String[] str = text.split(" ");
        for (String s : str){
            count += s.length();
        }
        return count;
    }

    //Replace old string
    @Override
    public void replace(String oldS, String newS) {
        String replaceString = text.replaceAll(oldS, newS);
        text = replaceString;
        System.out.println(replaceString);
    }

    @Override
    public String display() {
        return this.getClass().getSimpleName() + "  " + text;
    }

    @Override
    public String toString() {
        return text;
    }
}
