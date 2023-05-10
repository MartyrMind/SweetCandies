package com.example.course_work.Service;

import com.example.course_work.Entity.Category;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.ProductImage;
import com.example.course_work.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public Category save(Category category) {
        if (category.getProducts() != null)
            for (Product product : category.getProducts()) productService.addToCategory(product, category);
        return categoryRepository.save(category);
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category getByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void delete(Integer id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
    }

    public List<String> getTitles() {
        return categoryRepository.getTitles();
    }
}


