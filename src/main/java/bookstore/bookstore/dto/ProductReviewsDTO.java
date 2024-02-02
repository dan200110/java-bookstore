package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class ProductReviewsDTO {
    private int productId;
    private int userId;
    private int rating;
    private String comment;
    private String reviewDate;
}
