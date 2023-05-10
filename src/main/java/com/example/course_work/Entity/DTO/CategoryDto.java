package com.example.course_work.Entity.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    Long id;
    String title;
    List<String> productTitles;
}
