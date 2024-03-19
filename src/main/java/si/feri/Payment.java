package si.feri;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private String _id;
    private String user;
    private String description;
    private double amount;
    private String status;
    private String createdAt;
    private String updatedAt;

    public static Payment fromDocument(Document document) {
        var payment = new Payment();

        payment._id = document.getObjectId("_id").toString();
        payment.user = document.getString("user");
        payment.description = document.getString("description");
        payment.amount = document.getDouble("amount");
        payment.status = document.getString("status");
        payment.createdAt = document.getString("createdAt");
        payment.updatedAt = document.getString("updatedAt");
        return payment;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("amount", this.amount);
        document.append("status", this.status);
        document.append("description", this.description);
        document.append("user", this.user);
        document.append("createdAt", this.createdAt);
        document.append("updatedAt", this.updatedAt);
        return document;
    }
}
