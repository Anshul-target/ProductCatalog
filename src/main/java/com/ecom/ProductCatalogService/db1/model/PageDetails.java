package com.ecom.ProductCatalogService.db1.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PageDetails
{
    int number ;
    long totalElements ;
    int totalPages;
    boolean first;
    boolean last;
}
