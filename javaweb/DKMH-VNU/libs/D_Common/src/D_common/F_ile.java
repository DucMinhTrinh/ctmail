/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author vietduc
 */
public class F_ile {
    public static void writeStringToFile(String filePath, String value, boolean append) {
        Writer out2 = null;
        try {
            File file = new File(filePath);
            if (!file.exists()){
                F_ile.createFolder(file.getParent());
            }
            out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), "UTF-8"));
            out2.write(value);
            out2.write(System.lineSeparator());
            out2.close();
        } catch (Exception ex) {
            System.out.println("Error write to file");
            ex.printStackTrace();
        } finally {
            try {
                if (out2 != null) out2.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void addStringToHeadFile(String filePath, String str) {
        String oldStr = getContentOfFile(filePath);
        writeStringToFile(filePath, str + System.lineSeparator() + oldStr, false);
    }
    
    public static void createFolder(String hardPath/*"C:\\Directory2\\Sub2\\Sub-Sub2"*/) {
        try{
            if (hardPath == null || hardPath.equals("")){
                return;
            }
            File files = new File(hardPath);
            if (!files.exists()) {
                if (files.mkdirs()) {
                    D_common.Logger.Logger.print("Multiple directories are created!");
                } else {
                    D_common.Logger.Logger.print("Failed to create multiple directories!");
                }
            }
        } catch (Exception e){
            
        }
    }
    
    public static boolean checkFileExists(String hardPath/*"C:\\Directory2\\Sub2\\Sub-Sub2"*/){
        File files = new File(hardPath);
        return files.exists();
    }
    
    public static String getContentOfFile(String filePath){
        return getContentOfFile(new File(filePath));
    }
    
    public static String getContentOfFile(File file) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            StringBuilder resultBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                resultBuilder.append(line).append(System.lineSeparator());
            }
            in.close();
            return resultBuilder.toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String[] listFileInfolder(String folder) {
        File file = new File(folder);
        return file.list();
    }
    
    public static void deleteFileInsideFolder(String folder){
        File file = new File(folder);
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (String myFile1 : myFiles) {
                File myFile = new File(file, myFile1);
                myFile.delete();
            }
        }
    }
    
    public static long getFileSize(String file_name) {
        return getFileSize(new File(file_name));
    }
    
    public static long getFileSize(File file) {
        return file.length();
    }
    
    public static void copyFile(String source, String dest) throws IOException {
        copyFile(new File(source), new File(dest));
    }
    
    private static void copyFile(File source, File dest) throws IOException {
        FileUtils.copyFile(source, dest);
    }
    
    public static void main(String[] args) throws Exception{
        //deleteFileInsideFolder("New folder");
        //addStringToHeadFile("test.html", "<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n</head>");
        //System.out.println(getFileSize("test.html"));
        Collection<File> files = FileUtils.listFiles(new File("."), null, null);
        
        for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
            File next = iterator.next();
            System.out.println(next.getName());
        }
    }
    
    
    
    
    
}
