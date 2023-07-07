package pl.zaitis.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zaitis.shop.admin.order.model.AdminOrder;
import pl.zaitis.shop.admin.order.model.AdminOrderStatus;
import pl.zaitis.shop.admin.order.repository.AdminOrderLogRepository;
import pl.zaitis.shop.admin.order.repository.AdminOrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminExportService {

    private final AdminOrderRepository adminOrderRepository;
    public List<AdminOrder> exportOrders(LocalDateTime from, LocalDateTime to, AdminOrderStatus orderStatus) {
        return adminOrderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(from, to, orderStatus);

    }
}
