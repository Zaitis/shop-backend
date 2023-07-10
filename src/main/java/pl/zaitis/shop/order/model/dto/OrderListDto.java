package pl.zaitis.shop.order.model.dto;

import lombok.Builder;
import pl.zaitis.shop.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderListDto(
         Long id,
         LocalDateTime placeDate,
         OrderStatus orderStatus,
         BigDecimal grossValue
        ) {
}
