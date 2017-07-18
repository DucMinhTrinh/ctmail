/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common.E_xample;

import D_common.F_ile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author vietduc
 */
public class SSLHttpsSocket {
    public static String sendPostBySSLSocket(String host, String uri, String user, String pass)
            throws Exception {
        String postData = "username=" + user + "&password=" + pass;

        String header_Postdata = "";
        header_Postdata += "POST /auth/login HTTP/1.1\r\n";
        header_Postdata += "Host: vinaphone.com.vn:443\r\n";
        header_Postdata += "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0\r\n";
        header_Postdata += "Referer: https://vinaphone.com.vn/auth/login?service=http%3A%2F%2Fvinaphone.com.vn%3A80%2Flogin.jsp%3Flang%3Dvi\r\n";
        header_Postdata += "Cookie: JSESSIONID=123HJSGJHDGDJFG\r\n";
        header_Postdata += "Content-Type: application/x-www-form-urlencoded\r\n";
        header_Postdata += "Content-Length: " + postData.length() + "\r\n";
        header_Postdata += "\r\n";
        header_Postdata += postData + "\r\n";

        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket clientSK = ssf.createSocket(host, 443);
        Writer out = new OutputStreamWriter(clientSK.getOutputStream(), "UTF-8");
        
        out.write(header_Postdata);
        out.flush();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSK.getInputStream(), "UTF-8"));
        String html = "";
        String line = null;
        while ((line = in.readLine()) != null) {
            html += (line + "\n");
            System.out.println(line);
            F_ile.writeStringToFile("C:\\Users\\vietduc\\Desktop\\vn\\queylogin3.html", line, false);
            if (line.contains("<html")) {
                break;
            }
        }
        return html;
    }
}
