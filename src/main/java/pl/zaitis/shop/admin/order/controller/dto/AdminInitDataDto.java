package pl.zaitis.shop.admin.order.controller.dto;

import java.util.Map;

public record AdminInitDataDto(Map<String, String> orderStatuses) {
}
