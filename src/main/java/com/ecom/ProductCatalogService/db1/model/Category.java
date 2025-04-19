package com.ecom.ProductCatalogService.db1.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class Category {
    @Id
    private ObjectId id;

    private String name;
    private String imageName;

    public Boolean isActive; //User can see
}
