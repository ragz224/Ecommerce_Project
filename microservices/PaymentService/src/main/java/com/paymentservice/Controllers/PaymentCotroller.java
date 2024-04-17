package com.paymentservice.Controllers;

import com.paymentservice.Services.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentCotroller {

    private PaymentService paymentService;


    @Autowired
    public PaymentCotroller(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public String intiatePayment()  {
        return paymentService.intiatePayment();
    }

   @GetMapping
    public String gurl() {
        return paymentService.gurl();
    }
}
