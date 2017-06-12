package crawler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class Downloader {
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
            throw new IOException("no protocol");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlAddress.openConnection().getInputStream()))) {
            int i;
            while ((i = bufferedReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
            bufferedReader.close();
        } catch (FileNotFoundException fileException) {
            throw new IOException("Page not found");
        } catch (UnknownHostException hostException) {
            if (isReachable()) {
                throw new IOException("Site not found");
            } else {
                throw new IOException("Internet connection lost");
            }
        } catch (IOException ioException) {
            throw new IOException("Unidentified error");
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
            if (e.getMessage().equals("Page not found")) {
                throw new IOException("robots.txt not found");
            } else
                throw new IOException(e.getMessage());
        }
        return robotTxt;
    }

    public String downloadSiteMap(String sitemapURL) throws IOException {
        String sitemap;
        try {
            sitemap = download(sitemapURL);
        } catch (IOException e) {
            if (e.getMessage().equals("Page not found")) {
                throw new IOException("sitemap not found");
            } else
                throw new IOException(e.getMessage());
        }
        return sitemap;
    }
}