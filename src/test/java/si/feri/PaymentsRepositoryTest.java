package si.feri;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import si.feri.dto.UpdatePaymentDto;

import static org.junit.jupiter.api.Assertions.assertEquals;


@QuarkusTest
public class PaymentsRepositoryTest {

    @Inject
    PaymentsRepository paymentsRepository;

    @BeforeEach
    void setUp() {
        paymentsRepository.getCollection().deleteMany(new org.bson.Document());
    }

    @Test
    void testSave() {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setDescription("Test payment");
        payment.setStatus("CREATED");
        payment.setUser("test");
        payment.setCreatedAt(java.time.LocalDateTime.now().toString());
        payment.setUpdatedAt(java.time.LocalDateTime.now().toString());

        Payment p = paymentsRepository.save(payment);

        assertEquals(payment.getAmount(), p.getAmount());
        assertEquals(payment.getDescription(), p.getDescription());
        assertEquals(payment.getStatus(), p.getStatus());
        assertEquals(payment.getUser(), p.getUser());
        assertEquals(payment.getCreatedAt(), p.getCreatedAt());
        assertEquals(payment.getUpdatedAt(), p.getUpdatedAt());
    }

    @Test
    void testUpdate() {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setDescription("Test payment");
        payment.setStatus("CREATED");
        payment.setUser("test");
        payment.setCreatedAt(java.time.LocalDateTime.now().toString());
        payment.setUpdatedAt(java.time.LocalDateTime.now().toString());

        Payment p = paymentsRepository.save(payment);

        UpdatePaymentDto updatePaymentDto = new UpdatePaymentDto();
        updatePaymentDto.set_id(p.get_id());
        updatePaymentDto.setStatus("PAID");

        Payment u = paymentsRepository.update(updatePaymentDto);

        assertEquals(p.getAmount(), u.getAmount());
        assertEquals(p.getDescription(), u.getDescription());
        assertEquals("PAID", u.getStatus());
        assertEquals(p.getUser(), u.getUser());
        assertEquals(p.getCreatedAt(), u.getCreatedAt());
        assertEquals(p.getUpdatedAt(), u.getUpdatedAt());
    }


    @Test
    void testGet() {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setDescription("Test payment");
        payment.setStatus("CREATED");
        payment.setUser("test");
        payment.setCreatedAt(java.time.LocalDateTime.now().toString());
        payment.setUpdatedAt(java.time.LocalDateTime.now().toString());

        Payment p = paymentsRepository.save(payment);

        Payment g = paymentsRepository.get(p.get_id());

        assertEquals(p.getAmount(), g.getAmount());
        assertEquals(p.getDescription(), g.getDescription());
        assertEquals(p.getStatus(), g.getStatus());
        assertEquals(p.getUser(), g.getUser());
        assertEquals(p.getCreatedAt(), g.getCreatedAt());
        assertEquals(p.getUpdatedAt(), g.getUpdatedAt());
    }

    @Test
    void testGetAll() {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setDescription("Test payment");
        payment.setStatus("CREATED");
        payment.setUser("test");
        payment.setCreatedAt(java.time.LocalDateTime.now().toString());
        payment.setUpdatedAt(java.time.LocalDateTime.now().toString());

        Payment p = paymentsRepository.save(payment);

        var payments = paymentsRepository.getAll();

        assertEquals(1, payments.size());

        Payment g = payments.getFirst();

        assertEquals(p.getAmount(), g.getAmount());
        assertEquals(p.getDescription(), g.getDescription());
        assertEquals(p.getStatus(), g.getStatus());
        assertEquals(p.getUser(), g.getUser());
        assertEquals(p.getCreatedAt(), g.getCreatedAt());
        assertEquals(p.getUpdatedAt(), g.getUpdatedAt());
    }

}
