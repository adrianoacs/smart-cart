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

import java.util.List;

@Entity
@Table(name="STORE")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "NAME", length = 250)
    private String name;

    @Column(name = "CNPJ", length = 20)
    private String cnpj;

    @Column(name = "ADDRESS", length = 400)
    private String address;

    @OneToMany
    @JoinColumn(name = "STORE_ID", nullable = false, updatable = false, insertable = false)
    private List<Invoice> invoices;

}
