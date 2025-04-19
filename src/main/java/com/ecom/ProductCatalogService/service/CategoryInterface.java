package com.ecom.ProductCatalogService.service;

import com.ecom.ProductCatalogService.DTO.DatabaseObject;

import com.ecom.ProductCatalogService.db1.model.Category;

import java.util.List;

public interface CategoryInterface {

//  To save in the db
    public Category saveCategory(Category categoryModel);

    public Boolean existCategory(String name);
//    GEt all CategoryService
public List<DatabaseObject> getAllCategory();

//Delete the category by admin
    public Boolean deleteCategory(String name);

    public Category getCategoryByName(String name);

    public Boolean updateCategory(Category category);

    public List<DatabaseObject> getAllActiveCategory();

}
