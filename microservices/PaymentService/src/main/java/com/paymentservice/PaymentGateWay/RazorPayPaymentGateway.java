package com.paymentservice.PaymentGateWay;

import com.razorpay.PaymentLink;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.time.Instant;

@Service
public class RazorPayPaymentGateway implements PaymentGateway{
    @Override
    public String generateUrl()  {
            try {


                    RazorpayClient razorpay = new RazorpayClient("rzp_test_Rt3mxGAkzaAhKa",
                            "UYVxzSh7uTm0s6og3qrrnInO");
                    JSONObject paymentLinkRequest = new JSONObject();
                    paymentLinkRequest.put("amount", 1000);
                    paymentLinkRequest.put("currency", "INR");
                    paymentLinkRequest.put("accept_partial", true);
                    paymentLinkRequest.put("first_min_partial_amount", 100);

//            // Calculate expire_by timestamp at least 15 minutes in the future
//            long expireByTimestamp = Instant.now().plusSeconds(15 * 60).getEpochSecond();
//            paymentLinkRequest.put("expire_by", expireByTimestamp);

                    paymentLinkRequest.put("expire_by", 1709409999);
                    paymentLinkRequest.put("reference_id", "ec");
                    paymentLinkRequest.put("description", "Payment for policy no #23456");
                    JSONObject customer = new JSONObject();
                    customer.put("name", "+7893010948");
                    customer.put("contact", "Raghavendra");
                    customer.put("email", "vengalaraghu01@gmail.com");
                    paymentLinkRequest.put("customer", customer);
                    JSONObject notify = new JSONObject();
                    notify.put("sms", true);
                    notify.put("email", true);
                    paymentLinkRequest.put("notify", notify);
                    paymentLinkRequest.put("reminder_enable", false);
                    JSONObject notes = new JSONObject();
                    notes.put("policy_name", "Jeevan Bima");
                    paymentLinkRequest.put("notes", notes);
                    paymentLinkRequest.put("callback_url", "https://chat.openai.com/");
                    paymentLinkRequest.put("callback_method", "get");

                    PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
                    return payment.toString();
            }
            catch (Exception e) {
                    System.out.println(e);
            }
            return null;


    }

        @Override
        public String glink() {
                return null;
        }
}
