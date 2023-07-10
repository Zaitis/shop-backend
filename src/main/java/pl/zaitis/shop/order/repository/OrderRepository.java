package pl.zaitis.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.order.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
