package pl.zaitis.shop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zaitis.shop.order.model.dto.InitOrder;
import pl.zaitis.shop.order.model.dto.OrderDto;
import pl.zaitis.shop.order.model.dto.OrderListDto;
import pl.zaitis.shop.order.model.dto.OrderSummary;
import pl.zaitis.shop.order.service.OrderService;
import pl.zaitis.shop.order.service.PaymentService;
import pl.zaitis.shop.order.service.ShipmentService;
import pl.zaitis.shop.security.repository.UserRepository;
import pl.zaitis.shop.security.repository.model.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @PostMapping
    public OrderSummary placeOrder(@RequestBody OrderDto orderDto, @AuthenticationPrincipal String name) {
        User user =userRepository.findByUsername(name);
        return orderService.placeOrder(orderDto, user.getId());
    }

    @GetMapping("/initData")
    public InitOrder initData() {
        return InitOrder.builder()
                .shipments(shipmentService.getShipments())
                .payments(paymentService.getPayments())
                .build();
    }

    @GetMapping
    public List<OrderListDto> getOrder(@AuthenticationPrincipal String name){
        if(name == null){
            throw new IllegalArgumentException("User is empty.");
        }
        User user =userRepository.findByUsername(name);
        return orderService.getOrdersForCustomer(user.getId());
    }
}
