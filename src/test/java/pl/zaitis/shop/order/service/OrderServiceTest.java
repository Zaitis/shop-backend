package pl.zaitis.shop.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zaitis.shop.common.mail.EmailClientService;
import pl.zaitis.shop.common.mail.FakeEmailService;
import pl.zaitis.shop.common.model.Cart;
import pl.zaitis.shop.common.model.CartItem;
import pl.zaitis.shop.common.model.Product;
import pl.zaitis.shop.common.repository.CartItemRepository;
import pl.zaitis.shop.common.repository.CartRepository;
import pl.zaitis.shop.order.model.OrderStatus;
import pl.zaitis.shop.order.model.Payment;
import pl.zaitis.shop.order.model.PaymentType;
import pl.zaitis.shop.order.model.Shipment;
import pl.zaitis.shop.order.model.dto.OrderDto;
import pl.zaitis.shop.order.model.dto.OrderSummary;
import pl.zaitis.shop.order.repository.OrderRepository;
import pl.zaitis.shop.order.repository.OrderRowRepository;
import pl.zaitis.shop.order.repository.PaymentRepository;
import pl.zaitis.shop.order.repository.ShipmentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ShipmentRepository shipmentRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRowRepository orderRowRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private EmailClientService emailSender;

    @InjectMocks
    private  OrderService orderService;


    @Test
    void shouldPlaceOrder(){
        //given
        OrderDto orderDto = createOrder();
        when(cartRepository.findById(any())).thenReturn(createCart());
//        when(shipmentRepository.findById(any())).thenReturn(createShipment());
        when(paymentRepository.findById(any())).thenReturn(createPayment());
        when(orderRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        when(emailSender.getUInstance()).thenReturn(new FakeEmailService());

        //when
        OrderSummary orderSummary=orderService.placeOrder(orderDto, 1L);
        //then
        assertThat(orderSummary).isNotNull();
        assertThat(orderSummary.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(orderSummary.getGrossValue()).isEqualTo(new BigDecimal("48.33"));
        assertThat(orderSummary.getPayment().getType()).isEqualTo(PaymentType.BANK_TRANSFER);
        assertThat(orderSummary.getPayment().getName()).isEqualTo("payment test");
        assertThat(orderSummary.getPayment().getId()).isEqualTo(5L);

    }

    private Optional<Payment> createPayment() {
        return Optional.of(Payment.builder()
                .id(5L)
                .name("payment test")
                .type(PaymentType.BANK_TRANSFER)
                .defaultPayment(true)
                .build()
                );
    }

//    private Optional<Shipment> createShipment() {
//        return Optional.of(Shipment.builder()
//                .id(2L)
//                .price((new BigDecimal("15.00")))
//                .defaultShipment().build());
//    }

    private Optional<Cart> createCart() {
        return Optional.of(Cart.builder()
                        .id(1L)
                        .created(LocalDateTime.now())
                        .items(createItems())
                        .build());
    }

    private List<CartItem> createItems() {
        return List.of(
                CartItem.builder()
                        .id(1L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(1L)
                                .price(new BigDecimal("11.11"))
                                .build())
                        .build(),
                CartItem.builder()
                        .id(2L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(2L)
                                .price(new BigDecimal("22.22"))
                                .build())
                        .build()
        );
    }

    private static OrderDto createOrder() {
        return OrderDto.builder()
                .firstname("firstname")
                .lastname("lastname")
                .street("street")
                .zipcode("zipcode")
                .city("city")
                .email("email")
                .phone("phone")
                .cartId(1L)
                .shipmentId(2L)
                .paymentId(3L)
                .build();
    }
}