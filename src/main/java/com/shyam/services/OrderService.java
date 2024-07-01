package com.shyam.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shyam.dto.request.OrderRequest;
import com.shyam.dto.request.OrderUpdateDTO;
import com.shyam.dto.response.OrdersResponse;
import com.shyam.entities.MedicineEntity;
import com.shyam.entities.OrderEntity;
import com.shyam.entities.UserEntity;
import com.shyam.enums.OrderStatus;
import com.shyam.exceptions.CustomAccessDeniedException;
import com.shyam.exceptions.EntityNotFoundException;
import com.shyam.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public void deleteOrder(
        int orderId
    ) throws RuntimeException, EntityNotFoundException, CustomAccessDeniedException {
        OrderEntity order = orderRepository
                            .findById(orderId)
                            .orElseThrow(
                                () -> new EntityNotFoundException("Unable to find Medicine with id : " + orderId)
                            );

        if (order.getOrderStatus().equals(OrderStatus.SHIPPED)) {
            log.error("order already delivered can\'t cancel now");
            throw new RuntimeException("order already delivered can\\'t cancel now");
            
        }

        UserEntity currentUser = authService.getCurrentUser();
        if (order.getUserId() != currentUser.getId()) {
            log.error("User " + currentUser.getName() + " trying to access anther user resource");
            throw new CustomAccessDeniedException("you are trying to access anther user resource", "/delete-orders/");
        }

        orderRepository.delete(order);
    }

    public OrderEntity changeStatus(
        OrderUpdateDTO dto
    ) throws EntityNotFoundException {
        OrderEntity order = orderRepository
            .findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Order Not found with id : " + dto.getStatus()));

        order.setOrderStatus(OrderStatus.valueOf(dto.getStatus()));

        return orderRepository.save(order);
    }

}
