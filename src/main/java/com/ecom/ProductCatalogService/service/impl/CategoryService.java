package com.ecom.ProductCatalogService.service.impl;

import com.ecom.ProductCatalogService.DTO.DatabaseObject;

import com.ecom.ProductCatalogService.db1.model.Category;
import com.ecom.ProductCatalogService.repositories.CategoryRepo;
import com.ecom.ProductCatalogService.service.CategoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CategoryInterface {
   @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public Category saveCategory(Category categoryModel) {
        return categoryRepo.save(categoryModel);
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepo.existsByName(name);
    }

    @Override
    public List<DatabaseObject> getAllCategory() {
        List<Category> categories = categoryRepo.findAll();  // Get all categories from repository

        // Convert List<Category> to List<DatabaseObject>
        return categories.stream()
                .map(this::convertToDatabaseObject)
                .collect(Collectors.toList());




    }
    private DatabaseObject convertToDatabaseObject(Category category) {
        DatabaseObject dbObject = new DatabaseObject();
        dbObject.setId(category.getId().toString());
        dbObject.setName(category.getName());
        dbObject.setImageName(category.getImageName());
        dbObject.setIsActive(category.getIsActive());
        // Set other properties as needed
        return dbObject;
    }

    @Override
    public Boolean deleteCategory(String name) {
//        TODO to make an Global Exceptioin for displaying the proper error message in the jsoon format
try {
    Category category = categoryRepo.findByName(name);
    if(category!=null){
        categoryRepo.delete(category);
        return true;
    }
}
catch (Exception e){
    System.out.println(e.getMessage());
}


        return false;

    }

    @Override
    public Category getCategoryByName(String name) {
        Category category= categoryRepo.findByName(name);
        if(category!=null){
            return category;
        }
        return null;
    }

    @Override
    public Boolean updateCategory(Category category) {
//        categoryRepo
if (category!=null){
    categoryRepo.save(category);
    return true;
}
        return false;

    }

    @Override
    public List<DatabaseObject> getAllActiveCategory() {
        List<Category> alActiveCategory=categoryRepo.findByIsActiveTrue();
        List<DatabaseObject> databaseObject = alActiveCategory.stream().map(category -> {
            DatabaseObject dbObject = new DatabaseObject();
            dbObject.setId(category.getId().toString());
            dbObject.setName(category.getName());
            dbObject.setImageName(category.getImageName());
            dbObject.setIsActive(category.getIsActive());
            // Set other properties as needed
            return dbObject;

        }).collect(Collectors.toList());
        return databaseObject;
    }


}
