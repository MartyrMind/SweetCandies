package com.example.course_work.Service;

import com.example.course_work.Entity.DTO.OrderDto;
import com.example.course_work.Entity.DTO.OrderItemDto;
import com.example.course_work.Entity.Order;
import com.example.course_work.Entity.OrderItem;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.User;
import com.example.course_work.Repository.OrderRepository;
import com.example.course_work.Utils.MappingUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final EmailService emailService;
    private final MappingUtils utils;

    public Order save(User user, Order order) {
        List<Order> orders = user.getOrders();
        orders.add(order);
        userService.save(user);
        order.setUser(user);
        return orderRepository.save(order);
    }

    public Order getById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }

    public Order addProduct(Order order, Product product) {
        OrderItem orderItem = new OrderItem(product, order, 1);
        orderItemService.save(orderItem);
        return order;
    }

    public Order removeProduct(Order order, Product product) {
        return orderItemService.deleteFromOrder(order, product);
    }

    @Transactional
    public OrderItem increase(Order order, Product product) {
        OrderItem orderItem = orderItemService.getProductFromOrder(product, order);
        if (orderItem == null) {
            orderItem = new OrderItem(product, order, 0);
            orderItemService.save(orderItem);
        }
        return orderItemService.updateAmount(product, order, orderItem.getAmount() + 1);
    }

    @Transactional
    public OrderItem decrease(Order order, Product product) {
        OrderItem orderItem = orderItemService.getProductFromOrder(product, order);
        if (orderItem.getAmount() == 1) {
            removeProduct(order, product);
            return null;
        } else {
            return orderItemService.updateAmount(product, order, orderItem.getAmount() - 1);
        }
    }

    public List<Order> findAllByUserId(Integer user_id) {
        return orderRepository.findAllByUserId(user_id);
    }

    public List<Order> findAllByDate(Date date) {
        return orderRepository.findAllByDate(date);
    }

    public List<Order> findAllBetweenDates(Date before, Date after) {
        return  orderRepository.findAllByDateBetween(before, after);
    }

    public Order getConcreteOrder(Integer user_id, Long order_id) {
        return orderRepository.getOrderByUserIdAndId(user_id, order_id);
    }

    public void sendOrder(Integer user_id, Integer order_id) {
        User user = userService.getById(user_id);
        Order order = getById(order_id);
        OrderDto dto = utils.mapToOrderDto(order);
        String subject = "Заказ на сайте SweetCandies.com";
        String text = String.format("Ваш заказ номер %d стоимостью %.3f принят в обработку\n" +
                "Скоро с вами свяжется менеджер для уточнения деталей заказа", order.getId(), dto.getTotalPrice());
        emailService.sendEmail(subject, text, user.getEmail());
    }
}
