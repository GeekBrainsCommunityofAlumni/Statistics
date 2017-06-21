package crawler;

import java.util.ArrayList;

/**
 * Created by Serg on 21.06.2017.
 */
public class Person {

    private int ID;
    private ArrayList<String> keywordsList = new ArrayList<>();

    public Person(int personID, ArrayList<String> personKeywords) {
        this.ID = personID;
        this.keywordsList = personKeywords;
    }

    public int getID(){return this.ID;}

    public ArrayList<String> getKeywordsList(){return keywordsList;}
}
