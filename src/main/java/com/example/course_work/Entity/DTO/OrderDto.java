package com.example.course_work.Entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    Long id;
    String email;
    Float totalPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    Date date;
    List<OrderItemDto> items;
}
