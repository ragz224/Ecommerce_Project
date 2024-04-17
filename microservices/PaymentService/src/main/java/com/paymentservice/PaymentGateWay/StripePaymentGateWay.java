package com.paymentservice.PaymentGateWay;

import com.stripe.Stripe;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripePaymentGateWay implements PaymentGateway{
    @Override
    public String generateUrl() {

        Stripe.apiKey = "sk_test_51OpxaGSD0Fr9BVeuVJMun0AXuKh3pKzpxHHNmWwkLDpieeBjPGQQahOu8IcbLuTIEvsPiUfspr78RIpsOpyDwnru00AMkBZ9bi";
        try {
            Map<String, Object> productData = new HashMap<>();
            productData.put("name", "Test Product");

            Map<String, Object> productParam = new HashMap<>();
            productParam.put("unit_amount", 10000);
            productParam.put("currency", "inr");
            productParam.put("product_data", productData);

// Create price
            Price price = Price.create(productParam);

// Create price line item
            Map<String, Object> priceLineItem = new HashMap<>();
            priceLineItem.put("price", price.getId());
            priceLineItem.put("quantity", 1);

            List<Object> lineItems = new ArrayList<>();
            lineItems.add(priceLineItem);

            Map<String, Object> finalParams = new HashMap<>();
            finalParams.put("line_items", lineItems);

            Map<String, Object> afterCompletion = new HashMap<>();
            afterCompletion.put("type", "redirect");

            Map<String, Object> redirect = new HashMap<>();
            redirect.put("url", "https://google.com?payment_id={CHECKOUT_SESSION_ID}");

            afterCompletion.put("redirect", redirect);

            finalParams.put("after_completion", afterCompletion);

            PaymentLink paymentLink = PaymentLink.create(finalParams);

            return paymentLink.getUrl();
        } catch(Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public String glink() {
        try {
            Stripe.apiKey = "sk_test_51OpxaGSD0Fr9BVeuVJMun0AXuKh3pKzpxHHNmWwkLDp" +
                    "ieeBjPGQQahOu8IcbLuTIEvsPiUfspr78RIpsO";

            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice("price_1MoC3TLkdIwHu7ixcIbKelAC")
                                            .setQuantity(1L)
                                            .build()
                            )
                            .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
