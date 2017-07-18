/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common.E_xample;

import D_common.F_ile;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author vu
 */
public class FileBrowser {
    public static final int TypeBrowserOpen = 0;
    public static final int TypeBrowserSave = 1;
        
    public static FileBrowser PRC_EXLS;
    public static final String filePath = "path.txt";

    public static String getSavedDir() {
        File pathFile = new File(filePath);
        String savedPath;
        if (pathFile.exists()) {
            savedPath = F_ile.getContentOfFile(pathFile).trim();
            if (savedPath.trim().length()==0) {
                savedPath = System.getProperty("user.dir");
            }
        } else {
            savedPath = System.getProperty("user.dir");
            F_ile.writeStringToFile(filePath, savedPath, false);
        }
        return savedPath;
    }

    public static void setFileFilter(JFileChooser fileChooser, final String ext) {
        if (ext != null) {
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public String getDescription() {
                    return "*" + ext;
                }

                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }

                    return f.getName().contains(ext);
                }
            });
        }
    }

    public static String browseFile(JComponent jComponent, int type, String fileTypeFilter/*".xls"*/) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(getSavedDir()));
        setFileFilter(jFileChooser, fileTypeFilter);
        String newFilePath = "";
        int rVal = 0;
        if (type == TypeBrowserOpen) {
            rVal = jFileChooser.showOpenDialog(jComponent);
        } else {
            rVal = jFileChooser.showSaveDialog(jComponent);
        }
        if (rVal == 0) {
            newFilePath = jFileChooser.getSelectedFile().getAbsolutePath();
            F_ile.writeStringToFile(filePath, jFileChooser.getCurrentDirectory().toString(), false);
        }
        return newFilePath;
    }
    
    public static void main(String [] args){
        String res = FileBrowser.browseFile(null, FileBrowser.TypeBrowserOpen, ".xls");
        D_common.Logger.Logger.print(res);
    }

}
