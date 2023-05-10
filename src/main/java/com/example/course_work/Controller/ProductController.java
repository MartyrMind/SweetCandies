package com.example.course_work.Controller;

import com.example.course_work.Entity.DTO.ProductDto;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.ProductImage;
import com.example.course_work.Service.ProductImageService;
import com.example.course_work.Service.ProductService;
import com.example.course_work.Utils.MappingUtils;
import com.example.course_work.Utils.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductImageService imageService;
    private final MappingUtils utils;

    @PostMapping("/create")
    public ProductDto addProduct(@RequestBody Product product) {
        return utils.mapToProductDto(productService.save(product));
    }

    @PostMapping("/create/array")
    public List<ProductDto> addProducts(@RequestBody List<Product> products) {
        return products.stream().map(this::addProduct).collect(Collectors.toList());
    }

    @PostMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }

    @PostMapping("/add/{product_id}/{image_id}")
    public ProductDto addExistingImageToProduct(@PathVariable Integer product_id, @PathVariable Integer image_id) {
        Product product = productService.getById(product_id);
        ProductImage image = imageService.getById(image_id);
        imageService.addToProduct(image, product);
        return utils.mapToProductDto(product);
    }

    @PostMapping("/add/{id}")
    public ProductDto addNewImagesToProduct(@PathVariable Integer id, @RequestBody List<ProductImage> images) {
        Product product = productService.getById(id);
        for (ProductImage image : images) imageService.addToProduct(image, product);
        return utils.mapToProductDto(product);
    }

    @PostMapping("/remove/{product_id}/{image_id}")
    public ProductDto removeImageFromProduct(@PathVariable Integer product_id, @PathVariable Integer image_id) {
        Product product = productService.getById(product_id);
        ProductImage image = imageService.getById(image_id);
        imageService.addToProduct(image, null);
        return utils.mapToProductDto(product);
    }

    @PostMapping("/rate/{product_id}")
    public ProductDto rateProduct(@PathVariable Integer product_id, @RequestBody Rating rate) {
        return utils.mapToProductDto(productService.rateProduct(product_id, rate.getRating()));
    }

    @PostMapping("/update/{product_id}")
    public ProductDto updateProduct(@PathVariable Integer product_id, @RequestBody Product newProduct) {
        Product old = productService.getById(product_id);
        return utils.mapToProductDto(productService.update(old, newProduct));
    }

    @GetMapping("/get/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return utils.mapToProductDto(productService.getById(id));
    }

    @GetMapping("/find/{title}")
    public ProductDto findProductByTitle(@PathVariable String title) {
        return utils.mapToProductDto(productService.getByTitle(title));
    }

    @GetMapping("/order")
    public List<ProductDto> getOrderedByCost() {
        return productService.getOrderedByCost().stream().map(utils::mapToProductDto).collect(Collectors.toList());
    }

    @GetMapping("/best")
    public List<ProductDto> getBest() {
        return productService.getOrderedByRating().stream().map(utils::mapToProductDto).collect(Collectors.toList());
    }

    @GetMapping("/get/between/{low}/{high}")
    public List<ProductDto> getBetween(@PathVariable Integer low, @PathVariable Integer high) {
        return productService.getBetween(low, high).stream().map(utils::mapToProductDto).collect(Collectors.toList());
    }

    @GetMapping("/get/titles")
    public List<String> getTitle() {
        return productService.getTitles();
    }

    @GetMapping("/get/all")
    public List<ProductDto> getAll() {
        return productService.getAll().stream().map(utils::mapToProductDto).collect(Collectors.toList());
    }
}
