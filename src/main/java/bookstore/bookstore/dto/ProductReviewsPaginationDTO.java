package bookstore.bookstore.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductReviewsPaginationDTO extends PaginationBaseDTO {
    private List<ProductReviewsDTO> productReviewsDTOList;

}
