package com.example.course_work.Controller;

import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.ProductImage;
import com.example.course_work.Service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ProductImageService productImageService;
    @PostMapping("create")
    public ProductImage createProductImage(@RequestBody ProductImage productImage) {
        return productImageService.save(productImage);
    }
    @PostMapping("delete/{id}")
    public void deleteProductImage(@PathVariable Integer id) {
        productImageService.delete(id);
    }

    @GetMapping("/{id}")
    public ProductImage getImage(@PathVariable Integer id) {
        return productImageService.getById(id);
    }


}
