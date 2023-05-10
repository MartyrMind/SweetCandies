package com.example.course_work.Repository;

import com.example.course_work.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductByTitle(String title);
    List<Product> findAllByOrderByRatingDesc();
    List<Product> findAllByOrderByCostAsc();
    List<Product> findAllByCostBetweenOrderByCostAsc(Integer low, Integer high);
    @Query("select distinct p.title from Product p")
    List<String> getTitles();

}


