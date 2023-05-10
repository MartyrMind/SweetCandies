package com.example.course_work.Entity.DTO;

import lombok.Data;


import java.util.List;
import java.util.Set;

@Data
public class ProductDto {
    Long id;
    String title;
    String description;
    Integer rating;
    Integer cost;
    List<String> images = null;
}
