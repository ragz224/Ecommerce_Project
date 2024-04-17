package com.paymentservice.PaymentGateWay;

import com.razorpay.RazorpayException;

public interface PaymentGateway {

    public String generateUrl() ;

    public String glink();
}
