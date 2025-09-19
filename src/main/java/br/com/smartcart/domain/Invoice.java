package br.com.smartcart.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="INVOICE")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_ID")
    private Long invoiceId;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "NUMBER", length = 100)
    private String number;

    @OneToMany
    @JoinColumn(name = "INVOICE_ID", nullable = false, updatable = false, insertable = false)
    private List<ProductPrice> prices;

    @Column(name = "DT_ISSUE")
    private LocalDateTime dtIssue;

}
