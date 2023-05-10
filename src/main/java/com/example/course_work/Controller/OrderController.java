package com.example.course_work.Controller;

import com.example.course_work.Entity.DTO.OrderDto;
import com.example.course_work.Entity.DTO.OrderItemDto;
import com.example.course_work.Entity.DTO.UserDto;
import com.example.course_work.Entity.Order;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.User;
import com.example.course_work.Service.OrderService;
import com.example.course_work.Service.ProductService;
import com.example.course_work.Service.UserService;
import com.example.course_work.Utils.DataBoundaries;
import com.example.course_work.Utils.MappingUtils;
import com.example.course_work.Utils.MyUserDetails;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final MappingUtils utils;

    @PostMapping("/create")
    public OrderDto createOrder(@AuthenticationPrincipal MyUserDetails info, @RequestBody Order order) {
        User user = userService.getById(info.getId());
        return utils.mapToOrderDto(orderService.save(user, order));
    }

    @PostMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.delete(id);
    }

    @PostMapping("/add/{product_id}")
    public OrderDto addProductToOrder(@AuthenticationPrincipal MyUserDetails details,
                                      @PathVariable Integer product_id) {
        User user = userService.getById(details.getId());
        Order order = user.getOrders().get(user.getOrders().size() - 1);
        Product product = productService.getById(product_id);
        return utils.mapToOrderDto(orderService.addProduct(order, product));
    }

    @PostMapping("/remove/{product_id}")
    public OrderDto removeProductFromOrder(@AuthenticationPrincipal MyUserDetails details,
                                           @PathVariable Integer product_id) {
        User user = userService.getById(details.getId());
        Order order = user.getOrders().get(user.getOrders().size() - 1);
        Product product = productService.getById(product_id);
        return utils.mapToOrderDto(orderService.removeProduct(order, product));
    }

    @PostMapping("/incr/{product_id}")
    public OrderItemDto increaseAmount(@AuthenticationPrincipal MyUserDetails details,
                                       @PathVariable Integer product_id) {
        User user = userService.getById(details.getId());
        Order order = user.getOrders().get(user.getOrders().size() - 1);
        Product product = productService.getById(product_id);
        return utils.mapToOrderItemDto(orderService.increase(order, product));
    }

    @PostMapping("/decr/{product_id}")
    public OrderItemDto decreaseAmount(@AuthenticationPrincipal MyUserDetails details,
                                       @PathVariable Integer product_id) {
        User user = userService.getById(details.getId());
        Order order = user.getOrders().get(user.getOrders().size() - 1);
        Product product = productService.getById(product_id);
        return utils.mapToOrderItemDto(orderService.decrease(order, product));
    }

    @GetMapping("/checkout/")
    public void checkout(@AuthenticationPrincipal MyUserDetails details) {
        User user = userService.getById(details.getId());
        Order order = user.getOrders().get(user.getOrders().size() - 1);
        orderService.sendOrder(user.getId(), Math.toIntExact(order.getId()));
    }


    @GetMapping("/get/{order_id}")
    public OrderDto getOrder(@PathVariable Integer order_id) {
        return utils.mapToOrderDto(orderService.getById(order_id));
    }

    @GetMapping("/find/{user_id}")
    public List<OrderDto> getAllUserOrders(@PathVariable Integer user_id) {
        return orderService.findAllByUserId(user_id).stream().map(utils::mapToOrderDto).collect(Collectors.toList());
    }

    @GetMapping("get/date")
    public List<OrderDto> getOrdersAcordToDate(@RequestBody DataBoundaries dates) {
        if (dates.getAfter() == null)
            return orderService.findAllByDate(dates.getBefore())
                    .stream().map(utils::mapToOrderDto).collect(Collectors.toList());
        else return orderService.findAllBetweenDates(dates.getBefore(), dates.getAfter())
                    .stream().map(utils::mapToOrderDto).collect(Collectors.toList());
    }

}
