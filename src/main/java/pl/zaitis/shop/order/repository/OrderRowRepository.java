package pl.zaitis.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.order.model.OrderRow;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {
}
