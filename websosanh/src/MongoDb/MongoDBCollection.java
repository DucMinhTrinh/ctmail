/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MongoDb;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoDBCollection
{

    public static void main(String args[]) throws UnknownHostException, JSONException
    {
            long startTime = System.nanoTime();
         
            //Init
            MongoClient mongoClient=new MongoClient(MongoDb.Config.HOST,MongoDb.Config.defaultPort);
            DB db=mongoClient.getDB(MongoDb.Config.Schema);
            String colletioName = "websosanh";
            
            
            DBCollection collections = db.getCollection(colletioName);
           //MongoDBController mongDbController = new MongoDBController(mongoClient,db);
           //if(mongDbController.addACollection("")){
           for(int i=1;i<2;i++){
               JSONObject json = new JSONObject();
               json.put("status", "ok");
               MongoDBController.addDocumentFromJsonToJsonObject(json, collections);
               //System.out.println(collections.getCount());
           }
           long endTime = System.nanoTime();
           long duration = (endTime - startTime);
           System.out.println(collections.getCount());
           System.out.println("Time Total excute is "+ duration);
           
           //}

    }
}
