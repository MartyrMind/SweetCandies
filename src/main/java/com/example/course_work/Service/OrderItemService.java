package com.example.course_work.Service;

import com.example.course_work.Entity.DTO.OrderItemDto;
import com.example.course_work.Entity.Order;
import com.example.course_work.Entity.OrderItem;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.User;
import com.example.course_work.Repository.OrderItemRepository;
import com.example.course_work.Repository.OrderRepository;
import com.example.course_work.Utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem getById(Integer id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        orderItemRepository.deleteById(id);
    }

    public Order deleteFromOrder(Order order, Product product) {
        orderItemRepository.deleteByOrderIdAndProductId(order.getId(), product.getId());
        return order;
    }

    public OrderItem getProductFromOrder(Product product, Order order) {
        return orderItemRepository.findByOrderIdAndProductId(order.getId(), product.getId());
    }

    @Transactional
    public OrderItem updateAmount(Product product, Order order, Integer amount) {
        orderItemRepository.updateAmount(amount, order, product);
        return getProductFromOrder(product, order);
    }
}
