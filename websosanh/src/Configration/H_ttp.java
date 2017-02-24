
package Configration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class H_ttp {
    public static void main(String []sdsfd) throws Exception{
        
        //step 1
        String productPage = "https://websosanh.vn/dien-thoai-apple-iphone-6-16gb/1327160645/so-sanh.htm";
        CookieStore cookieStore = new BasicCookieStore();
        
        String postUrl = "https://websosanh.vn/Product/Detail/ListComparePrice";
        String postResult = "";
        {
             //step 2
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookieStore).build();

            
            HttpPost post = new HttpPost(postUrl);
            post.addHeader("Host", "websosanh.vn");
            post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
            post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.addHeader("Accept-Language", "en-US,en;q=0.5");
            post.addHeader("Accept-Encoding", "gzip, deflate, br");
            //post.addHeader("Referer", productPage);
            post.addHeader("Content-Type", "application/json");

            String postData = "{\"productId\":\"1327160645\",\"regionId\":0,\"sortType\":1,\"pageIndex\":3}";
            HttpEntity entity = new ByteArrayEntity(postData.getBytes());
            post.setEntity(entity);
            // execute post
            HttpResponse response = httpclient.execute(post);


            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            try {
                rd.close();
            } catch (IOException e) {
            }
            postResult = result.toString();
        }
        
        //String result = D_common.H_ttp.postData("https://websosanh.vn/Product/Detail/ListComparePrice", "{\"productId\":\"1327160645\",\"regionId\":0,\"sortType\":1,\"pageIndex\":2}");
        JSONObject a = new JSONObject(postResult);
        System.out.println(a.get("Data"));
    }
    
}
