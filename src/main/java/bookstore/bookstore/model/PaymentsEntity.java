package bookstore.bookstore.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "payments", schema = "e-commerce", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class PaymentsEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "payment_type")
    private String paymentType;
    @Basic
    @Column(name = "order_id")
    private Integer orderId;
    @Basic
    @Column(name = "amount")
    private Integer amount;
    @Basic
    @Column(name = "status")
    private String status;
}
