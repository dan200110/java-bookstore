package bookstore.bookstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "product_reviews", schema = "e-commerce", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class ProductReviewsEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity productsEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity usersEntity;

    @Basic
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "review_date")
    private Date reviewDate;

}
