package rit.ldom;

public class Paragraph extends SimpleDocUnit {

    public Paragraph(int id, TextSequence textSeq) {
        super(id, textSeq);
    }

    @Override
    public String generateHTML() {
        TextSequence textSequence = getTextSeq();
        String str = textSequence.generateHTML();
        return "<p>" + str + "</p>";
    }
}
