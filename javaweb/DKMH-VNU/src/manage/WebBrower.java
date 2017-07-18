package manage;

import D_common.F_ile;
import java.io.IOException;
import config.Configration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

public class WebBrower {

    public static CookieStore cookieStore;

    //Các field cần thiết cho post login
    public static String RequestVerificationToken;

    public WebBrower() {
        RequestVerificationToken = "";
        cookieStore = new BasicCookieStore();
        D_common.F_ile.createFolder("res");
    }

    public static void printCookie() {
        D_common.Logger.Logger.print("CK:");
        cookieStore.getCookies().forEach((ck) -> {
            D_common.Logger.Logger.print(ck.toString());
        });

        D_common.Logger.Logger.print();
    }
//1.Get login page 

    public static boolean getHomePage() {
        try {
            D_common.Logger.Logger.print("__getHomePage");
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpGet get = new HttpGet(Configration.HomePage);
            get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            get.addHeader("Accept-Encoding", "gzip, deflate");
            get.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            get.addHeader("Connection", "keep-alive");
            get.addHeader("DNT", "1");
            get.addHeader("Host", "dangkyhoc.vnu.edu.vn");
            get.addHeader("Upgrade-Insecure-Requests", "1");
            get.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(get);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {
                RequestVerificationToken = D_common.S_tring.getValue(result, "<input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"", "\" />    <div class=\"box bordered-box");
                System.out.println(RequestVerificationToken);
                //D_common.Logger.Logger.print(result);
                F_ile.writeStringToFile("res\\HomePage.html", result, false);
                return true;
            }

            return false;
        } catch (IOException e) {
            D_common.Logger.Logger.print("Catched Exception:");
            D_common.Logger.Logger.print(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //2.Post login page
    public static boolean postLogin(String username, String password) {
        try {
            D_common.Logger.Logger.print("__postLogin");
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost(Configration.postLoginLink);
            post.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            post.addHeader("Accept-Encoding", "gzip, deflate");
            post.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            post.addHeader("Connection", "Connection");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            post.addHeader("DNT", "1");
            post.addHeader("Host", Configration.HOST);
            post.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/dang-nhap");
            post.addHeader("Upgrade-Insecure-Requests", "1");
            post.addHeader("User-Agent", Configration.USER_AGENT);

            String postData = "__RequestVerificationToken=mytoken&LoginName=username&Password=password";
            postData = postData.replace("username", username);
            postData = postData.replace("password", password);
            postData = postData.replace("mytoken", RequestVerificationToken);
            System.out.println(postData);
            HttpEntity entity = new ByteArrayEntity(postData.getBytes());
            post.setEntity(entity);
            HttpResponse response = httpclient.execute(post);
            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 302) {
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

//    Đăng kí nghành học 1
    public static boolean loadDangKyNghanhHoc1() {
        try {
            D_common.Logger.Logger.print("__getDangKyNghanhHoc1");
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpGet get = new HttpGet("http://dangkyhoc.vnu.edu.vn/dang-ky-mon-hoc-nganh-1");
            get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            get.addHeader("Accept-Encoding", "gzip, deflate");
            get.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            get.addHeader("Connection", "keep-alive");
            get.addHeader("DNT", "1");
            get.addHeader("Host", "dangkyhoc.vnu.edu.vn");
            get.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/");
            get.addHeader("Upgrade-Insecure-Requests", "1");
            get.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(get);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {

                F_ile.writeStringToFile("res\\loadDangKyNghanhHoc1.html", result, false);
                return true;
            }

            return false;
        } catch (IOException e) {
            D_common.Logger.Logger.print("Catched Exception:");
            D_common.Logger.Logger.print(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean postDanhSachMonHoc() {
        try {
            D_common.Logger.Logger.print("__post-Danh-sach-mon-hoc");
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost("http://dangkyhoc.vnu.edu.vn/danh-sach-mon-hoc/1/1");
            post.addHeader("Accept", "text/html, */*; q=0.01");
            post.addHeader("Accept-Encoding", "gzip, deflate");
            post.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            post.addHeader("Connection", "Connection");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            post.addHeader("DNT", "1");
            post.addHeader("Host", Configration.HOST);
            post.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/dang-ky-mon-hoc-nganh-1");
            post.addHeader("X-Requested-With", "XMLHttpRequest");
            post.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(post);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {

                F_ile.writeStringToFile("res\\loadDanhsachmonhoc.html", result, false);
                return true;
            }
            return false;
        } catch (IOException | IllegalStateException e) {
            return false;
        }
    }

    public static boolean Danh_sach_mon_hoc_da_dang_ky() {
        try {
            D_common.Logger.Logger.print("_____post-Danh-sach-mon-hoc_da_dang_ky");
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost("http://dangkyhoc.vnu.edu.vn/danh-sach-mon-hoc-da-dang-ky/1");
            post.addHeader("Accept", "text/html, */*; q=0.01");
            post.addHeader("Accept-Encoding", "gzip, deflate");
            post.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            post.addHeader("Connection", "Connection");
            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            post.addHeader("DNT", "1");
            post.addHeader("Host", Configration.HOST);
            post.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/dang-ky-mon-hoc-nganh-1");
            post.addHeader("X-Requested-With", "XMLHttpRequest");
            post.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(post);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {

                F_ile.writeStringToFile("res\\loadDanhsachmonhocdadangky.html", result, false);
                return true;
            }
            return false;
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject kiem_tra_dieu_kien_tien_quyet(String ma_id_mon_hoc) {
        try {
            String url = "http://dangkyhoc.vnu.edu.vn/kiem-tra-tien-quyet/" + ma_id_mon_hoc + "/1";
            D_common.Logger.Logger.print("_____post-Kiểm tra điều kiện tiên quyết môn học có mã là " + ma_id_mon_hoc);
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.addHeader("Accept-Encoding", "gzip, deflate");
            post.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            post.addHeader("Connection", "Connection");
            post.addHeader("DNT", "1");
            post.addHeader("Host", Configration.HOST);
            post.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/dang-ky-mon-hoc-nganh-1");
            post.addHeader("X-Requested-With", "XMLHttpRequest");
            post.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(post);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {
                F_ile.writeStringToFile("res\\kiemtradieukientienquyet.html", result, false);
                try {
                    JSONObject json = new JSONObject(result);
                    return json;
                } catch (JSONException e) {
                    return null;
                }

            }
            return null;
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject chon_mon_hoc(String index) {
        try {
            String url = "http://dangkyhoc.vnu.edu.vn/chon-mon-hoc/" + index + "/1/1";
            D_common.Logger.Logger.print("_____post-Chọn môn học có mã là " + index);
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.addHeader("Accept-Encoding", "gzip, deflate");
            post.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            post.addHeader("Connection", "Connection");
            post.addHeader("DNT", "1");
            post.addHeader("Host", Configration.HOST);
            post.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/dang-ky-mon-hoc-nganh-1");
            post.addHeader("X-Requested-With", "XMLHttpRequest");
            post.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(post);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {
                F_ile.writeStringToFile("res\\chonMonHoc.html", result, false);
                try {
                    JSONObject json = new JSONObject(result);
                    return json;
                } catch (JSONException e) {
                    return null;
                }

            }
            return null;
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public static JSONObject xac_nhan_dang_ky() {
        try {
            String url = "http://dangkyhoc.vnu.edu.vn/xac-nhan-dang-ky/1";
            D_common.Logger.Logger.print("_____post-Xác nhận đăng ký");
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.addHeader("Accept-Encoding", "gzip, deflate");
            post.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            post.addHeader("Connection", "Connection");
            post.addHeader("DNT", "1");
            post.addHeader("Host", Configration.HOST);
            post.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/dang-ky-mon-hoc-nganh-1");
            post.addHeader("X-Requested-With", "XMLHttpRequest");
            post.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(post);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {
                F_ile.writeStringToFile("xacNhanDangKy.html", result, false);
                try {
                    JSONObject json = new JSONObject(result);
                    return json;
                } catch (JSONException e) {
                    return null;
                }

            }
            return null;
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static JSONObject huy_dang_ky(String row_index) {
        try {
            String url = "http://dangkyhoc.vnu.edu.vn/huy-mon-hoc/"+row_index+"/1/1";
            D_common.Logger.Logger.print("_____post-Xác nhận đăng ký");
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.addHeader("Accept-Encoding", "gzip, deflate");
            post.addHeader("Accept-Language", "bg,vi;q=0.8,en-US;q=0.5,en;q=0.3");
            post.addHeader("Connection", "Connection");
            post.addHeader("DNT", "1");
            post.addHeader("Host", Configration.HOST);
            post.addHeader("Referer", "http://dangkyhoc.vnu.edu.vn/dang-ky-mon-hoc-nganh-1");
            post.addHeader("X-Requested-With", "XMLHttpRequest");
            post.addHeader("User-Agent", Configration.USER_AGENT);
            HttpResponse response = httpclient.execute(post);

            //Output when create request
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuilder.append(line);
            }
            String result = resultBuilder.toString();

            if (response.getStatusLine().getStatusCode() == 200) {
                F_ile.writeStringToFile("xacNhanDangKy.html", result, false);
                try {
                    JSONObject json = new JSONObject(result);
                    return json;
                } catch (JSONException e) {
                    return null;
                }

            }
            return null;
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List listAccount(int ma_nam_hoc, int ma_truong, int min, int max) {
        List listAccounter = new ArrayList();
        String head = "" + ma_nam_hoc + "0";
        head = head + String.valueOf(ma_truong);
        String tail = "";
        for (int i = min; i <= max; i++) {
            if (i < 10) {
                tail = "000" + i;
            } else if (i < 100) {
                tail = "00" + i;
            } else if (i < 1000) {
                tail = "0" + i;
            } else {
                tail = "" + i;
            }
            listAccounter.add(head + tail);
        }
        return listAccounter;
    }

    public static void main(String[] args) {
        WebBrower webbrower = new WebBrower();
        boolean getLoginPage = webbrower.getHomePage();
        webbrower.printCookie();
        if (getLoginPage) {
            boolean postLogin = webbrower.postLogin("16061073", "16061073");
            if (postLogin) {
                System.out.println("Login thành công");
                webbrower.printCookie();
                boolean loadDangKyNghanh1 = webbrower.loadDangKyNghanhHoc1();
                if (loadDangKyNghanh1) {
                    boolean danhsachmonhoc = webbrower.postDanhSachMonHoc();
                    if (danhsachmonhoc) {
                        System.out.println("Load danh sách môn học thành công");

                    }

                    boolean danhSachDaDangKy = webbrower.Danh_sach_mon_hoc_da_dang_ky();
                    if (danhSachDaDangKy) {
                        System.out.println("Load danh sách môn học đã đăng ký thành công");
                        JSONObject kiem_tra_tien_quyet = webbrower.kiem_tra_dieu_kien_tien_quyet("0000096");
                        System.out.println(kiem_tra_tien_quyet);
                        JSONObject chon_mon_hoc = webbrower.chon_mon_hoc("11");
                        System.out.println(chon_mon_hoc);
                        JSONObject xacNhanDangKy = WebBrower.xac_nhan_dang_ky();
                        System.out.println(xacNhanDangKy);
                        JSONObject huyMonHoc = WebBrower.huy_dang_ky("2");
                        xacNhanDangKy = WebBrower.xac_nhan_dang_ky();
                        System.out.println(xacNhanDangKy);
                    }

                }

            }
        }
    }
}
