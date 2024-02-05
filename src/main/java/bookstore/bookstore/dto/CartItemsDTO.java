package bookstore.bookstore.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemsDTO {
    private int id;
    private int userId;
    private int productId;

    @Min(value = 1, message = "Quantity must be large than 0")
    private int quantity;
}
