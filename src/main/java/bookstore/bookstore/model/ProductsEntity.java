package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ProductsEntity extends BaseEntity {
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

}