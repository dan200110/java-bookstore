package bookstore.bookstore.service;

import bookstore.bookstore.dto.ProductReviewsDTO;
import bookstore.bookstore.dto.ProductReviewsPaginationDTO;

public interface ProductReviewsInterface {
    void createProductReview(String jwtToken, ProductReviewsDTO productReviewsDTO);

    ProductReviewsPaginationDTO getProductReviewByProductId(int productId, int pageNo, int pageSize, String sortBy, String sortDir);
}
