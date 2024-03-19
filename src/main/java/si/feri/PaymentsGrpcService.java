package si.feri;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

import java.util.List;
import java.util.logging.Logger;

@GrpcService
public class PaymentsGrpcService implements PaymentsGrpc {

    @Inject
    PaymentsRepository paymentsRepository;

    private static final Logger LOG = Logger.getLogger(PaymentsGrpcService.class.getName());

    @Override
    public Uni<GetPaymentResponse> getPayment(GetPaymentRequest request) {
        var payment = paymentsRepository.get(request.getId());

        LOG.info("Payment found: " + payment.get_id());

        return Uni.createFrom().item(GetPaymentResponse.newBuilder()
                .setId(payment.get_id())
                .setUser(payment.getUser())
                .setAmount(payment.getAmount())
                .setDescription(payment.getDescription())
                .setStatus(payment.getStatus())
                .setCreatedAt(payment.getCreatedAt())
                .setUpdatedAt(payment.getUpdatedAt())
                .build());
    }

    @Override
    public Uni<CreatePaymentResponse> createPayment(CreatePaymentRequest request) {
        var payment = new Payment();

        var createdAt = java.time.LocalDateTime.now();

        payment.setAmount(request.getAmount());
        payment.setDescription(request.getDescription());
        payment.setStatus("CREATED");
        payment.setUser(request.getUser());
        payment.setCreatedAt(createdAt.toString());
        payment.setUpdatedAt(createdAt.toString());

        var p = paymentsRepository.save(payment);
        LOG.info("Payment created: " + p.get_id());

        return Uni.createFrom().item(CreatePaymentResponse.newBuilder().setId(p.get_id()).build());
    }

    @Override
    public Uni<GetAllPaymentsResponse> getAllPayments(GetAllPaymentsRequest request) {
        List<Payment> payments = paymentsRepository.getAll();
        LOG.info("Payments found: " + payments.size());

        var response = GetAllPaymentsResponse.newBuilder();

        payments.forEach(payment -> {
            response.addPayments(GetPaymentResponse.newBuilder()
                    .setId(payment.get_id())
                    .setUser(payment.getUser())
                    .setAmount(payment.getAmount())
                    .setDescription(payment.getDescription())
                    .setStatus(payment.getStatus())
                    .setCreatedAt(payment.getCreatedAt())
                    .setUpdatedAt(payment.getUpdatedAt())
                    .build());
        });

        return Uni.createFrom().item(response.build());
    }

    @Override
    public Uni<UpdatePaymentResponse> updatePayment(UpdatePaymentRequest request) {
        var payment = new si.feri.dto.UpdatePaymentDto();
        payment.set_id(request.getId());
        payment.setStatus(request.getStatus());

        var p = paymentsRepository.update(payment);
        LOG.info("Payment updated: " + p.get_id());

        return Uni.createFrom().item(UpdatePaymentResponse.newBuilder()
                .setMessage("Payment updated")
                .build());
    }
}
