package com.example.course_work.Controller;

import com.example.course_work.Entity.DTO.OrderDto;
import com.example.course_work.Entity.DTO.UserDto;
import com.example.course_work.Entity.Order;
import com.example.course_work.Entity.User;
import com.example.course_work.Service.OrderService;
import com.example.course_work.Service.UserService;
import com.example.course_work.Utils.MappingUtils;
import com.example.course_work.Utils.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final MappingUtils utils;

    @PostMapping("/create")
    public UserDto createUser(@RequestBody User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return utils.mapToUserDto(userService.save(user));
    }

    @GetMapping("/get/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return utils.mapToUserDto(userService.getById(id));
    }

    @PostMapping("/delete/{id}")
    public String delUser(@PathVariable Integer id) {
        userService.delete(id);
        return "Пользователь с id " + id + " удален или не существовал";
    }

    @PostMapping("/update/{id}")
    public UserDto updateUser(@PathVariable Integer id, @RequestBody User nw) {
        User old = userService.getById(id);
        return utils.mapToUserDto(userService.update(old, nw));
    }

    @GetMapping("get/all/orders")
    public List<OrderDto> getAllUserOrders(@AuthenticationPrincipal MyUserDetails user) {
        return orderService.findAllByUserId(user.getId()).stream().map(utils::mapToOrderDto).collect(Collectors.toList());
    }

    @GetMapping("get/order/{order_id}")
    public OrderDto getOrder(@AuthenticationPrincipal MyUserDetails user, @PathVariable Long order_id) {
        Order order = orderService.getConcreteOrder(user.getId(), order_id);
        if (order != null) return utils.mapToOrderDto(orderService.getConcreteOrder(user.getId(), order_id));
        else return null;
    }

    @GetMapping("/get/all/users")
    public List<UserDto> getAllUsers() {
        return userService.getAll().stream().map(utils::mapToUserDto).collect(Collectors.toList());
    }
}
