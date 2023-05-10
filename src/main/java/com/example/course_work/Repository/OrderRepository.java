package com.example.course_work.Repository;

import com.example.course_work.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(Integer user_id);
    List<Order> findAllByDate(Date date);
    List<Order> findAllByDateBetween(Date before, Date after);

    Order getOrderByUserIdAndId(Integer user_id, Long product_id);
}
