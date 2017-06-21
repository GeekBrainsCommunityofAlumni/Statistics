package crawler;

import java.util.ArrayList;

/**
 * Created by Serg on 21.06.2017.
 */
public class CrawlerTaskRepository {
    private ArrayList<CrawlerTask> crawlerTaskArrayList = new ArrayList<CrawlerTask>();

    public void addTask(CrawlerTask crawlerTask){
        crawlerTaskArrayList.add(crawlerTask);
    }

    public synchronized CrawlerTask getTask() {
        if (crawlerTaskArrayList.size()>0){
            CrawlerTask result = crawlerTaskArrayList.get(0);
            crawlerTaskArrayList.remove(0);
            return result;
        } else {
            return null;
        }
    }

    public int size(){
        return crawlerTaskArrayList.size();
    }
}
