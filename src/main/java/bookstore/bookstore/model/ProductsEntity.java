package bookstore.bookstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products", schema = "e-commerce", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class ProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "product_name")
    private String productName;
    @Basic
    @Column(name = "product_price")
    private Integer productPrice;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @Basic
    @Column(name = "product_details")
    private String productDetails;
    @Basic
    @Column(name = "product_photo")
    private String productPhoto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_category")
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private UsersEntity usersEntity;

//    @OneToMany(mappedBy = "productsEntity", fetch = FetchType.LAZY)
//    private List<CartItemsEntity> cartItemsEntityList;
}