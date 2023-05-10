package com.example.course_work.Entity.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Integer id;
    private String firstname;
    private String secondname;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
    private Date birthday;
    private String favouriteProduct = "none";
    private String favouriteCategory = "none";
    private String profileImage;
    private List<OrderDto> orders;
}
