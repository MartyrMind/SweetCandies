package com.example.course_work.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "order_items")
@IdClass(OrderItem.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem implements Serializable {
    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @Column(name="amount")
    private Integer amount;

}
