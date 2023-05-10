package com.example.course_work.Repository;

import com.example.course_work.Entity.Category;
import com.example.course_work.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Transactional
    @Query("select distinct c.title from Category c")
    List<String> getTitles();
    Category findByTitle(String title);

}
