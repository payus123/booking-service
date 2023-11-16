package com.hostfully.bookingservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private String title;
    private String body;
    private Double amount;
    private String beneficiaryAccountNumber;
    private String userNote;
    private Double locationLatitude;
    private String description;
    private String locationAddress;
    private String type;
    private String transactionId;
    private String createdAt;
    private String beneficiaryAccountName;
    private String originatorAccountNumber;
    private Double locationLongitude;
    private String paymentMethod;
    private String originatorAccountName;
    private String id;
    private String category;
    private String status;

}
