package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "users", schema = "e-commerce", catalog = "")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "user_name")
    private String userName;
    @Basic
    @Column(name = "user_type")
    private String userType;
    @Basic
    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<CartItemsEntity> cartItemsEntityList;

    public void addCartItems(CartItemsEntity cartItemsEntity) {
        if (cartItemsEntityList == null) {
            cartItemsEntityList = new ArrayList<>();
        }

        cartItemsEntityList.add(cartItemsEntity);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id && Objects.equals(userName, that.userName) && Objects.equals(userType, that.userType) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userType, password);
    }

    @Override
    public String toString() {
        return "id: " + id + "userName: " + userName + "userType: " + userType;
    }
}