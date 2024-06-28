package com.shyam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponse {
    private int medicineId;
    private String medicineName;
    private int medicinePrice;
    private int orderId;
    private String phone;
    private String address;
    private int quantity;
    private int totalPrice;
}
