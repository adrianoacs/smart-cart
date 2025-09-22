package br.com.smartcart.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_PRICE")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPrice {

    @Id
    @Column(name = "PRICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "INVOICE_ID")
    private Long invoiceId;

    @Column(name = "PRICE")
    private BigDecimal price;
}
