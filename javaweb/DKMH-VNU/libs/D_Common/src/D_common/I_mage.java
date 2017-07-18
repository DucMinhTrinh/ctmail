/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

/**
 *
 * @author vietduc
 */
public class I_mage {
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
        return Scalr.resize(image, Mode.AUTOMATIC, width, height, Scalr.OP_ANTIALIAS);
    }
    
    public static BufferedImage cropImage(BufferedImage src, int width, int height) {
        BufferedImage dest = src.getSubimage(0, 0, width, height);
        return dest;
    }
    
    public static BufferedImage cropSquareType1(BufferedImage src) {
        try {
            int width = src.getWidth();
            int height = src.getHeight();
            if (width < height) {
                return src.getSubimage(0, 0, width, width);
            } else if (width > height) {
                return src.getSubimage((width - height) / 2, 0, height, height);
            } else {
                return src;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void main(String[] args) throws IOException {
        /* test scale image
        BufferedImage img1 = ImageIO.read(new File("C:\\Users\\vietduc\\Desktop\\g1.jpg"));
        BufferedImage img2 = I_mage.getScaledImage(img1, 300, 200);
        File outputfile = new File("C:\\Users\\vietduc\\Desktop\\g2.jpg");
        ImageIO.write(img2, "JPG", outputfile);         
        */
        
        String image_path = "/Users/danny/Desktop/av_nam";
        String image_out = "/Users/danny/workspace/nb/http_service/toeic_campaign_api/img_avata_2";
        
        String[] files = F_ile.listFileInfolder(image_path);
        
        for (int i = 0; i < files.length; i++) {
            System.out.println("img: " + i + " - " + files[i]);
            BufferedImage img_in_i = ImageIO.read(new File(image_path + "/" + files[i])); 
            BufferedImage img_out_i = cropSquareType1(img_in_i);
            if (img_out_i != null) {
                ImageIO.write(img_out_i, "JPG", new File(image_out + "/avata_n_" + i + ".jpg"));
            }
        }
        
        
        
    }
    
    
}
