package shook.xeem;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MongoWorker {

    private static MongoClient mongoClient;
    private static MongoDatabase dbObject;

    public static void connect (String _url, String _dbname) {
        mongoClient = new MongoClient(_url);
        dbObject = mongoClient.getDatabase(_dbname);
    }

    public static void write (String _collection, Document _document) {
        dbObject.getCollection(_collection).insertOne(_document);
    }

}
