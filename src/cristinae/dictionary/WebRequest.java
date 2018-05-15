package cristinae.dictionary;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class WebRequest {
    
    private static void setRequestHeader(HttpURLConnection conn){
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36)");
        conn.setRequestProperty("Accept", "Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }
    public static String post(String url, String data, String charset) {
        try {
            URL __url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            setRequestHeader(conn);
            OutputStreamWriter bos = new OutputStreamWriter(
                    conn.getOutputStream(), charset);
            bos.write(data);
            bos.flush();
            BufferedReader bis = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = bis.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            bis.close();
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
    public static String get(String url, String charset) {
        try {
            URL __url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
            setRequestHeader(conn);
            BufferedReader bis = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = bis.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            bis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static byte[] file(String url) {
        try {
            URL __url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
            setRequestHeader(conn);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(
                    conn.getInputStream());
            byte[] b = new byte[1024];
            int length = -1;
            while ((length = bis.read(b)) != -1) {
                bos.write(b, 0, length);
                bos.flush();
            }
            bis.close();
            bos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String mid(String value, String left, String right) {
        try {
            int i = value.indexOf(left) + left.length();
            return value.substring(i, value.indexOf(right, i));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String sub(String value, String mark, int len) {
        try {
            int i = value.indexOf(mark) + mark.length();
            return value.substring(i, i + len);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String decode(String value, String charset) {
        try {
            return URLDecoder.decode(value, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String encode(String value, String charset) {
        try {
            return URLEncoder.encode(value, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
