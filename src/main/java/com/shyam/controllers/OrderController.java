package com.shyam.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.request.OrderRequest;
import com.shyam.exceptions.CustomAccessDeniedException;
import com.shyam.exceptions.EntityNotFoundException;
import com.shyam.services.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(
    name = "Order Controller",
    description = "User can control their orders here"
)
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest request) {
        System.out.println(request);
        orderService.addOrder(request);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "order placed successfully"));
    }

    @GetMapping("/")
    public ResponseEntity<?> myOrder() {

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(orderService.getOrders());
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> deleteOrder(
        @PathVariable("orderId") int orderId
    ) throws EntityNotFoundException, CustomAccessDeniedException {
        orderService.deleteOrder(orderId);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "order placed successfully"));
    }

}
