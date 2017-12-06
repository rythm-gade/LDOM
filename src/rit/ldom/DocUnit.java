package rit.ldom;


public abstract class DocUnit implements Component {

    public static final int DEFAULT_ID = -1;

    public int id;

    protected DocUnit(int id) {
        this.id = id;
    }

    protected DocUnit() {
        this(DEFAULT_ID);
    }

    public int getID() {
        return id;
    }

}
