package com.ecom.ProductCatalogService.repositories;

//import Category;
import com.ecom.ProductCatalogService.db1.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepo extends MongoRepository<Category, ObjectId> {

    public Category findByName(String name);
    public Boolean existsByName(String name);

    public List<Category> findByIsActiveTrue();
//    Optional<Category> findById(ObjectId id);

}
