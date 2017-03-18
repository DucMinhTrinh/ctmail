/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analytical;

import Configration.AjaxPost;
import Configration.Hosting;
import MongoDb.Main;
import MongoDb.MongoDBController;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author MinhPC
 */
public class Parser {
    
    //Get all product category
    public static List getJSONCategory() throws JSONException{
        List category = new ArrayList();
        try{
            Response response = Jsoup.connect(new Hosting().HOSTNAME)
            .userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
            .method(Connection.Method.GET)
            .execute();
            if(response.statusCode()==200){
                
                //if get success homepage
                Document homepage = response.parse();
                
                //Find all node have tag name ul and class by hidden
                Elements categoryList = homepage.getElementsByClass("hidden");
                for (int i = 0; i < categoryList.size(); i++) {
                    if(!categoryList.get(i).tagName().equals("ul")){
                        categoryList.remove(i);
                    }
                }
                categoryList.remove(categoryList.size()-1);
                
                List<JSONObject> cateList = new ArrayList<JSONObject>();
                for (int i = 0; i < categoryList.size(); i++) {
                    String categoryName = categoryList.get(i).previousElementSibling().text();
                    JSONObject first = new JSONObject();
                    first.put("categoryName",categoryName);
                    List<JSONObject> subcategoryList = new ArrayList<JSONObject>();
                    Elements a = categoryList.get(i).getElementsByTag("a");
                    for (int j = 0; j < a.size(); j++) {
                        JSONObject sub = new JSONObject();
                        sub.put("subname",a.get(j).text());
                        sub.put("suburl",a.get(j).attr("href"));
                        subcategoryList.add(sub);
                    }
                    first.put("subList",subcategoryList);
                    cateList.add(first);
                }
                return cateList;
                
            }
        }catch(IOException e){
            
        }
        
        return category;
    }
    
    
    /*Get the number max size webpage*/
    public static int getTheNumberMaxOfWebPage(Document pagehtml){
        int maxPage = 0;
        Element page = pagehtml.getElementsByAttributeValue("title", "Trang cuối").get(0);
        String max   = page.attr("data-page-index");
        maxPage = Integer.parseInt(max);
        return maxPage;
    }
    
    /**Get all link from a page of sub categories
     * @param url is the link of a page sub categories
     * @return  **/
    public static List getAllLinkOfAPageOfSubCategories(String url){
        List list = new ArrayList();
        try{
            Document page = Jsoup.connect(url).method(Connection.Method.GET).execute().parse();
            Elements listLink = page.getElementsByClass("item ");
            for (int i = 0; i < listLink.size(); i++) {
                list.add(listLink.get(i).getElementsByTag("a").get(0).attr("href"));
            }
            return list;
        }catch(IOException e){
            return null;
        }
       
    }
    
    //Remove the same link
    public static List removeDuplicate(List arrList)
    {
        HashSet h = new HashSet(arrList);
        arrList.clear();
        arrList.addAll(h);
        return arrList;
    }
    
    //Get next url
    public static String getNextUrl(String url,int i){
       return url.substring(0, url.lastIndexOf(".")) +"?pi="+i+".htm";
    }
    
    //Get all url detail of a sub categorie
    public static List getAllDetailUrlProductOfSubCotegory(String subCategoryUrl){
       String url = subCategoryUrl;
       List<List> total = new ArrayList<List>();
       total.add(Parser.getAllLinkOfAPageOfSubCategories(url));
       int k = 2;
       String suburl;
       while( k  <= 28){
           suburl = Parser.getNextUrl(url, k);
           total.add(Parser.getAllLinkOfAPageOfSubCategories(suburl));
           k++;
       }
        Parser.removeDuplicate(total);
        return total;
    }
    
    
    //Phân tích một product page
    public static JSONObject getDetailProduct(String category,String subcategory,String url) throws JSONException{
        JSONObject total = new JSONObject();
        JSONObject json = new JSONObject();
       
        total.put("subCategory", subcategory);
        total.put("category", category);
         /**Start analytical**/
        try{
            Response response = Jsoup.connect(url).method(Connection.Method.GET).execute();
            Document page = response.parse();
            
            //Get name product
            String nameProduct = page.getElementsByClass("page-detail").get(0).getElementsByTag("h1").get(0).text();
//            System.out.println("Product Name :" + nameProduct);
            json.put("productName", nameProduct);
            
            //Image List of the product
            List<String> imageList = new ArrayList<String>();
            Elements      thumbnail = page.getElementsByClass("thumbnail").get(0).getElementsByTag("img");
            for (int i = 0; i < thumbnail.size(); i++) {
                imageList.add(thumbnail.get(i).attr("src"));
            }
            json.put("ListImage", imageList);
            //Information of Product
            List<String> informationList = new ArrayList<String>();
            Elements      information    = page.getElementsByClass("information");
            for (int i = 0; i < information.size(); i++) {
                informationList.add(information.get(i).html());
            }
            
            json.put("productInformation", informationList);
            
            
            //So sanh gia voi cac hang khac
            JSONObject compareProductList = AjaxPost.getJsonOfAProduct(url);
            if(compareProductList==null) return null;
            json.put("compare", compareProductList);
            total.put("metadata",json);
            return total;
        }catch(IOException e){
            return null;
        }  
        
         /**End analytical**/
        
    }
    
    
    //Check pagination
    public static boolean checkPagination(Document page){
        
        
        try{
            return page.getElementsByClass("pagination").get(0).getElementsByTag("li").size()>0;
        }catch(Exception e){
             return false;
        }
       
        
    }
    
    
    
    
    public static void main(String[] args) throws JSONException, UnknownHostException {
          
        
           //Init connect mongodb
        MongoClient mongoClient = new MongoClient(MongoDb.Config.HOST, MongoDb.Config.defaultPort);
        DB db = mongoClient.getDB(MongoDb.Config.Schema);
        
        
        //Insert category and subCategory
         Main.insertCategoryAndSubCategory();
        //Connect
        MongoDBController demo = new MongoDBController(mongoClient, db);
        DBCollection category = db.getCollection("Category");
        DBCollection subCategoryCol = db.getCollection("subCategory");
        DBCollection product = db.getCollection("Production");
        DBCollection featureImageCol = db.getCollection("featureImage");
        DBCollection productInforCol = db.getCollection("ProductDetailInformation");
        DBCollection compareProductCol = db.getCollection("compareProduction");
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        List CategoriesList = Parser.getJSONCategory();
        CategoriesList.forEach(new Consumer() {
            @Override
            public void accept(Object CategoriesList1) {
                JSONObject category = (JSONObject)CategoriesList1;
                try {
                    JSONArray subCategoryList    = (JSONArray) category.get("subList");
                    String categoryName = category.get("categoryName").toString();//==================//
                    
                    for (int i = 0; i < subCategoryList.length(); i++) {
                        
                        JSONObject subCategory = subCategoryList.getJSONObject(i);
                        
                        
                        String subCategoryName = subCategory.get("subname").toString();//==================//
                        String suburl          = subCategory.get("suburl").toString();
                        
                        ////Su li tiep vao day nhe
                        List bigList = Parser.getAllDetailUrlProductOfSubCotegory(suburl);
                        for (int j = 0; j < bigList.size(); j++) {
                            List smallList = (List) bigList.get(j);
                            for (int k = 0; k < smallList.size(); k++) {   
                                if(!smallList.get(k).toString().contains("direct.htm")){
                                    try{
                                        JSONObject json = Parser.getDetailProduct(categoryName, subCategoryName, smallList.get(k).toString());
                                        if(json!=null){
                                            System.out.println(json);
                                            MongoDBController.addAProduct(json, mongoClient, db, demo, product, subCategoryCol, featureImageCol, productInforCol, compareProductCol);

                                        }
                                    }catch(Exception e){

                                    }
                                }
                            }
                        }
                        
                    }
                    
                    
                    
                } catch (JSONException ex) {
                    Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });//      System.out.println(Parser.getAllDetailUrlProductOfSubCotegory("https://websosanh.vn/dien-thoai-smartphone/cat-82.htm"));
//     
    }
}
