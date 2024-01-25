package bookstore.bookstore.service;

import bookstore.bookstore.model.ProductsEntity;
import bookstore.bookstore.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService implements ProductsServiceInterface{
    private final ProductsRepository productsRepository;
    @Override
    public Optional<List<ProductsEntity>> findAllProducts() {
        return Optional.of(productsRepository.findAll());
    }
}
