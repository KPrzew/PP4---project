package kPrzewoźnik.sales.productdetails;

import kPrzewoźnik.sales.productdetails.ProductDetails;

import java.util.Optional;

public interface ProductDetailsProvider {
    public Optional<ProductDetails> load(String productId);
}