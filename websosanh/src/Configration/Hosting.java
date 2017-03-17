/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configration;

import java.net.CookieStore;


/**
 *
 * @author MinhPC
 */
public final class Hosting {
    public  String HOSTNAME       = "https://websosanh.vn/";
    public  String POST_AJAX_LINK = "https://websosanh.vn/Product/Detail/ListComparePrice";
    public static void setSystemtoFillder(){
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
    }   
    
    
   
}
