package D_common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author vietduc
 */
public class D_ate {

    public static String getDateString(String format/*"MM/dd/yyyy HH:mm:ss"*/) {
        DateFormat df = new SimpleDateFormat(format);
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }
    
    
    public static void main(String []dfs){
//        D_common.Logger.Logger.print(D_ate.getDateString("MM/dd/yyyy HH:mm:ss"));
//        D_common.Logger.Logger.print(D_ate.getDateString("yyyy-MM-dd_HH-mm-ss"));
        System.out.println(new Date().getTime());
    }
}
