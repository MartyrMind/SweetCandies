package com.example.course_work.Controller;

import com.example.course_work.Entity.Category;
import com.example.course_work.Entity.DTO.UserDto;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.ProductImage;
import com.example.course_work.Entity.User;
import com.example.course_work.Service.CategoryService;
import com.example.course_work.Service.ProductService;
import com.example.course_work.Service.UserService;
import com.example.course_work.Utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/mvc")
@RequiredArgsConstructor
public class MVCController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final MappingUtils utils;

    @GetMapping("/get/product/{product_id}")
    public String getFancyProduct(@PathVariable Integer product_id, Model model) {
        Product product = productService.getById(product_id);
        model.addAttribute("product", product);
        return "product_card";
    }

    @GetMapping("/get/category/{category_id}")
    public String getFancyCategory(@PathVariable Integer category_id, Model model) {
        Category category = categoryService.getById(category_id);
        model.addAttribute("category", category);
        return "category";
    }

    @GetMapping("/get/user/{user_id}")
    public String getFancyUser(@PathVariable Integer user_id, Model model) {
        User user = userService.getById(user_id);
        UserDto dto = utils.mapToUserDto(user);
        model.addAttribute("user", dto);
        return "user";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
