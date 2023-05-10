package com.example.course_work.Service;

import com.example.course_work.Entity.Category;
import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.ProductImage;
import com.example.course_work.Repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public void delete(Integer id) {
        productImageRepository.findById(id).ifPresent(productImageRepository::delete);
    }

    public ProductImage getById(Integer id) {
        return productImageRepository.findById(id).orElse(null);
    }

    public ProductImage getByUrl(String url) {
        return productImageRepository.findByUrl(url);
    }

    @Transactional
    public void addToProduct(ProductImage image, Product product) {
        if (image != null) {
            image.setProduct(product);
            save(image);
        }
    }

}
