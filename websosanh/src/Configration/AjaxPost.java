
package Configration;

import Analytical.Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;




public class AjaxPost {
    
    public static JSONObject requestPost(String productID,String pageIndex) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, JSONException{
          //step 1
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

            String postData = "{productId:"+ productID+",regionId:0,sortType:1,pageIndex:"+pageIndex+"}";
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
                return new JSONObject(postResult);
    }
   
    public static JSONObject getJsonOfAProduct(String urlPage){
       JSONObject json = null;
       String TotalProduct = "";
       JSONArray provinss = new JSONArray();
       JSONArray Products = new JSONArray();
       try{
            Document pagebody  =  Jsoup.connect(urlPage).method(Connection.Method.GET).execute().parse();
            String hfProductId = pagebody.getElementById("hfProductId").val().trim();
            if(hfProductId.equals(null)){
                return json;
            }
            
            int maxPage = Parser.getTheNumberMaxOfWebPage(pagebody);
            for (int i = 1; i <= maxPage ;i++) {
                JSONObject subJson = AjaxPost.requestPost(hfProductId, String.valueOf(i));
                if(subJson==null) return json;
////              
                JSONObject data = null ;
                String dataString =subJson.get("Data").toString();
                data = new JSONObject(dataString);
                if(i==1){
                    TotalProduct =  data.get("TotalProduct").toString();
                    provinss = (JSONArray) data.get("Provins");
                }
//                
                JSONArray product = (JSONArray) data.get("Products");
                for (int j = 0; j < product.length(); j++) {   
                    Products.put(product.get(j));
                }
                
            }
            
            if(Products.length()==0){
                return null;
            }
            if(TotalProduct.equals("")){
                return null;
            }
            if(provinss.length()==0){
                return null;
            }
            json = new JSONObject();
            json.put("TotalProduct", TotalProduct);

            json.put("Products", Products);

            json.put("Provins", provinss);

            return json;
            
       }catch(Exception e){
           return json;
       }
    }
    
    /*Checking*/
    public static void main(String []sdsfd) throws Exception{
       String urlPage = "https://websosanh.vn/may-tinh-bang-apple-ipad-air-2-cellular-64gb/131232511/so-sanh.htm";
       JSONObject json = AjaxPost.getJsonOfAProduct(urlPage);
       if(json!=null){
           System.out.println(json);
       }

    }
    
}
