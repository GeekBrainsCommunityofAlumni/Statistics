package crawler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

public class Downloader {
//    public static void main(String[] args) { //Метод для тестирования класса
//
//    }

    public String download(String urlProtocol) throws UnknownHostException {
        UnknownHostException e = new UnknownHostException();

        //System.out.println("Downloader:download: urlProtocol: " + urlProtocol );
        if (!urlProtocol.startsWith("http")) {
            //System.out.println("Downloader:download:!haveProtocol(urlProtocol) - true");
            urlProtocol = "http://" + urlProtocol;
        }
        //System.out.println("Downloader:download: urlProtocol: " + urlProtocol );

        try {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES);
            OkHttpClient client = builder.build();
            Request request = new Request.Builder()
                    .url(urlProtocol)
                    .build();


            //TODO Дописать загрузку и распаковку GZip как в методе ранее.
            String result;
            try (Response response = client.newCall(request).execute()) { //UnknownHostException

                if (isItGzArchiveLink(urlProtocol)) {
                    result = downloadGzipFile1(response.body().bytes());
                } else {
                    result = response
                            .body()
                            .string()
                            .replaceFirst("windows-1251", "utf-8");
                }
                response.close();
                System.gc();
                return result;
            } catch (UnknownHostException e1) {
                e = e1;
                if (isReachable()) {
                    throw e; // while site not found
                } else {
                    return null; // while Internet connection lost null
                }
            }
        } catch (Exception e2) {
            if (e2.getClass().toString().equals("class java.net.UnknownHostException")) {
                throw e;
            } else {
                return null;
            }
        }
    }

    private boolean isItGzArchiveLink(String url) {
        return url.matches(".*[.]gz");
    }

    private static boolean isReachable() {
        for (int i = 0; i < 2; i++) {
            try {
                new BufferedReader(new InputStreamReader(new URL("https://www.google.ru").openConnection().getInputStream()));
                return true;
            } catch (IOException ignored) {
            }

            try {
                new BufferedReader(new InputStreamReader(new URL("https://www.yandex.ru/").openConnection().getInputStream()));
                return true;
            } catch (IOException ignored) {
            }

            try {
                new BufferedReader(new InputStreamReader(new URL("https://mail.ru/").openConnection().getInputStream()));
                return true;
            } catch (IOException ignored) {
            }

            try {
                if (i == 0) {
                    Thread.sleep(6 * 5);
                }
            } catch (InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    public String downloadRobot(String site) throws UnknownHostException {
        if (site.endsWith("/")) {
            site = site.substring(0, site.length() - 1);
        }
        return download(site + "/robots.txt");
    }

    public String downloadSiteMap(String urlProtocol) throws UnknownHostException {
        return download(urlProtocol);
    }

    private static String downloadGzipFile1(byte[] bytes) {
        try {
            InputStream isToGzip = new ByteArrayInputStream(bytes);
            GZIPInputStream gzis = new GZIPInputStream(isToGzip);
            BufferedReader in = new BufferedReader(new InputStreamReader(gzis));
            StringBuilder stringBuilder = new StringBuilder();
            String readed;
            while ((readed = in.readLine()) != null) {
                stringBuilder.append(readed);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}