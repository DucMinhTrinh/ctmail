/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common.DataStructure;

/**
 *
 * @author vu
 */
public class SimAccount {
    private String number;
    private String password;
    
    public SimAccount(String _number, String _pass) {
        this.number = _number;
        this.password = _pass;
    }
    public SimAccount(String _number) {
        this.number = _number;
        this.password = "";
    }
    
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
