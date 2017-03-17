package websosanh;
/**
 *
 * @author MinhPC
 */
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
 
import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
 
public class TaipeiWater {
    
    final static String requestUrl = "https://websosanh.vn/dien-thoai-smartphone/cat-82.htm";
 
   public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
 
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
 
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
 
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
 
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
                
        Response response = Jsoup.connect("https://websosanh.vn/dien-thoai-apple-iphone-6-16gb/1327160645/so-sanh.htm")
                            .method(Connection.Method.GET)
                            .execute();
        
        
      
        Map cookie = response.cookies();
          System.out.println(cookie);
        Document detail = Jsoup.connect("https://websosanh.vn/Product/Detail/ListComparePrice")
                           .header("Accept", "application/json, text/javascript, */*; q=0.01")
                           .header("Accept-Encoding", "gzip, deflate, br")
                           .header("Accept-Language", "vi")
                           .header("Connection", "keep-alive")
                           .header("Content-Type", "application/json")
                           .header("Host", "websosanh.vn")
                           .header("Origin", "https://websosanh.vn")
                           .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                           .header("X-Requested-With", "XMLHttpRequest")
                           .cookies(cookie)
                           .data("pageIndex", "2")
                           .data("productId","1327160645")
                           .data("regionId", "0")
                           .data("regionId", "1")
                           .referrer("https://websosanh.vn/dien-thoai-apple-iphone-6-16gb/1327160645/so-sanh.htm")
                           .ignoreContentType(true)
                           .post();
 
       
    }
 
    public static void doProcess(Document document){
        System.out.println(document.body());
    }
}