package bookstore.bookstore.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductsPaginationDTO extends PaginationBaseDTO {
    private List<ProductsDTO> productsDTOList;
}
