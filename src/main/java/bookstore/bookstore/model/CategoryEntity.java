package bookstore.bookstore.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "category", schema = "e-commerce", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "category_name")
    private String categoryName;
}
