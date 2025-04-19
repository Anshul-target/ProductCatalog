package com.ecom.ProductCatalogService.service;

import com.ecom.ProductCatalogService.db1.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceInterface {
    public Product saveProduct(Product product);

    public List<Product> getAllProducts();

    public Boolean deleteProduct(String id);

    public Product getProductById(String id);

    public Product updateProduct(Product product, MultipartFile file);

    public List<Product> getAllActiveProducts(String category);

//    List<Product> searchProduct(String ch);
    public Page <Product> getAllActiveProductPagination(Integer pageno,Integer pageSize,String category);
}
