package pl.zaitis.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.order.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
