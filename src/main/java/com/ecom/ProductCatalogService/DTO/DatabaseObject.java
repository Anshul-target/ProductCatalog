package com.ecom.ProductCatalogService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseObject {

    private String id;

    private String name;
    private String imageName;

    public Boolean isActive;
}
