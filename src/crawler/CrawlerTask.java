package crawler;

import java.util.ArrayList;

/**
 * Created by Serg on 21.06.2017.
 */
public class CrawlerTask {
    private ArrayList<Person> personList = new ArrayList<>();
    private PageForScanning pageForScanning;


    public void addPerson(Person person) {
        personList.add(person);
    }

    public void addPageForScanning(PageForScanning pageForScanning){
        this.pageForScanning = pageForScanning;
    }

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public PageForScanning getPage() {
        return pageForScanning;
    }

    public void addPersonList(ArrayList<Person> personArrayList) {
        if(personArrayList != null){
            this.personList.addAll(personArrayList);
        }
    }
}
