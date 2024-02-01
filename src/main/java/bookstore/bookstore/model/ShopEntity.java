package bookstore.bookstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "shop", schema = "e-commerce", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class ShopEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "shop_owner_id")
    private Integer shopOwnerId;
    @Basic
    @Column(name = "revenue")
    private Integer revenue;

}