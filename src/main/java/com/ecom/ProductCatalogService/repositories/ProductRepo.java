package com.ecom.ProductCatalogService.repositories;

import com.ecom.ProductCatalogService.db1.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public  interface ProductRepo extends MongoRepository<Product, ObjectId> {
        public List<Product> findByIsActiveTrue();
        public Page<Product> findByIsActiveTrue(Pageable pageable);
//
        List<Product> findByCategoryAndIsActiveTrue(String category);

       public Page<Product> findByCategoryAndIsActiveTrue(Pageable pageable,String category);
    }

