package kPrzewoźnik.sales.payment;

public interface PaymentGateway {
    PaymentData register(RegisterPaymentRequest request);
}
