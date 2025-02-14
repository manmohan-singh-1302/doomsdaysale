package com.doomsdaysale.model;

import lombok.Data;

@Data // this annotation will generate getter setter tostring equalsandhash requiredargsconstructor
public class BankDetails {
    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
}
