package com.example.course_work.Controller;

import com.example.course_work.Entity.Category;
import com.example.course_work.Entity.DTO.CategoryDto;
import com.example.course_work.Entity.DTO.ProductDto;
import com.example.course_work.Entity.Product;
import com.example.course_work.Service.CategoryService;
import com.example.course_work.Service.ProductService;
import com.example.course_work.Utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final MappingUtils utils;

    @PostMapping("/create")
    public CategoryDto addCategory(@RequestBody Category category) {
        return utils.mapToCategoryDto(categoryService.save(category));
    }

    @PostMapping("/delete/{category_id}")
    public void deleteCategory(@PathVariable Integer category_id) {
        categoryService.delete(category_id);
    }

    @PostMapping("/add/{category_id}")
    public CategoryDto addNewProductsToCategory(@PathVariable Integer category_id, @RequestBody List<Product> products) {
        Category category = categoryService.getById(category_id);
        for (Product product : products) productService.addToCategory(product, category);
        return utils.mapToCategoryDto(category);
    }

    @PostMapping("/add/{category_id}/{product_id}")
    public CategoryDto addExistingProductToCategory(@PathVariable Integer category_id, @PathVariable Integer product_id) {
        Category category = categoryService.getById(category_id);
        Product product = productService.getById(product_id);
        productService.addToCategory(product, category);
        return utils.mapToCategoryDto(category);
    }

    @PostMapping("/remove/{category_id}/{product_id}")
    public CategoryDto removeProductFromCategory(@PathVariable Integer category_id, @PathVariable Integer product_id) {
        Category category = categoryService.getById(category_id);
        Product product = productService.getById(product_id);
        productService.addToCategory(product, null);
        return utils.mapToCategoryDto(category);
    }

    @GetMapping("/get/{id}")
    public CategoryDto getById(@PathVariable Integer id) {
        return utils.mapToCategoryDto(categoryService.getById(id));
    }

    @GetMapping("/get/{id}/products")
    public List<ProductDto> getProductsFromCategory(@PathVariable Integer id) {
        return categoryService.getById(id).getProducts().stream().map(utils::mapToProductDto).collect(Collectors.toList());
    }
    @GetMapping("/get/all")
    public List<CategoryDto> getAll() {
        return categoryService.getAll().stream().map(utils::mapToCategoryDto).collect(Collectors.toList());
    }

    @GetMapping("find/{title}")
    public CategoryDto findByTitle(@PathVariable String title) {
        return utils.mapToCategoryDto(categoryService.getByTitle(title));
    }

    @GetMapping("get/titles")
    public List<String> getTitles() {
        return categoryService.getTitles();
    }
}
