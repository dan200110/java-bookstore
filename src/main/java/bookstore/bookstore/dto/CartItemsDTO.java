package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class CartItemsDTO {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
}
