package pl.zaitis.shop.cart.model;

import jakarta.persistence.*;
import lombok.*;
import pl.zaitis.shop.common.model.Product;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @OneToOne
    private Product product;
    private Long cartId;
}
