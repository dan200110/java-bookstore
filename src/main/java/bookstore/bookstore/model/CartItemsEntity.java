package bookstore.bookstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "cart_item", schema = "e-commerce", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class CartItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity productsEntity;
}
