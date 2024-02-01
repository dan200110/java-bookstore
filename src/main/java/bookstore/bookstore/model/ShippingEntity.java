package bookstore.bookstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "shipping", schema = "e-commerce", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class ShippingEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "order_id")
    private Integer orderId;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "required_date")
    private Date requiredDate;
    @Basic
    @Column(name = "shipped_date")
    private Date shippedDate;

}
