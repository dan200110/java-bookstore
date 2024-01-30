package bookstore.bookstore.controller;

import bookstore.bookstore.dto.ProductsDTO;
import bookstore.bookstore.dto.ProductsPaginationDTO;
import bookstore.bookstore.service.ProductsService;
import bookstore.bookstore.utils.AppConstants;
import bookstore.bookstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final JwtUtils jwtUtils;

    @GetMapping("/get_products")
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        Optional<List<ProductsDTO>> productsDTOList = productsService.findAllProducts();

        return productsDTOList
                .map(productsDTOs -> new ResponseEntity<>(productsDTOs, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/create_product")
    public ResponseEntity<String> createProduct(@RequestHeader(name = "Authorization") String authHeader, @RequestBody ProductsDTO productsDTO) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);

        if (Boolean.TRUE.equals(productsService.createProduct(jwtToken, productsDTO))) {
            return new ResponseEntity<>("Adding new product is successful", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to add new product", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get_products_pagination")
    public ProductsPaginationDTO getAllProductsPagination(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return productsService.getAllProductsPagination(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/get_products_by_name")
    public ProductsPaginationDTO getProductsByName(
            @RequestParam("query") String query,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return productsService.getProductsByName(query, pageNo, pageSize, sortBy, sortDir);
    }
}
