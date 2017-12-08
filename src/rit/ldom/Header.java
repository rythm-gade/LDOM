package rit.ldom;

public class Header extends SimpleDocUnit {
    int lvl;

    public Header(int level, int id, TextSequence textSeq) {
        super(id, textSeq);
        this.lvl = level;

    }

    public Header(int level, TextSequence textSeq) {
        super(textSeq);
        this.lvl = level;
    }

    @Override
    public String generateHTML() {
        TextSequence textSequence = getTextSeq();
        String str = textSequence.generateHTML();
        return "<h" + String.valueOf(lvl) + ">" + str + "</h" + String.valueOf(lvl) + ">";
    }
}
