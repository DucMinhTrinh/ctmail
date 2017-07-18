/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common;


public class A_pp {
    public static final int SMSVINAPHONE = 0;
    public static final int SMSVIETTEL = 1;
    public static final int SMSMOBI = 2;
    public static final int DKTTMOBI = 13;
    public static final int DKTT_GSM_VIETTEL = 14;
    public static final int V_PN_SEARCH = 21;
    public static final int V_PN_CUSTOMER = 22;
    public static final int V_PN_CUSTOMER_EXP_KIT = 23;
    public static final int EXCEL_EDIT = 30;

    public static final String SMSVINAPHONE_TXT = "SMSVINAPHONE";
    public static final String SMSVIETTEL_TXT = "SMSVIETTEL";
    public static final String SMSMOBI_TXT = "SMSMOBI";
    public static final String DKTTMOBI_TXT = "DKTTMOBI";
    public static final String DKTT_GSM_VIETTEL_TXT = "DKTT_GSM_VIETTEL";
    public static final String V_PN_SEARCH_TXT = "VIETTEL_TCTT";
    public static final String V_PN_CUSTOMER_TXT = "VIETTEL_CUSTOMER";
    public static final String V_PN_CUSTOMER_EXP_KIT_TXT = "VIETTEL_CUSTOMER_EXPKIT";
    public static final String EXCEL_EDIT_TXT = "EXCELEDITOR";
    

    public static final int[] APPLIST = {SMSVINAPHONE, SMSVIETTEL, SMSMOBI, DKTTMOBI, DKTT_GSM_VIETTEL, V_PN_SEARCH, V_PN_CUSTOMER, V_PN_CUSTOMER_EXP_KIT, EXCEL_EDIT};
    public static final String[] APPLIST_TXT = {SMSVINAPHONE_TXT, SMSVIETTEL_TXT, SMSMOBI_TXT, DKTTMOBI_TXT, DKTT_GSM_VIETTEL_TXT, V_PN_SEARCH_TXT, V_PN_CUSTOMER_TXT, V_PN_CUSTOMER_EXP_KIT_TXT, EXCEL_EDIT_TXT};
    
    public static void main(String [] args) {
        String hashValue = D_common.E_ncript.getKey("-486841425", 31).trim();
        System.err.println(hashValue);
    }
}
