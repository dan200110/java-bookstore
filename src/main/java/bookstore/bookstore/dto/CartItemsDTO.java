package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class CartItemsDTO {
    private final int id;
    private final String jwt;
    private final int productId;
    private final int quantity;
}
