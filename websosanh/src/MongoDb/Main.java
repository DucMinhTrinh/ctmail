/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MongoDb;

import Analytical.Parser;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MinhPC
 */
public class Main {
    public static void main(String[] args) throws JSONException, UnknownHostException {
        
         long startTime = System.nanoTime();
         
            //Init connect mongodb
            MongoClient mongoClient=new MongoClient(MongoDb.Config.HOST,MongoDb.Config.defaultPort);
            DB db=mongoClient.getDB(MongoDb.Config.Schema);
            String colletioName = "Category";
            DBCollection collections = db.getCollection(colletioName);
            
            System.out.println(collections.count());
            DBCollection subCategoryCollection = db.getCollection("subCategory");
            
            
            
            List categoryList = Parser.getJSONCategory();
            for (int i = 0; i < categoryList.size(); i++) {
                
                //Insert Category
                JSONObject jsonCategoty = (JSONObject)categoryList.get(i);
                String categoryName     = jsonCategoty.getString("categoryName");
                JSONObject jsonOfCatrgory        = new JSONObject();
                jsonOfCatrgory.put("categoryName", categoryName);
                String _idCategory = MongoDBController.addDocumentFromJsonToJsonObject(jsonOfCatrgory, collections);
                System.out.println(_idCategory);
                if(_idCategory.length()>5){
                    System.out.println("Đã insert category");
                    JSONArray   subCategoryArray = jsonCategoty.getJSONArray("subList");

                    for (int j = 0; j < subCategoryArray.length(); j++) {
                        JSONObject jsonSubCategory = subCategoryArray.getJSONObject(j);
                        jsonSubCategory.put("categoryId", _idCategory);
                        String _idSubCategory = MongoDBController.addDocumentFromJsonToJsonObject(jsonSubCategory, subCategoryCollection);
                        if(_idSubCategory.length()>5){
                            System.out.println("Đã insert subcategory");
                        }
                    }
                }
            }
        
    }
}
