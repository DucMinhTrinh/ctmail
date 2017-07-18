
package D_common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

public class H_ttp {
    public static String postData(String urls, String data, int c) {
	try {
	    URL url = new URL(urls);
	    URLConnection con = url.openConnection();

	    con.setDoOutput(true);

	    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
	    wr.write(data);
	    wr.flush();


	    String str = "";
	    String line;
	    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    while ((line = in.readLine()) != null) {
		str += line + "\n";
		if (line.indexOf("</html>") >= 0) {
		    break;
		}
	    }
	    in.close();
	    wr.close();

	    return str;
	} catch (Exception e) {
	    if (c < 20) {
		return postData(urls, data, c + 1);
	    } else {
		return "";
	    }
	}
    }

    public static String postData(String urls, String data) {
	return postData(urls, data, 0);
    }

    public static String getData(String urls) {
        try {
	    URL url = new URL(urls);
	    URLConnection con = url.openConnection();
	    String str = "";
	    String line;
	    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    while ((line = in.readLine()) != null) {
		str += line + "\n";
		if (line.indexOf("</html>") >= 0) {
		    break;
		}
	    }
            return str;
        } catch (Exception e){
            return "";
        }
    }
    
    public static void printHttpHeaderResponce(HttpResponse response){
        System.out.println("Response Line:" + response.getStatusLine().toString());
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName() + ": " + header.getValue());
        }
        System.out.println();
    }
    public static boolean checkIsReachable(String urlLink) {
        try {
            URL url = new URL(urlLink);
            URLConnection connection = url.openConnection();

            if (connection.getContentLength() == -1) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    
    public static boolean download(String url, String filename) {
        try {
            return download(new URL(url), new File(filename));
        } catch (Exception e){
            return false;
        }
    }
    public static boolean download(URL url, File dstFile) {
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy()) // adds HTTP REDIRECT support to GET and POST methods 
                .build();
        try {
            HttpGet get = new HttpGet(url.toURI()); // we're using GET but it could be via POST as well
            File downloaded = httpclient.execute(get, new FileDownloadResponseHandler(dstFile));
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            IOUtils.closeQuietly(httpclient);
        }
    }

    static class FileDownloadResponseHandler implements ResponseHandler<File> {

        private final File target;

        public FileDownloadResponseHandler(File target) {
            this.target = target;
        }

        @Override
        public File handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            InputStream source = response.getEntity().getContent();
            System.out.println("downloading...");
            FileUtils.copyInputStreamToFile(source, this.target);
            return this.target;
        }
        
    }
    public static void main(String []sdsfd){
        String link = "https://redirector.googlevideo.com/videoplayback?requiressl=yes&id=2af5345abcd11f05&itag=22&source=webdrive&ttl=transient&app=hdodriveapp&ip=210.245.18.137&ipbits=0&expire=1483817635&sparams=requiressl%2Cid%2Citag%2Csource%2Cttl%2Cip%2Cipbits%2Cexpire%2Cmm%2Cmn%2Cms%2Cmv%2Cpl&signature=B49058E4D60BAEA347A00DC16915F541167C3F9B.BA02BC228AFC6388A3280268DFF5B5F43924EA87&key=ck2&mm=31&mn=sn-42u-nbos&ms=au&mt=1483803134&mv=u&pl=24";
        H_ttp.download(link, "googlevideo");
    }
    
}
