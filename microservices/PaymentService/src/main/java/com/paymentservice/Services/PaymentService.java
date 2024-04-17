package com.paymentservice.Services;

import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private  PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    public PaymentService(PaymentGatewayChooserStrategy paymentGatewayChooserStrategy) {
        this.paymentGatewayChooserStrategy = paymentGatewayChooserStrategy;
    }

    public String intiatePayment()  {
        return paymentGatewayChooserStrategy.bestPaymentGateWay().generateUrl();

    }

    public String gurl() {
        return paymentGatewayChooserStrategy.bestPaymentGateWay().glink();

    }
}
