package rit.ldom;

public abstract class SimpleDocUnit extends DocUnit {

    //Container for textsequences
    TextSequence textSequence;

    protected SimpleDocUnit(int id, TextSequence textSeq){
        super(id);
        this.textSequence = textSeq;
    }

    protected SimpleDocUnit(TextSequence textSeq){
        super();
        this.textSequence = textSeq;
    }

    public TextSequence getTextSeq() {
        return textSequence;
    }

    public String generateHTML(){
        String str = "";
        for (Text text : textSequence.getChildren()){
            str += text.generateHTML();
        }
        return str;
    }

    @Override
    public long characterCount() {
        long count = 0;
        for (Text text : textSequence.getChildren()){
            count += text.characterCount();
        }
        return count;
    }

    @Override
    public void replace(String oldS, String newS) {
        for (Text text : textSequence.getChildren()){
            text.replace(oldS, newS);
        }
    }
}

