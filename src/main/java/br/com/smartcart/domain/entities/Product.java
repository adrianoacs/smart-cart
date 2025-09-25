package br.com.smartcart.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name="PRODUCT")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "NAME", length = 250)
    private String name;

    @OneToMany
    @JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false, insertable = false)
    private List<ProductPrice> prices;

    @ManyToMany
    @JoinTable(name = "SHOPPING_ITEMS_PRODUCT",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "SHOPPING_ITEMS_ID")
    )
    private List<ShoppingItems> shoppingItemsList;
}
