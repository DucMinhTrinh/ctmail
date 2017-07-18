package D_common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;

public class S_tring {
    public static String getValue(String str, String pretxt, int lengthPreTxt, String endtxt, String def) {
	try {
	    String ret = "";
	    int d1 = str.indexOf(pretxt);
	    if (d1 < 0) {
		return def;
	    }
	    String str2 = str.substring(d1 + lengthPreTxt);
	    int d2 = str2.indexOf(endtxt);
	    if (d2 < 0) {
		return def;
	    }
	    ret = str2.substring(0, d2);
	    return ret;
	} catch (Exception e) {
	    return def;
	}
    }
    
    public static String getValue(String str, String pretxt, String endtxt, String def) {
        return S_tring.getValue(str, pretxt, pretxt.length(), endtxt, def);
    }
    
    public static String getValue(String str, String pretxt, String endtxt){
        return S_tring.getValue(str, pretxt, pretxt.length(), endtxt, "");
    }
    
    public static String inputStream2String(InputStream in) throws IOException{
        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();

        while(read != null) {
            sb.append(read);
            read = br.readLine();
        }
        return sb.toString();
    }
    
    public static String filterLetterNumber(String string) {
        String ret = "";
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if ((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9')) {
                ret += ch;
            }
        }
        return ret;
    }
    
    public static String convertStringToPost(String string) {
	String convertedStr = "";
	for (int i = 0; i < string.length(); i++) {
	    if ((string.charAt(i) == '_') || ((string.charAt(i) >= 'a') && (string.charAt(i) <= 'z')) || ((string.charAt(i) >= 'A') && (string.charAt(i) <= 'Z')) || ((string.charAt(i) >= '0') && (string.charAt(i) <= '9'))) {
		convertedStr = convertedStr + string.charAt(i);
	    } else {
		int c = string.charAt(i);
		convertedStr = convertedStr + "%" + Integer.toHexString(c).toUpperCase();
	    }
	}
	return convertedStr;
    }
    
    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    public static String convertUtfToAscii(String str) throws Exception {
        char[] preStr = Hex.encodeHex(str.getBytes(), false);
        String ret = "";
        for (int i = 0; i < preStr.length / 2; i++) {
            int j = i * 2;
            char[] twoDigit = {preStr[j], preStr[j + 1]};
            String s = new String(Hex.decodeHex(twoDigit));
            String s1 = "";
            if (s.length() == 1 && s.charAt(0) == ' ') {
                s1 = "+";
            } else if (s.length() == 1 && ((s.charAt(0) >= 'A' && s.charAt(0) <= 'z') || (s.charAt(0) >= '0' && s.charAt(0) <= '9'))) {
                s1 = s;
            } else {
                s1 = "%";
                s1 += preStr[j];
                s1 += preStr[j + 1];
            }
            ret += s1;
        }
        return ret;
    }
    
    public static String getLastString(String str, int lenght_) {
        return str.substring(str.length() - lenght_, str.length());
    }

    public static String ofucatorStringContent(String content, int maxlen) {
        if (!content.contains(" ")) {
            return content;
        }
        String [] content_word_list = content.split(" ");
        
        char [] spec_char = {'~', '~', '-', '*', ',', '.', '+', '+'};
        int n_spec_char = spec_char.length;
        
        char [] lnk_char = {'-', '-', '.', '-', '+', '+'};
        int n_lnk_char = lnk_char.length;
        
        String ret = "";
        for (int i = 0; i < content_word_list.length; i++) {
            String content_word = content_word_list[i];
            if (!content_word.contains("http://") && !content_word.contains("www.")) {
                if (content_word.length() <= 3) {
                    if (M_ath.hit(0.3)) {
                        char add_s = spec_char[M_ath.getrd() % n_spec_char];
                        if (M_ath.hit(0.5)) {
                            content_word = content_word + add_s;
                            if (M_ath.hit(0.4)) {
                                content_word = content_word + " ";
                            }
                        } else {
                            content_word = add_s + content_word;
                            if (M_ath.hit(0.4)) {
                                content_word = " " + content_word;
                            }
                        }
                    }
                } else {
                    if (M_ath.hit(0.55)) {
                        if (M_ath.hit(0.2)) {
                            char[] content_word_chars = content_word.toCharArray();
                            for (int j = 0; j < content_word_chars.length; j++) {
                                if (content_word_chars[j] == 'u'
                                        || content_word_chars[j] == 'e'
                                        || content_word_chars[j] == 'o'
                                        || content_word_chars[j] == 'a'
                                        || content_word_chars[j] == 'i') {
                                    
//                                    if (M_ath.hit(0.6)) {
//                                        content_word_chars[j] = '?';
//                                    } else {
//                                        content_word_chars[j] = '-';
//                                    }
                                    break;
                                }
                            }
                            content_word = String.valueOf(content_word_chars);
                        } else if (M_ath.hit(0.4)) {
                            char[] content_word_chars = content_word.toCharArray();
                            int sw_index = M_ath.getrd() % (content_word_chars.length - 1);
                            content_word = "";
                            for (int j = 0; j < content_word_chars.length; j++) {
                                content_word = content_word + content_word_chars[j];
                                if (sw_index == j) {
                                    if (M_ath.hit(0.5)) {
                                        content_word = content_word + ".";
                                    } else {
                                        content_word = content_word + "-";
                                    }
                                }
                            }
                        }  else if (M_ath.hit(0.5) && content_word.length() > 5) {
                            char[] content_word_chars = content_word.toCharArray();
                            int sw_index = 1 + (M_ath.getrd() % (content_word_chars.length - 3 ));
                            char c1 = content_word_chars[sw_index];
                            char c2 = content_word_chars[sw_index + 1];
                            content_word_chars[sw_index] = c2;
                            content_word_chars[sw_index + 1] = c1;
                            content_word = String.valueOf(content_word_chars);
                        }
                    } else if (M_ath.hit(0.5)) {
                        char[] content_word_chars = content_word.toCharArray();
                        for (int j = 0; j < content_word_chars.length; j++) {
                            if (content_word_chars[j] == '0' && M_ath.hit(0.6)) {
                                content_word_chars[j] = 'o';
                            }
                            if (content_word_chars[j] == 'o' && M_ath.hit(0.6)) {
                                content_word_chars[j] = '0';
                            }
                            if (content_word_chars[j] == 'O' && M_ath.hit(0.6)) {
                                content_word_chars[j] = '0';
                            }
                            if (content_word_chars[j] == 'l' && M_ath.hit(0.6)) {
                                content_word_chars[j] = 'I';
                            }
                            if (content_word_chars[j] == '0' && M_ath.hit(0.6)) {
                                content_word_chars[j] = 'O';
                            }
                        }
                        content_word = String.valueOf(content_word_chars);
                    }
                }
                if (M_ath.hit(0.5)) {
                    char add_s = lnk_char[M_ath.getrd() % n_lnk_char];
                    ret = ret + content_word + add_s;
                    if (M_ath.hit(0.5)) {
                        ret = ret + " ";
                    }
                } else {
                    ret = ret + content_word + " ";
                }
            } else {
                ret = ret + " " + content_word + " "; 
            }
        }
        ret = ret.replace("  ", " ");
        ret = ret.replace("--", "-");
        if (ret.length() > maxlen) return ofucatorStringContent(content, maxlen);
        return ret.trim();
    }
    
    //
    private static char[] SOURCE_CHARACTERS = { 'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự', };
    private static char[] DESTINATION_CHARACTERS = { 'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u', };

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }
    
    public static String removeSpecialCharacters(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ((ch <= 'z' && ch >= 'a') || 
                    (ch <= 'Z' && ch >= 'A') ||
                    (ch <= '9' && ch >= '0')) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    
    public static String changeSpecialCharacters(String s) {
        s = removeAccent(s);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ((ch <= 'z' && ch >= 'a') || 
                    (ch <= 'Z' && ch >= 'A') ||
                    (ch <= '9' && ch >= '0')) {
                sb.append(ch);
            } else {
                sb.append(" ");
            }
        }
        return sb.toString().trim().replaceAll(" ", "_");
    }
    
    
    
    public static String uriFromUrl(String url) {
        int index1 = url.lastIndexOf("/");
        if (index1 >=0) {
            String uri1 = url.substring(index1);
            return changeSpecialCharacters(uri1);
        }
        return changeSpecialCharacters(url);
    }
    
    public static String getFileType(String filename) {
        try {
            for (int i = filename.length() -1; i >= 0; i--){
                if (filename.charAt(i) == '.') {
                    return filename.substring(i + 1);
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }
    //
    
    public static String insertToString(String beginStr, String insertStr, int ind) {
        String s1 = beginStr.substring(0, ind);
        String s2 = beginStr.substring(ind);
        
        return s1+insertStr+s2;
    }
    
    public static void main(String[] args) {
        //String str = "1,2,3,4,5,6";
        //System.out.println(str.replace(",", ", "));
        //D_common.Logger.Logger.print(S_tring.convertStringToPost("a=3l ong+45("));
        //D_common.Logger.Logger.print(removeAccent("Số thuê bao có serial đăng ký không đúng"));
        
        //System.out.println(ofucatorStringContent("Moi ban su dung ung dung hoc toeic cho nhan vien Viettel tren ios: www.happyphone.vn/toeic-campaign/", 125));
        
        
        //System.out.println(removeSpecialCharacters("http://n10.hdonline.vn/4efba61a37d83ebce1f5785ecab857ce/s2/012015/25/On_Her_Majesty_s_Secret_Service_1969_BluRay_1080p_DTS_x264_CHD/playlist_m.m3u8"));
        String url1 = "/playlist_m.m3u8";

        System.out.println(changeSpecialCharacters(url1));
        
    }
}
