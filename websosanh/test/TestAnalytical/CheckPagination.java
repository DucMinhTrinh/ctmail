/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestAnalytical;

//import Analytical.Parser;
import Configration.Hosting;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author MinhPC
 */
public class CheckPagination {
    public static void main(String[] args) throws IOException {
       Response home =  Jsoup.connect(new Hosting().HOSTNAME)
               .method(Connection.Method.GET)
               .execute();
       
//       Document page = Jsoup.connect("http://websosanh.vn/Product/Detail/ListComparePrice")
//                       .cookies(home.cookies())
//                       .header("Accept", "application/json, text/javascript, */*; q=0.01")
//                       .header("Accept-Encoding", "gzip, deflate, br")
//                       .header("Content-Type", "application/x-www-form-urlencoded")
//                       .header("X-Requested-With", "XMLHttpRequest")
//                       .data("productId", "1327160645")
//                       .data("pageIndex", "2")
//                       .data("regionId", "0")
//                       .data("sortType", "1")
//                       .userAgent("Mozilla")
//                       .post();
//       
       
       
       
       
       String httpsURL = "https://websosanh.vn/Product/Detail/ListComparePrice";

        String query = "pageIndex=2&productId=1327160645&regionId=0&sortType=1";

        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-length", String.valueOf(query.length())); 
        con.setRequestProperty("Content-Type","application/x-www- form-urlencoded"); 
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)"); 
        con.setRequestProperty("Cookie", home.cookies().toString().substring(1, home.cookies().toString().length()-1));
        con.setDoOutput(true); 
        con.setDoInput(true); 

        DataOutputStream output = new DataOutputStream(con.getOutputStream());  


        output.writeBytes(query);

        output.close();

        DataInputStream input = new DataInputStream( con.getInputStream() ); 



        for( int c = input.read(); c != -1; c = input.read() ) 
        System.out.print( (char)c ); 
        input.close(); 

        System.out.println("Resp Code:"+con .getResponseCode()); 
        System.out.println("Resp Message:"+ con .getResponseMessage()); 
            }
}
