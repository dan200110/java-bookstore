package bookstore.bookstore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemsDTO extends JwtModel {
    private final int id;
    private final int productId;
    private final int quantity;
}
