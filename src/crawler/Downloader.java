package crawler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Юрий on 06.06.2017.
 * http://javatalks.ru/topics/13271?page=1#62015
 */
public class Downloader {
//    public static void main(String[] args) throws IOException {
//        long t = System.currentTimeMillis();
//        Downloader downloader = new Downloader();
//        String url = "https://lenta.ru/";
//        String s = downloader.download(url);
//        System.out.println(s);
//        BufferedWriter bw = new BufferedWriter(new FileWriter("1.html"));
//        bw.write(s);
//        bw.close();
//        System.out.println(System.currentTimeMillis() - t);
//    }

    public String download(String url) throws IOException {
        URL urlAddress = null;
        StringBuilder builder = new StringBuilder();

        try {
            urlAddress = new URL(url);
        } catch (MalformedURLException e) {
            throw new IOException(e + "\n " + url + " указан неверно");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(urlAddress.openConnection().getInputStream()))) {
            int i;
            while ((i = br.read()) != -1) {
                builder.append((char) i);
            }
            br.close();
        } catch (IOException e) {
            throw new IOException(e + "\n " + "Ошибка чтения страницы");
        }
        return builder.toString();
    }

    public String downloadRobot(String site) {

        return "";
    }
}
