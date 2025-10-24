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
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="SHOPPING_ITEMS")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOPPING_ITEMS_ID")
    private Long shoppingItemsId;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "DT_ADD")
    private LocalDateTime dtAdd;

//    @ManyToMany(mappedBy = "shoppingItems")
//    private List<Product> products;

    @Column(name = "CUSTOMER_ID")
    private Long costumerId;

}
