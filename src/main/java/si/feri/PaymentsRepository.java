package si.feri;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import si.feri.dto.UpdatePaymentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class PaymentsRepository {

    @Inject
    MongoClient mongoClient;

    MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("payments").getCollection("payments");
    }


    public Payment save(Payment payment) {
        Document document = payment.toDocument();
        getCollection().insertOne(document);
        return Payment.fromDocument(document);
    }

    public Payment update(UpdatePaymentDto payment) {
        var objectId = new org.bson.types.ObjectId(payment.get_id());
        var filter = new Document("_id", objectId);
        var update = new Document("$set", new Document("status", payment.getStatus()));
        getCollection().updateOne(filter, update);
        return get(payment.get_id());
    }

    public Payment get(String id) {
        var objectId = new org.bson.types.ObjectId(id);
        var p = Payment.fromDocument(Objects.requireNonNull(getCollection().find(new Document("_id", objectId)).first()));
        return p;
    }

    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
        try {
            while (cursor.hasNext()) {
                payments.add(Payment.fromDocument(cursor.next()));
            }
        } finally {
            cursor.close();
        }
        return payments;
    }

}
