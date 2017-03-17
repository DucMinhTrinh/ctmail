package MongoDb;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.json.JSONObject;

/**
 *
 * @author MinhPC
 */
public class MongoDBController {

    public MongoClient mongoClient;
    public DB db;

    public MongoDBController(MongoClient mongoClient, DB db) {
        this.mongoClient = mongoClient;
        this.db = db;
    }

    //Create a new ID
    public static ObjectId getNewID() {
        return new ObjectId();
    }

    //Add a collection in a schema
    public boolean addACollection(String collectionName) {

        try {

            DBCollection linked = db.createCollection(collectionName, new BasicDBObject());
            return true;
        } catch (Exception e) {
            System.out.println("Collection đã tồn tại hoặc có lỗi xảy ra");
            System.out.println(e.getClass().getName() + ":" + e.getMessage());
            return false;
        }
    }

    //Add a JSONObject in a Collection
    public static String addDocumentFromJsonToJsonObject(JSONObject json, DBCollection collections) {
        try {
            json.put("_id", MongoDBController.getNewID());
            DBObject document = (DBObject) JSON.parse(json.toString());
            WriteResult writeResult = collections.insert(document);
            return json.get("_id").toString();
        } catch (Exception e) {
            return "error";
        }
    }

}
