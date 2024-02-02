package bookstore.bookstore.controller;

import bookstore.bookstore.dto.CartItemsDTO;
import bookstore.bookstore.exception.ProductOutOfStockException;
import bookstore.bookstore.model.CategoryEntity;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.service.impl.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoriesService categoriesService;

    @PostMapping("/create_category")
    public ResponseEntity<String> createCategory(@RequestParam String categoryName) {
        try {
            categoriesService.createCategory(categoryName);
            return ResponseEntity.ok("Category created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category creation failed. Please try again.");
        }
    }

    @GetMapping("/get_all_category")
    public ResponseEntity<List<CategoryEntity>> getAllCategory() {

        Optional<List<CategoryEntity>> categoryEntityList = categoriesService.getAllCategory();

        return categoryEntityList.map(categoryEntities -> new ResponseEntity<>(categoryEntities, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST));

    }
}
