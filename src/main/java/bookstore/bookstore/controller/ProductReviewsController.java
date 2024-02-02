package bookstore.bookstore.controller;

import bookstore.bookstore.dto.ProductReviewsDTO;
import bookstore.bookstore.dto.ProductReviewsPaginationDTO;
import bookstore.bookstore.service.impl.ProductReviewsService;
import bookstore.bookstore.utils.AppConstants;
import bookstore.bookstore.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/product_review")
@RequiredArgsConstructor
public class ProductReviewsController {
    private final ProductReviewsService productReviewsService;
    private final JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<String> createProductReview(
            @RequestHeader(name = "Authorization") String authHeader,
            @RequestBody ProductReviewsDTO productReviewsDTO
    ) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);
        try {
            productReviewsService.createProductReview(jwtToken, productReviewsDTO);
            return new ResponseEntity<>("Product review created successfully", HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Product or user not found", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_by_product_id")
    public ProductReviewsPaginationDTO getByProductId(
            @RequestParam(value = "productId") int productId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return productReviewsService.getProductReviewByProductId(productId, pageNo, pageSize, sortBy, sortDir);
    }
}
