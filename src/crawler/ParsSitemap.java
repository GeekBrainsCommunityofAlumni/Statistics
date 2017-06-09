package crawler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ParsSitemap {
    String sitemap;
    static List<String> site = new ArrayList<>();

    public ParsSitemap(String sitemap) {
        this.sitemap = sitemap;
    }

    public void parsSitemapFile() throws IOException, SAXException, ParserConfigurationException {
        URL url = new URL(sitemap);
        URLConnection con = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.xml")));
        String str;
        while ((str = reader.readLine()) != null) {
            writer.write(str);
        }
        writer.close();
        saveSite();
    }

    public void saveSite() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("data.xml"));

        NodeList nodeLst = doc.getElementsByTagName("url");

        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;

                NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("loc");

                Element fstNmElmnt = (Element) fstNmElmntLst.item(0);

                NodeList fstNm = fstNmElmnt.getChildNodes();

                site.add(((Node) fstNm.item(0)).getNodeValue());//Заменить сохрание в БД
            }
        }
    }
}
