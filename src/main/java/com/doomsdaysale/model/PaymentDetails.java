package com.doomsdaysale.model;

import com.doomsdaysale.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentLinkZWSP;
    private PaymentStatus status;
}
