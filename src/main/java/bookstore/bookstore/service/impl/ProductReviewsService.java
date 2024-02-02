package bookstore.bookstore.service.impl;

import bookstore.bookstore.dto.ProductReviewsDTO;
import bookstore.bookstore.dto.ProductReviewsPaginationDTO;
import bookstore.bookstore.model.ProductReviewsEntity;
import bookstore.bookstore.model.ProductsEntity;
import bookstore.bookstore.model.UsersEntity;
import bookstore.bookstore.repository.ProductReviewsRepository;
import bookstore.bookstore.repository.ProductsRepository;
import bookstore.bookstore.service.ProductReviewsInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductReviewsService implements ProductReviewsInterface {
    private final UserService userService;
    private final ProductsRepository productsRepository;
    private final ProductReviewsRepository productReviewsRepository;

    @Override
    public void createProductReview(String jwtToken, ProductReviewsDTO productReviewsDTO) {

        UsersEntity usersEntity = userService.getUserByJwtToken(jwtToken).orElseThrow(() -> new EntityNotFoundException("User not found"));
        ProductsEntity productsEntity = productsRepository.findById(productReviewsDTO.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Create a new ProductReviewsEntity
        ProductReviewsEntity productReview = new ProductReviewsEntity();
        productReview.setProductsEntity(productsEntity);
        productReview.setUsersEntity(usersEntity);
        productReview.setRating(productReviewsDTO.getRating());
        productReview.setComment(productReviewsDTO.getComment());
        productReview.setReviewDate(Date.valueOf(LocalDate.now()));

        // Save the ProductReviewsEntity
        productReviewsRepository.save(productReview);
    }

    @Override
    public ProductReviewsPaginationDTO getProductReviewByProductId(int productId, int pageNo, int pageSize, String sortBy, String sortDir) {
        return productsRepository.findById(productId)
                .map(productsEntity -> {
                    Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
                    Page<ProductReviewsEntity> productReviewsEntities = productReviewsRepository.findAllByProductsEntity_Id(productId, pageable);

                    return mapToPagination(productReviewsEntities, mapToDTOList(productReviewsEntities));
                })
                .orElse(new ProductReviewsPaginationDTO());
    }

    private List<ProductReviewsDTO> mapToDTOList(Page<ProductReviewsEntity> productReviewsEntities) {
        return productReviewsEntities.map(this::mapToDTO).toList();
    }

    private ProductReviewsDTO mapToDTO(ProductReviewsEntity productReviewsEntity) {
        ProductReviewsDTO productReviewsDTO = new ProductReviewsDTO();
        productReviewsDTO.setProductId(productReviewsEntity.getProductsEntity().getId());
        productReviewsDTO.setReviewDate(String.valueOf(productReviewsEntity.getReviewDate()));
        productReviewsDTO.setComment(productReviewsEntity.getComment());
        productReviewsDTO.setRating(productReviewsEntity.getRating());
        productReviewsDTO.setUserId(productReviewsEntity.getUsersEntity().getId());

        return productReviewsDTO;
    }

    private Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(pageNo, pageSize, sort);
    }

    private ProductReviewsPaginationDTO mapToPagination(Page<ProductReviewsEntity> productReviewsEntities, List<ProductReviewsDTO> productReviewsDTOList) {
        ProductReviewsPaginationDTO productReviewsPaginationDTO = mapToBasePagination(productReviewsEntities);
        productReviewsPaginationDTO.setProductReviewsDTOList(productReviewsDTOList);
        return productReviewsPaginationDTO;
    }

    private ProductReviewsPaginationDTO mapToBasePagination(Page<ProductReviewsEntity> productReviewsEntities) {
        ProductReviewsPaginationDTO productReviewsPaginationDTO = new ProductReviewsPaginationDTO();
        productReviewsPaginationDTO.setPageNo(productReviewsEntities.getNumber());
        productReviewsPaginationDTO.setPageSize(productReviewsEntities.getSize());
        productReviewsPaginationDTO.setTotalElements(productReviewsEntities.getTotalElements());
        productReviewsPaginationDTO.setTotalPages(productReviewsEntities.getTotalPages());
        productReviewsPaginationDTO.setLast(productReviewsEntities.isLast());

        return productReviewsPaginationDTO;
    }
}

