/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analytical;


/**
 *
 * @author MinhPC
 */
public class SubCategory {
    private String name;
    private String url;
    
    

    public SubCategory(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public SubCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
