package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class OrdersDTO {
    private int totalAmount;
    private String status;
}
