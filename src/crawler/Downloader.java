package crawler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

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

    public String download(String urlProtocol) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlProtocol)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

//        String result = null;
//        if (isItGzArchiveLink(urlProtocol)) {
//            result = downloadGzipFile(urlProtocol);
//        } else {
//
//            URL urlAddress = null;
//            StringBuilder stringBuilder = new StringBuilder();
//
//            try {
//                urlAddress = new URL(urlProtocol);
//            } catch (MalformedURLException malformedURLException) {
//        }
//
//            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlAddress.openConnection().getInputStream()))) {
//                int i;
//                while ((i = bufferedReader.read()) != -1) {
//                    stringBuilder.append((char) i);
//                }
//                bufferedReader.close();
//            } catch (FileNotFoundException fileException) {
//
//                //throw new IOException(PAGE_NOT_FOUND);
//            } catch (UnknownHostException hostException) {
//                if (isReachable()) {
//                    System.out.println("throw new IOException(SITE_NOT_FOUND);");
//                    throw new IOException(SITE_NOT_FOUND);
//                } else {
//                    System.out.println("throw new IOException(INTERNET_CONNECTION_LOST);");
//                    throw new IOException(INTERNET_CONNECTION_LOST);
//                }
//            } catch (IOException ioException) {
//                System.out.println("throw new IOException(UNIDENTIFIED_ERROR);");
//                throw new IOException(UNIDENTIFIED_ERROR);
//            }
//            result = stringBuilder.toString();
//            System.out.println(result);
//        }
//
//        return result;
    }


    private boolean isItGzArchiveLink(String url) {
        return url.matches(".*[.]gz");
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
        String urlProtocol = null;
        //Выполняем проверку на наличие протокола в URL и если нет добавляем.

        if (!haveProtocol(site)) {
            urlProtocol = "http://" + site;
        } else {
            urlProtocol = site;
        }
        String robotTxt;
        System.out.println("Адрес сайта robots.txt: " + urlProtocol + "/robots.txt");
        robotTxt = download(urlProtocol + "/robots.txt");
        if (robotTxt.contains("301 Moved Permanently")) {
            String httpsURLSite = "https://" + site;
            robotTxt = download(httpsURLSite + "/robots.txt");
        }
        return robotTxt;
    }

    public String downloadSiteMap(String siteMapURL) throws IOException {
        String siteMap = null;
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

    private static String downloadGzipFile(String url) throws IOException {
        URL urlAddress = new URL(url);
        InputStream is = urlAddress.openConnection().getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytes;
        while ((bytes = is.read()) != -1) {
            baos.write(bytes);
        }
        baos.flush();
        InputStream isToGzip = new ByteArrayInputStream(baos.toByteArray());
        GZIPInputStream gzis = new GZIPInputStream(isToGzip);
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(gzis);
        BufferedReader in = new BufferedReader(reader);
        String readed;
        while ((readed = in.readLine()) != null) {
            stringBuilder.append(readed);
        }
        return stringBuilder.toString();
    }

    private boolean haveProtocol(String url) {
        boolean res = false;
        res = (url.matches("^http://[.]*")) || (url.matches("^https://[.]*"));
        return res;
    }
}