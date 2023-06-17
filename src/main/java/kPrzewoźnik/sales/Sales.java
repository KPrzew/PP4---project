package kPrzewoźnik.sales;

import kPrzewoźnik.sales.cart.Cart;
import kPrzewoźnik.sales.cart.CartStorage;
import kPrzewoźnik.sales.offering.EveryNItemLineDiscountPolicy;
import kPrzewoźnik.sales.offering.Offer;
import kPrzewoźnik.sales.offering.OfferCalculator;
import kPrzewoźnik.sales.offering.TotalDiscountPolicy;
import kPrzewoźnik.sales.payment.PaymentData;
import kPrzewoźnik.sales.payment.PaymentGateway;
import kPrzewoźnik.sales.payment.RegisterPaymentRequest;
import kPrzewoźnik.sales.productdetails.NoSuchProductException;
import kPrzewoźnik.sales.productdetails.ProductDetails;
import kPrzewoźnik.sales.productdetails.ProductDetailsProvider;
import kPrzewoźnik.sales.reservation.OfferAcceptanceRequest;
import kPrzewoźnik.sales.reservation.Reservation;
import kPrzewoźnik.sales.reservation.ReservationDetails;
import kPrzewoźnik.sales.reservation.InMemoryReservationStorage;

import java.math.BigDecimal;
import java.util.Optional;

public class Sales {
    private CartStorage cartStorage;
    private ProductDetailsProvider productDetailsProvider;
    private final OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private InMemoryReservationStorage reservationStorage;

    public Sales(
            CartStorage cartStorage,
            ProductDetailsProvider productDetails,
            OfferCalculator offerCalculator,
            PaymentGateway paymentGateway,
            InMemoryReservationStorage reservationStorage
        ) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetails;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationStorage = reservationStorage;
    }

    public void addToCart(String customerId, String productId) {
        Cart customerCart = loadCartForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails product = loadProductDetails(productId)
                .orElseThrow(() -> new NoSuchProductException());

        customerCart.add(product.getId());

        cartStorage.addForCustomer(customerId, customerCart);
    }

    private Optional<ProductDetails> loadProductDetails(String productId) {
        return productDetailsProvider.load(productId);
    }

    private Optional<Cart> loadCartForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }

    public Offer getCurrentOffer(String customerId) {
        Cart customerCart = loadCartForCustomer(customerId)
                .orElse(Cart.empty());

        Offer offer = this.offerCalculator.calculateOffer(
                customerCart.getCartItems(),
                new TotalDiscountPolicy(BigDecimal.valueOf(500), BigDecimal.valueOf(50)),
                new EveryNItemLineDiscountPolicy(5)
        );

        return offer;
    }

    public ReservationDetails acceptOffer(String customerId, OfferAcceptanceRequest request) {
        Offer offer = this.getCurrentOffer(customerId);

        PaymentData payment = paymentGateway.register(RegisterPaymentRequest.of(request, offer));

        Reservation reservation = Reservation.of(request, offer, payment);

        reservationStorage.save(reservation);

        return new ReservationDetails(reservation.getId(), reservation.getPaymentUrl());
    }
}