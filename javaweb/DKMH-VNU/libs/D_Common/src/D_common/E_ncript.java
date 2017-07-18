/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common;

import java.math.BigInteger;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author vietduc
 */
public class E_ncript {

    private static final int KEYLENGTH = 16; // * 8 = 128

    public static String getMd5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getKey(String hddSn, int appId) {
        return getMd5(hddSn + appId);
    }
    
    // encryption
    
    public static byte[] makeAESKey(String keyStr) {
        byte[] key = keyStr.getBytes();
        byte[] key2 = new byte[KEYLENGTH];
        for (int i = 0; i < KEYLENGTH; i++) {
            if (i < key.length) {
                key2[i] = key[i];
            } else {
                key2[i] = 0;
            }
        }
        return key2;
    }
    
    public static byte[] encrypt(byte [] data, String keyStr) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(makeAESKey(keyStr), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptString(String strToEncrypt, String keyStr) {
        try {
            return Base64.encodeBase64String(encrypt(strToEncrypt.getBytes(), keyStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    
    public static byte[] decrypt(byte [] data, String keyStr) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(makeAESKey(keyStr), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String decryptString(String strToDecrypt, String keyStr) {
        try {
            String ret = new String(decrypt(Base64.decodeBase64(strToDecrypt), keyStr), "UTF-8");
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String [] agrs) {
        String txt = "836bAuGZo9rvA9GZBonoSw==";
        //String enStr = E_ncript.encryptString(txt, "d@");
        String desStr = E_ncript.decryptString(txt, "tcp@");
        
        System.out.println(txt + "_" + desStr);
    }

}
