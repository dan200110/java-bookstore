package bookstore.bookstore.controller;

import bookstore.bookstore.controller.base.BaseControllerTest;
import bookstore.bookstore.dto.ProductsDTO;
import bookstore.bookstore.service.impl.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductsControllerTest extends BaseControllerTest {
    private static final String GET_ALL_PRODUCTS_PATH = "/product/get_products";

    @Autowired
    private ProductsController productsController;

    @MockBean
    private ProductsService productsService;

    @Test
    void testGetAllProducts() throws Exception {
        // Arrange
        List<ProductsDTO> mockProductsDTOList = createMockProductsDTOList();
        when(productsService.findAllProducts()).thenReturn(Optional.of(mockProductsDTOList));

        mockMvc.perform(get(GET_ALL_PRODUCTS_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].productName", is("Smartphone")))
                .andExpect(jsonPath("$[1].productName", is("T-Shirt")));

    }

    private List<ProductsDTO> createMockProductsDTOList() {
        ProductsDTO product1 = new ProductsDTO();
        product1.setProductId(1);
        product1.setCategoryName("Electronics");
        product1.setProductName("Smartphone");
        product1.setProductPrice(500);
        product1.setQuantity(10);
        product1.setProductPhoto("smartphone.jpg");
        product1.setProductDetails("Latest smartphone with advanced features");
        product1.setProviderId(101);

        ProductsDTO product2 = new ProductsDTO();
        product2.setProductId(2);
        product2.setCategoryName("Clothing");
        product2.setProductName("T-Shirt");
        product2.setProductPrice(20);
        product2.setQuantity(50);
        product2.setProductPhoto("shirt.jpg");
        product2.setProductDetails("Comfortable cotton T-shirt");
        product2.setProviderId(102);
        return List.of(product1, product2);
    }
}
