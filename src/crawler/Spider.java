import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Spider {
    static Set<String> erste = new HashSet<String>();
    static Set<String> zweite = new HashSet<String>();
    static Set<String> dritte = new HashSet<String>();
    static Iterator<String> itr;

    public Spider(){}
    public static Set<String> scanSite(String host) {

        searchSite(erste, host, host);
        dritte.addAll(erste);
        for (int i = 0; i < 1; i++) {//В цикле можно выставить какую вложенность страниц анализировть

            itr = dritte.iterator();
            while (itr.hasNext()) {
                try {
                    searchSite(zweite, host, itr.next().toString());
                } catch (Exception e) {
                }
            }
            zweite.removeAll(erste);
            dritte.removeAll(dritte);
            dritte.addAll(zweite);
            erste.addAll(zweite);
        }
        return erste;
    }
    public static Set<String> searchSite(Set<String> set, String host, String site) {
        Document doc = null;
        try {
            doc = Jsoup.connect(site).get();
        } catch (IOException e) {
        }
        for (int i = 0; i < doc.select("a").size(); i++) {
            Element link = doc.select("a").get(i);
            String linkHref = link.attr("href");
            if (linkHref.startsWith("/"))
            set.add(host + linkHref);
        }
        return set;
    }
}
