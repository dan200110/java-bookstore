package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class OrderItemsDTO {
    private int productId;
    private int orderId;
    private int price;
    private int quantity;
}
