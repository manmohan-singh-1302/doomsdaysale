package com.doomsdaysale.service;

import com.doomsdaysale.exceptions.ProductException;
import com.doomsdaysale.model.Product;
import com.doomsdaysale.model.Seller;
import com.doomsdaysale.request.CreateProductRequest;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest req, Seller seller);

    void deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId, Product product) throws ProductException;

    Product findProductById(Long ProductId) throws ProductException;

    List<Product> searchProduct(String query);

    Page<Product> getAllProducts( String category, String brand, String colors,
                                  String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount,
                                  String sort, String stock, Integer pageNumber);

    List<Product> getProductBySellerId(Long sellerId);



}
