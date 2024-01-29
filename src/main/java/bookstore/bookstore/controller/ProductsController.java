package bookstore.bookstore.controller;

import bookstore.bookstore.dto.CartItemsDTO;
//import bookstore.bookstore.dto.NewProductsDTO;
import bookstore.bookstore.dto.NewProductsDTO;
import bookstore.bookstore.dto.ResponseProductsDTO;
import bookstore.bookstore.model.ProductsEntity;
import bookstore.bookstore.service.ProductsService;
import bookstore.bookstore.util.JwtUtils;
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
    public ResponseEntity<List<ResponseProductsDTO>> getAllProducts() {
        Optional<List<ResponseProductsDTO>> productsDTOList = productsService.findAllProducts();

        return productsDTOList
                .map(productsDTOs -> new ResponseEntity<>(productsDTOs, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/create_product")
    public ResponseEntity<String> createProduct(@RequestHeader(name = "Authorization") String authHeader, @RequestBody NewProductsDTO newProductsDTO) {
        String jwtToken = jwtUtils.extractJwtTokenFromAuthHeader(authHeader);

        if (Boolean.TRUE.equals(productsService.createProduct(jwtToken, newProductsDTO))) {
            return new ResponseEntity<>("Adding new product is successful", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to add new product", HttpStatus.BAD_REQUEST);
    }
}
