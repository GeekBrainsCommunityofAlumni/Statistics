package crawler;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Serg on 12.06.2017.
 */
public class CrawlerTest {
    private static DBHelper dbHelper;
    private static Parser parser;


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        init();

        //testDBHelper();
        testParser();
    }

    private static void testParser() throws IOException, SAXException, ParserConfigurationException {
        System.out.println("Тестируем Parser...");
        parser = new Parser();
        String testText1 = "<body>Иванов Иванов не лопух.</body>";
        ArrayList<String> testKeywords1 = new ArrayList<>();
        testKeywords1.add("Иванов");
        testKeywords1.add("лопух");
        int rank1 = parser.calculateRank(testText1, testKeywords1);
        System.out.println("testRank1:" +(3==rank1));

        String testText2 = "alksqw jdhfkslerjf hsaerlkjfhs'f;lskqwf';aslkfaks jhfdksa faaserqwgfdksjhf dg;drtslfkgj ;dslrtjgrtrt";
        ArrayList<String> testKeywords2 = new ArrayList<>();
        testKeywords2.add("qw");
        testKeywords2.add("er");
        testKeywords2.add("rt");
        int rank2 = parser.calculateRank(testText2, testKeywords2);
        System.out.println("testRank2:" + (10==rank2));


        String testRobotsTxt1 = "User-agent: *\n" +
                "Allow: /infographics/\n" +
                "Disallow: /ajax\n" +
                "Disallow: /rate\n" +
                "Disallow: /pogoda\n" +
                "Disallow: /maps\n" +
                "Disallow: */page\n" +
                "Disallow: */comments\n" +
                "Disallow: /inf\n" +
                "Disallow: /pic\n" +
                "Disallow: /search*\n" +
                "Disallow: /monitoring\n" +
                "Disallow: /preview\n" +
                "Disallow: /rb*\n" +
                "Disallow: /*utm_\n" +
                "Disallow: /*/gallery/\n" +
                "Disallow: /*q=\n" +
                "Disallow: /currency.html?date=\n" +
                "Disallow: /go-web/\n" +
                "Disallow: /go-mobile/\n" +
                "Host: https://news.mail.ru\n" +
                "Sitemap: https://news.mail.ru/sitemap_index.xml";
        System.out.println("testRobotsTxt1:" + (parser.parseRobotTxt(testRobotsTxt1).equals("https://news.mail.ru/sitemap_index.xml")));


        String testSitemapIndexXml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:mobile=\"http://www.google.com/schemas/sitemap-mobile/1.0\">\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap.xml</loc>\n" +
                "        <lastmod>2017-06-16T08:30+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_reference.xml</loc>\n" +
                "        <lastmod>2017-06-16T08:30+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1493704618.xml.gz</loc>\n" +
                "        <lastmod>2017-06-16T08:30+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1486230912.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:10+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1478855276.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:10+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1471599811.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:10+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1464463955.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:09+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1456462885.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:06+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1447764268.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:04+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1439976331.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:04+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1432646470.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:03+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1425490311.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:03+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1418369789.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:02+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1412238022.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:02+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1406033575.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:01+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1400157736.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:01+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1395274430.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:01+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1390387061.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:00+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1385133402.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T05:00+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1380644147.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:59+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1376655533.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:59+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1372841409.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:59+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1368874015.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:58+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1364310472.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:58+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1359727581.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:58+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1354871754.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:57+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1350313134.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:57+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1345625544.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:57+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1340984137.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:56+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1336655951.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:56+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1332163991.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:56+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1327407753.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:55+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1322050277.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:55+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1317181727.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:55+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1311774710.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:54+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1306718335.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:54+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1301659784.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:54+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1296683074.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:53+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1291041068.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:53+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1285666637.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:53+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1279707962.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:53+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1273137238.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:52+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1267005960.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:52+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1258186764.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:52+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1249555819.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:52+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1240659525.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:51+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "    <sitemap>\n" +
                "        <loc>https://news.mail.ru/sitemap_1230766433.xml.gz</loc>\n" +
                "        <lastmod>2017-06-01T04:51+03:00</lastmod>\n" +
                "    </sitemap>\n" +
                "\n" +
                "</sitemapindex>\n";
        ArrayList<String> urlPages = parser.parseSiteMapIndex(testSitemapIndexXml1);
        System.out.println("testSitemapXml1:");
        for (String urlpage: urlPages) {
            System.out.println(urlpage);
        }



    }


    private static void init() {
    }

    private static void testDBHelper() {
        dbHelper = new DBHelper();
        System.out.println("Подключаемся к MySQL");
        dbHelper.connectToDB();
        System.out.println("Вероятно успешно");

        System.out.println("---");

        System.out.println("Список ID имеющихся в базе:");
        ArrayList<Integer> personsID = dbHelper.getPersonsID();
        //Показываем что получили.
        for (Integer personID : personsID) {
            System.out.println(personID);
        }
    }

}
