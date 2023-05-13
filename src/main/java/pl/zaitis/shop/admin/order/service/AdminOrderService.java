package pl.zaitis.shop.admin.order.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.zaitis.shop.admin.order.model.AdminOrder;
import pl.zaitis.shop.admin.order.model.AdminOrderLog;
import pl.zaitis.shop.admin.order.model.AdminOrderStatus;
import pl.zaitis.shop.admin.order.repository.AdminOrderLogRepository;
import pl.zaitis.shop.admin.order.repository.AdminOrderRepository;
import pl.zaitis.shop.common.mail.EmailClientService;

import java.time.LocalDateTime;
import java.util.Map;

import static pl.zaitis.shop.admin.order.service.AdminOrderEmailMessage.createCompletedEmailMessage;
import static pl.zaitis.shop.admin.order.service.AdminOrderEmailMessage.createProcessingEmailMessage;
import static pl.zaitis.shop.admin.order.service.AdminOrderEmailMessage.createRefundEmailMessage;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final AdminOrderRepository orderRepository;
    private final AdminOrderLogRepository adminOrderLogRepository;
    private final EmailClientService emailClientService;

    public Page<AdminOrder> getOrders(Pageable pageable) {
        return orderRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("id").descending())
        );
    }

    public AdminOrder getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public void patchOrder(Long id, Map<String, String> values) {
       AdminOrder adminOrder= orderRepository.findById(id).orElseThrow();
       patchValues(adminOrder, values);
       orderRepository.save(adminOrder);
    }

    private void patchValues(AdminOrder adminOrder, Map<String, String> values) {
        if(values.get("orderStatus") != null){
            processOrderStatusChange(adminOrder, values);

        }
    }

    private void processOrderStatusChange(AdminOrder adminOrder, Map<String, String> values) {
        AdminOrderStatus oldStatus = adminOrder.getOrderStatus();
        AdminOrderStatus newStatus = AdminOrderStatus.valueOf(values.get("orderStatus"));
        adminOrder.setOrderStatus(AdminOrderStatus.valueOf(values.get("orderStatus")));
        logStatusChange(adminOrder.getId(), oldStatus, newStatus);
        sendEmailNotification(newStatus, adminOrder);
    }

    private void sendEmailNotification(AdminOrderStatus newStatus, AdminOrder adminOrder) {
        //send mails to PROCESSING, COMPLETED, REFUND

        if(newStatus == AdminOrderStatus.PROCESSING){
            sendEmail(adminOrder.getEmail(), "Your order "+adminOrder.getId() + " was change status to " + newStatus.getValue(),
                    createProcessingEmailMessage(adminOrder.getId(), newStatus));
        }
        else if(newStatus == AdminOrderStatus.COMPLETED){
            sendEmail(adminOrder.getEmail(), "Your order "+adminOrder.getId() + " was been completed. ",
                    createCompletedEmailMessage(adminOrder.getId(), newStatus));
        }
        else if(newStatus == AdminOrderStatus.REFUND){
            sendEmail(adminOrder.getEmail(), "Your order "+adminOrder.getId() + " was been refund. ",
                    createRefundEmailMessage(adminOrder.getId(), newStatus));

        }
    }



    private void sendEmail(String email, String subject, String message) {
        emailClientService.getUInstance("fakeEmailService").send(email,subject, message);

    }

    private void logStatusChange(Long orderId, AdminOrderStatus oldStatus, AdminOrderStatus newStatus){
        adminOrderLogRepository.save(AdminOrderLog.builder()
                        .created(LocalDateTime.now())
                        .orderId(orderId)
                        .note("change order status from " + oldStatus.getValue() + " to " + newStatus.getValue())
                        .build());

    }
}
