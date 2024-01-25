package bookstore.bookstore.controller;

import bookstore.bookstore.model.ProductsEntity;
import bookstore.bookstore.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping("/get_products")
    public ResponseEntity<List<ProductsEntity>> getAllProducts() {
        Optional<List<ProductsEntity>> productsEntityList = productsService.findAllProducts();

        if (productsEntityList.isPresent()) {
            return new ResponseEntity<>(productsEntityList.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
    }
}
