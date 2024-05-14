package com.orderservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.orderservice.dto.OrderLineItemsDto;
import com.orderservice.dto.OrderRequest;
import com.orderservice.models.Order;
import com.orderservice.models.OrderLineItems;
import com.orderservice.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private WebClient webClient;
    
    public Order placeOrder(OrderRequest req){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = req.getOrderLineItemDtoList().stream().map(this::mapToDto).toList();

        order.setOrderItemsList(orderLineItems);

        orderRepo.save(order);
        return order;
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems(); 
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        
        return orderLineItems;
    }
}
