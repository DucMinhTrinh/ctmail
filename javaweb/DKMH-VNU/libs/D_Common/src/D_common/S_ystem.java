/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;


import java.io.InputStreamReader;

/**
 *
 * @author vietduc
 */
public class S_ystem {
    public static String getHardriveSerialNumber(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
    public static String getMotherboardSerialNumber() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs
                    = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
    
    public static void dsleep(int mss) {
        try {
            Thread.sleep(mss);
        } catch (InterruptedException ex) {
        }
    }
    
    public static String getJavaHome() {
        return System.getProperty("java.home");
    }
    
    public static String testThrowException() throws Exception {
        throw new Exception("test getStackTree");
    }
    
    public static String getStackTree(Exception e) {
        String [] filter = {"."};
        return getStackTree(e, filter);
    }
    
    public static String getStackTree(Exception e, String [] filter) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder stackTraceElementStrings = new StringBuilder();
        stackTraceElementStrings.append(System.lineSeparator());
        stackTraceElementStrings.append(">>>");
        stackTraceElementStrings.append(e.getClass().toString()).append(":").append(e.getMessage());
        stackTraceElementStrings.append(System.lineSeparator());
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            String stackTraceElementString = "" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()
                    + "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
            for (int fi = 0; fi < filter.length; fi++) {
                if (stackTraceElementString.contains(filter[fi])) {
                    stackTraceElementStrings.append(">>>");
                    stackTraceElementStrings.append(stackTraceElementString);
                    stackTraceElementStrings.append(System.lineSeparator());
                    break;
                }
            }
        }

        return stackTraceElementStrings.toString();
    }
    
    public static void main(String[] args) {
        try {
            testThrowException();
        } catch (Exception e) {
            e.getMessage();
            System.out.println(D_common.S_ystem.getStackTree(e));
        }
    }
}
