package otel.demo.order_service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record Order(Long id, Long companyId, ZonedDateTime orderDate, BigDecimal totalAmount) {
}
