package com.paymentservice.Services;

import com.paymentservice.PaymentGateWay.PaymentGateway;
import com.paymentservice.PaymentGateWay.RazorPayPaymentGateway;
import com.paymentservice.PaymentGateWay.StripePaymentGateWay;
import org.springframework.stereotype.Service;


@Service
public class PaymentGatewayChooserStrategy {

    private RazorPayPaymentGateway razorPayGateway;
    private StripePaymentGateWay stripePaymentGateWay;

    public PaymentGatewayChooserStrategy(RazorPayPaymentGateway razorPayGateway, StripePaymentGateWay stripePaymentGateWay) {
        this.razorPayGateway = razorPayGateway;
        this.stripePaymentGateWay = stripePaymentGateWay;
    }

    public PaymentGateway bestPaymentGateWay() {
//        need to write our own logic hiw we can use this payment Strategey
//        return razorPayGateway;

        return stripePaymentGateWay;
    }
}
