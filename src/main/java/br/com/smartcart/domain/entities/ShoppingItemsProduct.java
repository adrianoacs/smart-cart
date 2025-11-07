package br.com.smartcart.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="SHOPPING_ITEMS_PRODUCT")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItemsProduct {

    @EmbeddedId
    private ShoppingItemsProductId id = new ShoppingItemsProductId();

    @ManyToOne()
    @MapsId("shoppingItemsId")
    @JoinColumn(name = "SHOPPING_ITEMS_ID")
    private ShoppingItems shoppingItems;

    @ManyToOne()
    @MapsId("productId")
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "POSITION")
    private Integer position;

}
