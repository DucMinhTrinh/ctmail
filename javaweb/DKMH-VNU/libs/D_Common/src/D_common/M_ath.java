/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common;

/**
 *
 * @author vietduc
 */
public class M_ath {
    public static boolean hit(double d) {
        return Math.random() < d;
    }
    
    public static int getrd() {
        return (int) (Math.random() * 1000000);
    }
    
}
