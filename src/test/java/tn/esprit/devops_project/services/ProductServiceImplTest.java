package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct() {
        Product productToAdd = new Product(
                null,
                "New Product",
                15.0f,
                10,
                ProductCategory.ELECTRONICS,
                null
        );

        Stock mockStock = new Stock();
        when(stockRepository.findById(anyLong())).thenReturn(Optional.of(mockStock));

        when(productRepository.save(productToAdd)).thenReturn(productToAdd);

        Product addedProduct = productService.addProduct(productToAdd, 1L);

        assertEquals(productToAdd, addedProduct);
    }

    @Test
    void retrieveProduct() {
        Long productIdToRetrieve = 1L;
        Product expectedProduct = new Product(
                productIdToRetrieve,
                "Product 1",
                10.0f,
                5,
                ProductCategory.ELECTRONICS,
                null
        );
        when(productRepository.findById(productIdToRetrieve)).thenReturn(Optional.of(expectedProduct));

        Product retrievedProduct = productService.retrieveProduct(productIdToRetrieve);

        assertEquals(expectedProduct, retrievedProduct);
    }

    @Test
    void retreiveAllProduct() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(
                null,
                "Product 1",
                10.0f,
                5,
                ProductCategory.ELECTRONICS,
                null
        ));
        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.retreiveAllProduct();

        assertEquals(expectedProducts.size(), actualProducts.size());
    }

    @Test
    void retrieveProductByCategory() {
        ProductCategory categoryToRetrieve = ProductCategory.ELECTRONICS;
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(
                null,
                "Product 1",
                10.0f,
                5,
                categoryToRetrieve,
                null
        ));
        when(productRepository.findByCategory(categoryToRetrieve)).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.retrieveProductByCategory(categoryToRetrieve);


        assertEquals(expectedProducts.size(), actualProducts.size());
    }

    @Test
    void deleteProduct() {
        Long productIdToDelete = 1L;

        productService.deleteProduct(productIdToDelete);

        verify(productRepository, times(1)).deleteById(productIdToDelete);
    }

    @Test
    void retreiveProductStock() {
        Long stockIdToRetrieve = 1L;
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(
                null,
                "Product 1",
                10.0f,
                5,
                ProductCategory.ELECTRONICS,
                null
        ));
        when(productRepository.findByStockIdStock(stockIdToRetrieve)).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.retreiveProductStock(stockIdToRetrieve);

        assertEquals(expectedProducts.size(), actualProducts.size());
    }
}
