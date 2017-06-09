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

public class Parser {
    private static final String searhStr = "sitemap";
    private static String sitemap;
    private List<String> site;
    private URL url;
    private URLConnection con;
    private BufferedReader reader;
    private BufferedWriter writer;

    public float calculateRating(String site, String person) {
        return (float) 0.0;
    }

    public String parseRobotTxt(String host) throws IOException {
        url = new URL("https://" + host + "/robots.txt");
        con = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String robots;
        while ((robots = reader.readLine()) != null) {
            if (robots.contains(searhStr)) {
                sitemap = robots.substring(9);
            }
        }
        return sitemap;
    }

    public List<String> parseSiteMap(String sitemap) throws IOException, ParserConfigurationException, SAXException {
        site = new ArrayList<>();
        url = new URL(sitemap);
        con = url.openConnection();
        reader = new BufferedReader(new InputStreamReader(url.openStream()));
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data.xml")));
        String str;
        while ((str = reader.readLine()) != null) {
            writer.write(str);
        }
        reader.close();
        writer.close();

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

                site.add(((Node) fstNm.item(0)).getNodeValue());
            }
        }
        return site;
    }
}
