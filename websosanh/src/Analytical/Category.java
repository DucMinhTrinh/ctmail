/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analytical;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MinhPC
 */
public class Category {
    private String name;
    private List<SubCategory> subCategoryList = null;

    public Category() {
        name = null;
        subCategoryList = new ArrayList<SubCategory>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
