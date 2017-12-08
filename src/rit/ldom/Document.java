package rit.ldom;

import java.util.ArrayList;

public class Document implements Component {

    //ArrayList to hold DocUnits
    ArrayList<DocUnit> document;

    public String title;

    //Initialize document with title and arraylist
    public Document(String title){
        this.title = title;
        this.document = new ArrayList<>();
    }

    //add DocUnit
    public void addChild(DocUnit docUnit){
        document.add(docUnit);
    }

    public Iterable<DocUnit> getChildren(){
        return document;
    }

    //Loop through DocUnits and generateHTML on each
    //Output HTML tree
    @Override
    public String generateHTML() {
        String str = "";
        for (DocUnit units : document){
            str += units.generateHTML();
        }
        return "<html>\n" +
                "   <head>\n" +
                "       <title>" + title + "</title>\n" +
                "   </head>\n" +
                "   <body>\n" +
                "       " + str + "\n" +
                "   </body>\n" +
                "</html>";
    }

    //Charactercount for each unit
    @Override
    public long characterCount() {
        long count = 0;
        for (DocUnit units : document){
            count += units.characterCount();
        }
        return count;
    }

    @Override
    public void replace(String oldS, String newS) {
        for (DocUnit unit : document){
            unit.replace(oldS, newS);
        }

    }

    public void replace(int docUnitID, DocUnit docUnit){
        for (int i = 0; i < document.size(); i++){
            DocUnit unit = document.get(i);
            if (unit.getID() == docUnitID){
                document.set(i, docUnit);
                System.out.println("DocUnit successfully replaced");
                break;
            }

            else{
                System.out.println("Could not find DocUnit");
            }
        }
    }

}
