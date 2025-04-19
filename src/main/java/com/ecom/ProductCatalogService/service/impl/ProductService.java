package com.ecom.ProductCatalogService.service.impl;

import com.ecom.ProductCatalogService.db1.model.Product;
import com.ecom.ProductCatalogService.repositories.ProductRepo;
import com.ecom.ProductCatalogService.service.ProductServiceInterface;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService implements ProductServiceInterface {
@Autowired
private ProductRepo productRepo;
    @Override
    public Product saveProduct(Product product) {
 
        double discount = product.getPrice() * (product.getDiscount() / 100.0);
        double price = product.getPrice() - discount;
        product.setDiscountPrice(price);
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepo.findAll();
    }

    @Override
    public Boolean deleteProduct(String id) {
        ObjectId id1=new ObjectId(id);
       Product product=productRepo.findById(id1).orElse(null);
        productRepo.delete(product);
        if (product!=null)
            return true;
        return false;
    }

    @Override
    public Product getProductById(String id) {

        ObjectId id1=new ObjectId(id);
        return productRepo.findById(id1).orElse(null);
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) {

        Product dbProduct = getProductById(product.getId().toString());
        String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();
        dbProduct.setTitle(product.getTitle());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setPrice(product.getPrice());
        dbProduct.setStock(product.getStock());
        dbProduct.setImage(imageName);
        dbProduct.setDiscount(product.getDiscount());
dbProduct.setIsActive(product.getIsActive());
        double discount = product.getPrice() * (product.getDiscount() / 100.0);
        double price = product.getPrice() - discount;
        dbProduct.setDiscountPrice(price);
//        dbProduct.setPrice(price);
//        elasticSearchService.saveToElasticsearch(product);
        Product updateProduct = productRepo.save(dbProduct);
        return updateProduct ;
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {


        if(category==null || category.isEmpty()){
            List<Product> byIsActiveTrue = productRepo.findByIsActiveTrue();

            return byIsActiveTrue;
        }
        else {
            List<Product> byIsActiveTrue = productRepo.findByCategoryAndIsActiveTrue( category);
//            System.out.println(byIsActiveTrue.get(0).getDiscount());

            System.out.println();
            return byIsActiveTrue;

        }
    }

    @Override
    public Page<Product> getAllActiveProductPagination(Integer pageno, Integer pageSize,String category) {
//        Pageable pageable=PageRequest.of(pageno, pageSize);
        Pageable pageable = PageRequest.of(pageno, pageSize);
        Page<Product> page = null;
        if (category == null || category.isEmpty()) {
            page = productRepo.findByIsActiveTrue(pageable);
        } else {
            page  = productRepo.findByCategoryAndIsActiveTrue(pageable,category);

        }

//        Pageable pageable = PageRequest.of(pageno, pageSize);
//        Page<Product> page = productRepo.findByIsActiveTrue(pageable);
        return page;
    }
}
