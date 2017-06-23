package crawler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final String searhStr = "sitemap:";

    public String parseRobotTxt(String site) {
        if (site.toLowerCase().contains(searhStr)) {
            return site.substring(site.toLowerCase().indexOf(searhStr) + 9);
        } else return null;
    }

    public ArrayList<String> parseSiteMap(String sitemap) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<String> urlPages = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(sitemap)));
        NodeList nodeLst = doc.getElementsByTagName("url");
//        System.gc();
        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;

                NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("loc");

                Element fstNmElmnt = (Element) fstNmElmntLst.item(0);

                NodeList fstNm = fstNmElmnt.getChildNodes();

                urlPages.add(((Node) fstNm.item(0)).getNodeValue());
            }
        }
        return urlPages;
    }

    public int calculateRank(String pageSource, List<String> personKeywords) {
        int count = 0;
        int start = 0;
        for (int i = 0; i < personKeywords.size(); i++) {
            start = 0;
            while (true) {
                pageSource = pageSource.substring(start);
                if (pageSource.contains(personKeywords.get(i))) {
                    start = pageSource.indexOf(personKeywords.get(i)) + 1;
                    count++;
                } else break;
            }
        }
        return count;
    }

    public ArrayList<String> parseSiteMapIndex(String sitemapIndex) throws ParserConfigurationException, IOException, SAXException { //Метод для парсинтга Sitemap Index
        ArrayList<String> urlPages = new ArrayList<>();
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(sitemapIndex)));
        NodeList nodeList = doc.getElementsByTagName("sitemap");
        for (int s = 0; s < nodeList.getLength(); s++){
            Node fstNode = nodeList.item(s);
            if(fstNode.getNodeType() == Node.ELEMENT_NODE){
                Element fstElmnt = (Element) fstNode;
                NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("loc");
                Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                NodeList fstNm = fstNmElmnt.getChildNodes();
                String urlPage = ((Node)fstNm.item(0)).getNodeValue();
                urlPages.add(urlPage);
            }
        }
        return urlPages;
    }
    //TODO получаем все ссылки из html. реализовать ввиде отдельного метода.
    //        Document html = Jsoup.parse(new HtmlSourceRepository().getSource());
    //        for (Element element : html.select("a")) {
    //        if (element.attr("abs:href") != "") {
    //            System.out.println(element.attr("abs:href"));
    //        }
    //    }
}