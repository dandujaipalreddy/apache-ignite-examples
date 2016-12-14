package com.jaipal.cache.server;

import com.jaipal.model.Player;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoPersistence<K, V> {
    private static MongoClient client = new MongoClient();
    private static MongoDatabase db = client.getDatabase("ignite");
    private static MongoCollection<Document> coll = db.getCollection("sample");
    private static Logger logger = Logger.getLogger(MongoPersistence.class);


    public void insert(Player r) {
        Document doc = Document.parse(new Gson().toJson(r));
        coll.updateOne(
                new Document("id", r.getUsername()).append("sessionId", r.getSessionId()),
                new Document("country", r.getCountry()),
                new UpdateOptions().upsert(true));
    }

    public Player get(String id) {
        Document doc = coll.find(new Document("id", id)).first();
        return new Gson().fromJson(doc.toJson(), Player.class);
    }

    public List<Player> getAll() {
        List<Player> rs = new ArrayList<>();
        List<Document> resultSet = coll.find().into(new ArrayList<Document>());
        resultSet.forEach(document -> {
            List<Integer> list = (List<Integer>) document.get("subId");
            for (Integer i : list)
                rs.add(new Player(document.getString("id"), document.getString("sessionId"), document.getString("country")));
        });
        System.out.println("********************" + rs + "****************" + rs.size());

        return rs;
    }

    public void delete(int id) {
        coll.deleteOne(new Document("id", id));
    }
}
