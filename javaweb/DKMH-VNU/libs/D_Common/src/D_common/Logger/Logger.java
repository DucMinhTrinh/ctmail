/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common.Logger;

import D_common.D_ate;
import D_common.F_ile;

/**
 *
 * @author vietduc
 */
public class Logger {
    private static String logFileName = "";
    private static final String logFolder = "logs";
    
    public static void print(String str){
        if (logFileName.equals("")){
            F_ile.createFolder(logFolder);
            logFileName = logFolder + "/log_" + D_ate.getDateString("yyyy-MM-dd_HH-mm-ss") + ".txt";
            
        }
        
        F_ile.writeStringToFile(logFileName, "[" + D_ate.getDateString("yyyy-MM-dd HH:mm:ss") + "] " + str, true);
        System.out.println(str);
        
    }
    
    public static void print(){
        D_common.Logger.Logger.print(System.lineSeparator());
    }
    
    public static void main(String [] args){
        D_common.Logger.Logger.print("123abcabcsdf");
        D_common.Logger.Logger.print("1");
    }
}
