package dev.chunghyun.shop.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o JOIN FETCH o.orderItemsList ORDER BY o.id DESC")
    List<Orders> findOrdersDesc();
}
