package com.example.course_work.Repository;

import com.example.course_work.Entity.Product;
import com.example.course_work.Entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProductOrderByPriorityDesc(Product product);
    ProductImage findByUrl(String url);
}
