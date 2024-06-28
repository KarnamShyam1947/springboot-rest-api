package com.shyam.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.request.OrderRequest;
import com.shyam.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest request) {
        System.out.println(request);
        orderService.addOrder(request);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "order placed successfully"));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> myOrder() {

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(orderService.getOrders());
    }

}
