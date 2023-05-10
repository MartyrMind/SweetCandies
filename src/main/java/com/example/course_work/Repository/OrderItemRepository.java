package com.example.course_work.Repository;

import com.example.course_work.Entity.Order;
import com.example.course_work.Entity.OrderItem;
import com.example.course_work.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findAllByOrderId(Long order_id);
    OrderItem findByOrderIdAndProductId(Long order_id, Long product_id);
    void deleteByOrderIdAndProductId(Long order_id, Long product_id);

    @Modifying
    @Query("update OrderItem i set i.amount = ?1 where i.order = ?2 and  i.product = ?3")
    void updateAmount(Integer amount, Order order, Product product);
}
