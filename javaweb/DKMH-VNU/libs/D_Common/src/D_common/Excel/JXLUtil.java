/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common.Excel;

import D_common.DataStructure.SimAccount;
import D_common.DataStructure.Pair;
import D_common.F_ile;
import D_common.S_tring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JXLUtil {
    
    public static Sheet getSheet(File file, int sheetNo) throws Exception {
        if ((file.exists()) && (file.length() > 0L)) {

            Workbook w = Workbook.getWorkbook(file);
            Sheet sheet = w.getSheet(sheetNo-1);
            return sheet;
        }
        throw new Exception();
    }
    
    public static ArrayList<String> getSmss(String filePath, int sheetNumber) throws Exception {
        D_common.Logger.Logger.print("__getSmss:"+filePath+" "+sheetNumber);
        Sheet iSheet = getSheet(new File(filePath), sheetNumber);
        ArrayList<String> arrSms = new ArrayList<>();
        int ROW = iSheet.getRows();
        for (int i = 0; i < ROW; i++) {
            jxl.Cell cellSimNo = iSheet.getCell(0, i);
            String text = cellSimNo.getContents();
            if (text.length()==0) continue;
            arrSms.add(text);

        }
        D_common.Logger.Logger.print("__getSmss: Return "+arrSms.size());
        return arrSms;
    }
    
    public static ArrayList<SimAccount> getAccountSims(String filePath, int sheetNumber) throws Exception {
        D_common.Logger.Logger.print("__getAccountSims:"+filePath+" "+sheetNumber);
        Sheet iSheet = getSheet(new File(filePath), sheetNumber);
        ArrayList<SimAccount> simAccounts = new ArrayList<>();
        int ROW = iSheet.getRows();
        for (int i = 0; i < ROW; i++) {

            String simNo = iSheet.getCell(0, i).getContents().trim();
            String password = iSheet.getCell(1, i).getContents().trim();
            
            simNo = simNo.replace(" ", "");
            simNo = simNo.replace("-", "");
            simNo = simNo.replace(".", "");
            password = password.replace(" ", "");
            password = password.replace("-", "");
            password = password.replace(".", "");
            
            if (simNo.length()<5 || password.length()==0) continue;
            if (!S_tring.isNumeric(simNo)) continue;
            
            if (simNo.startsWith("0")){
                simNo = simNo.substring(1);
            } else if (simNo.startsWith("84")){
                simNo = simNo.substring(2);
            } else if (simNo.startsWith("+84")){
                simNo = simNo.substring(3);
            }
            
            simNo = "84" + simNo;
            
            if (simNo.length() != 11 && simNo.length() != 12) continue;
            
            simAccounts.add(new SimAccount(simNo, password));
            
        }
        D_common.Logger.Logger.print("__getAccountSims: Return "+simAccounts.size());
        return simAccounts;
    }

    public static ArrayList< Pair<String, String> > getReceiveSimsContent(String filePath, int sheetNumber) throws Exception {
        D_common.Logger.Logger.print("__getReceiveSims:"+filePath+" "+sheetNumber);
        Sheet iSheet = getSheet(new File(filePath), sheetNumber);
        ArrayList< Pair<String, String> > receiveList = new ArrayList<>();
        int ROW = iSheet.getRows();
        for (int i = 0; i < ROW; i++) {

            String simNo = iSheet.getCell(0, i).getContents().trim();
            String content = iSheet.getCell(1, i).getContents().trim();
            
            simNo = simNo.replace(" ", "");
            simNo = simNo.replace("-", "");
            simNo = simNo.replace(".", "");
            
            if (simNo.length() < 5 || content.length() < 5) continue;
            
            if (!S_tring.isNumeric(simNo)) continue;
            
            if (simNo.startsWith("0")) {
                simNo = simNo.substring(1);
            } else if (simNo.startsWith("84")) {
                simNo = simNo.substring(2);
            } else if (simNo.startsWith("+84")) {
                simNo = simNo.substring(3);
            }
            
            simNo = "0" + simNo;
            
            if (simNo.length() != 10 && simNo.length() != 11) continue;
            
            //D_common.Logger.Logger.print("sim = " + simNo);
            
            receiveList.add(new Pair<>(simNo, content));   
        }
        D_common.Logger.Logger.print("__getReceiveSims: Return " + receiveList.size());
        return receiveList;
    }
    
    
    public static ArrayList<String> getReceiveSims(String filePath, int sheetNumber) throws Exception {
        D_common.Logger.Logger.print("__getReceiveSims:"+filePath+" "+sheetNumber);
        Sheet iSheet = getSheet(new File(filePath), sheetNumber);
        ArrayList<String> receiveList = new ArrayList<>();
        int ROW = iSheet.getRows();
        for (int i = 0; i < ROW; i++) {

            String simNo = iSheet.getCell(0, i).getContents().trim();
            
            simNo = simNo.replace(" ", "");
            simNo = simNo.replace("-", "");
            simNo = simNo.replace(".", "");
            
            if (simNo.length() < 5 ) continue;
            
            if (!S_tring.isNumeric(simNo)) continue;
            
            if (simNo.startsWith("0")) {
                simNo = simNo.substring(1);
            } else if (simNo.startsWith("84")) {
                simNo = simNo.substring(2);
            } else if (simNo.startsWith("+84")) {
                simNo = simNo.substring(3);
            }
            
            simNo = "0" + simNo;
            
            if (simNo.length() != 10 && simNo.length() != 11) continue;
            
            //D_common.Logger.Logger.print("sim = " + simNo);
            
            receiveList.add(simNo);
        }
        D_common.Logger.Logger.print("__getReceiveSims: Return " + receiveList.size());
        return receiveList;
    }
    
    public static void appendToXls(String filePath, ArrayList<Pair<Integer, String>> dataList) {
        try {
            WritableSheet sheet;
            WritableWorkbook workbook;
            if (!F_ile.checkFileExists(filePath)) {
                workbook = Workbook.createWorkbook(new File(filePath));
                sheet = workbook.createSheet("Ket qua", 0);
            } else {
                Workbook workbook1 = Workbook.getWorkbook(new File(filePath));
                workbook = Workbook.createWorkbook(new File(filePath), workbook1);
                sheet = workbook.getSheet(0);
            }
            final int ROW = sheet.getRows();
            for (Pair<Integer, String> data : dataList) {
                int col = data.getFirst();
                String value = data.getSecond();
                Label label = new Label(col - 1, ROW, value);
                sheet.addCell(label);
            }

            workbook.write();
            workbook.close();
        } catch (IOException | BiffException | IndexOutOfBoundsException | WriteException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String []arg) throws Exception{
    	ArrayList<Pair<Integer, String>> list = new ArrayList<>();
    	list.add(new Pair<Integer, String>(1, "11111"));
    	list.add(new Pair<Integer, String>(2, "22222"));
    	list.add(new Pair<Integer, String>(3, "33333"));
    	JXLUtil.appendToXls("C:\\Users\\vietduc\\Desktop\\out.xls", list);
    }
    
}
