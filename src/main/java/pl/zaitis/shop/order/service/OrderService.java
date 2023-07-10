package pl.zaitis.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaitis.shop.common.mail.EmailClientService;
import pl.zaitis.shop.common.model.Cart;
import pl.zaitis.shop.common.repository.CartItemRepository;
import pl.zaitis.shop.common.repository.CartRepository;
import pl.zaitis.shop.order.model.Order;
import pl.zaitis.shop.order.model.Payment;
import pl.zaitis.shop.order.model.Shipment;
import pl.zaitis.shop.order.model.dto.OrderDto;
import pl.zaitis.shop.order.model.dto.OrderSummary;
import pl.zaitis.shop.order.repository.OrderRepository;
import pl.zaitis.shop.order.repository.OrderRowRepository;
import pl.zaitis.shop.order.repository.PaymentRepository;
import pl.zaitis.shop.order.repository.ShipmentRepository;

import static pl.zaitis.shop.order.service.mapper.OrderMapper.createdNewOrder;
import static pl.zaitis.shop.order.service.mapper.OrderMapper.createdOrderSummary;
import static pl.zaitis.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.zaitis.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartItemRepository cartItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto, Long userId) {
        Cart cart= cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment =shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment =paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        Order newOrder = orderRepository.save(createdNewOrder(orderDto, cart, shipment, payment, userId));
        saveOrderRows(cart, newOrder.getId(), shipment);
        clearOrderCart(orderDto);
        emailClientService.getUInstance()
                .send(newOrder.getEmail(),
                "Your order was been created",
                createEmailMessage(newOrder));
        return createdOrderSummary(payment, newOrder);
    }

    private void clearOrderCart(OrderDto orderDto) {
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
    }

    private String createEmailMessage(Order order){
        return "Your order with id: "+ order.getId()+" will be send.";
    }

    private void saveOrderRows(Cart cart, Long id, Shipment shipment) {
        saveProductRows(cart, id);
        saveShipmentRow(id, shipment);
    }

    private void saveShipmentRow(Long id, Shipment shipment) {
        orderRowRepository.save(mapToOrderRow(id, shipment));
    }

    private void saveProductRows(Cart cart, Long id) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRowWithQuantity(id, cartItem))
                .peek(orderRowRepository::save)
                .toList();
    }
}
