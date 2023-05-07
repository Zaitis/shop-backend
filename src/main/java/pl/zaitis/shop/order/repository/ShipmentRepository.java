package pl.zaitis.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaitis.shop.order.model.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
