package com.shyam.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shyam.dto.request.OrderRequest;
import com.shyam.dto.response.OrdersResponse;
import com.shyam.entities.MedicineEntity;
import com.shyam.entities.OrderEntity;
import com.shyam.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final MedicineService medicineService;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    public void addOrder(OrderRequest request) {
        OrderEntity order = modelMapper.map(request, OrderEntity.class);
        MedicineEntity medicine = medicineService.getById(request.getMedicineId()).get();
        order.setTotalPrice(request.getQuantity() * medicine.getPrice());
        order.setId(0);
        
        orderRepository.save(order);
    }
    
    public List<OrdersResponse> getOrders() {
        int id = authService.getCurrentUser().getId();
        return orderRepository.getOrderResponse(id);
    }

}
