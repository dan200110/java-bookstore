package bookstore.bookstore.dto;

import lombok.Data;

@Data
public class PaginationBaseDTO {
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
