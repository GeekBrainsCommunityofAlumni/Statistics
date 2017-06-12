package crawler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class Downloader {
    public static final String NO_PROTOCOL = "No protocol";
    public static final String PAGE_NOT_FOUND = "Page not found";
    public static final String SITE_NOT_FOUND = "Site not found";
    public static final String INTERNET_CONNECTION_LOST = "Internet connection lost";
    public static final String UNIDENTIFIED_ERROR = "Unidentified error";
    public static final String ROBOTSTXT_NOT_FOUND = "Robots.txt not found";
    public static final String SITE_MAP_NOT_FOUND = "Site map not found";
    public static void main(String[] args) throws IOException { //Метод для тестирования класса

//        long t = System.currentTimeMillis();
//        Downloader downloader = new Downloader();
//        String url = "http://lenta.ru/robots.txt";
//        //String url = "https://www.kommersant.ru/robots.txt";
//        String s = downloader.download(url);
//        System.out.println(s);
//        BufferedWriter bw = new BufferedWriter(new FileWriter("1.html"));
//        bw.write(s);
//        bw.close();
//        System.out.println(System.currentTimeMillis() - t);
    }

    public String download(String url) throws IOException {
        URL urlAddress = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            urlAddress = new URL(url);
        } catch (MalformedURLException malformedURLException) {
            throw new IOException(NO_PROTOCOL);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlAddress.openConnection().getInputStream()))) {
            int i;
            while ((i = bufferedReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            bufferedReader.close();
        } catch (FileNotFoundException fileException) {
            throw new IOException(PAGE_NOT_FOUND);
        } catch (UnknownHostException hostException) {
            if (isReachable()) {
                throw new IOException(SITE_NOT_FOUND);
            } else {
                throw new IOException(INTERNET_CONNECTION_LOST);
            }
        } catch (IOException ioException) {
            throw new IOException(UNIDENTIFIED_ERROR);
        }
        return stringBuilder.toString();
    }

    private static boolean isReachable() {
        boolean test1;
        boolean test2;
        boolean test3;

        try {
            new BufferedReader(new InputStreamReader(new URL("https://www.google.ru").openConnection().getInputStream()));
            test1 = true;
        } catch (IOException e) {
            test1 = false;
        } finally {
            try {
                new BufferedReader(new InputStreamReader(new URL("https://www.yandex.ru/").openConnection().getInputStream()));
                test2 = true;
            } catch (IOException e) {
                test2 = false;
            } finally {
                try {
                    new BufferedReader(new InputStreamReader(new URL("https://mail.ru/").openConnection().getInputStream()));
                    test3 = true;
                } catch (IOException e) {
                    test3 = false;
                }
            }
        }

        return test1 || test2 || test3;
    }


    public String downloadRobot(String site) throws IOException {
        String robotTxt;
        try {
            robotTxt = download(site + "/robots.txt");
        } catch (IOException e) {
            if (e.getMessage().equals(PAGE_NOT_FOUND)) {
                throw new IOException(ROBOTSTXT_NOT_FOUND);
            } else
                throw new IOException(e.getMessage());
        }
        return robotTxt;
    }

    public String downloadSiteMap(String siteMapURL) throws IOException {
        String siteMap;
        try {
            siteMap = download(siteMapURL);
        } catch (IOException e) {
            if (e.getMessage().equals(PAGE_NOT_FOUND)) {
                throw new IOException(SITE_MAP_NOT_FOUND);
            } else
                throw new IOException(e.getMessage());
        }
        return siteMap;
    }
}