
import java.util.List;
import manage.WebBrower;
public class CheckLogin {
    public static void main(String[] args) {
         List listAcc = WebBrower.listAccount(16, 6, 0, 9999);
        for (int i = 0; i < listAcc.size(); i++) {
            boolean getLoginPage = WebBrower.getHomePage();
            if (getLoginPage) {
                boolean postLogin = WebBrower.postLogin(listAcc.get(i).toString(), listAcc.get(i).toString());
                if (postLogin) {
                    System.out.println(listAcc.get(i).toString());
                }
            }
        }
    }
}
