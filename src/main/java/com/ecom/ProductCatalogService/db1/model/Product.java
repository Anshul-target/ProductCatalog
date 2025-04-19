package com.ecom.ProductCatalogService.db1.model;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Document
    public  class Product {

        @Id

        private ObjectId id;
        @Size(max = 15, message = "Title length must be less than 15 characters")
        private String title;

        @Size(max = 5000, message = "Description length must be less than 5000 characters")
        private String description;
        private String category;
        private  Double price;

        private int stock;
        private String image;

        private  int discount;
        private double discountPrice;
        private  boolean isActive;
        public boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(boolean val) {
            this.isActive=val;
        }

    }

