package br.com.smartcart.domain.entities;

import jakarta.persistence.CascadeType;
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
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "DT_UPD")
    private LocalDateTime dtUpd;

    @OneToMany(mappedBy = "shoppingItems")
    private List<ShoppingItemsProduct> shoppingItemsProducts;

    @Column(name = "CUSTOMER_ID")
    private Long costumerId;

    @Column(name = "BLOCKED")
    private boolean blocked;

    public List<ShoppingItemsProduct> getShoppingItemsProducts() {
        if(CollectionUtils.isEmpty(shoppingItemsProducts)){
            shoppingItemsProducts = new ArrayList<>();
        }
        return shoppingItemsProducts;
    }

}
