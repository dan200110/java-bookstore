package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class ProductsDTO {
    private int productId;
    private String categoryName;
    private String productName;
    private Integer productPrice;
    private Integer quantity;
    private String productPhoto;
    private String productDetails;
    private int providerId;
}

