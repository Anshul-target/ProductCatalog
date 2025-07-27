package com.ecom.ProductCatalogService.controller;

import com.ecom.ProductCatalogService.DTO.DatabaseObject;
import com.ecom.ProductCatalogService.DTO.Product_DTO;

import com.ecom.ProductCatalogService.db1.model.Category;
import com.ecom.ProductCatalogService.db1.model.PageDetails;
import com.ecom.ProductCatalogService.db1.model.Product;
import com.ecom.ProductCatalogService.service.impl.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ecom.ProductCatalogService.service.impl.CategoryService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
//CategoryService
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
//    Interservice communication

    public   Product_DTO convertToDTO(Product product_dto){
        Product_DTO product=new Product_DTO();
        product.setId(product_dto.getId().toString());
        product.setPrice(product_dto.getPrice());
        product.setCategory(product_dto.getCategory());
        product.setImage(product_dto.getImage());
        product.setTitle(product_dto.getTitle());
        product.setDescription(product_dto.getDescription());
        product.setStock(product_dto.getStock());
        product.setDiscountPrice(product_dto.getDiscountPrice());
        product.setDiscount(product_dto.getDiscount());
        product.setIsActive(product_dto.getIsActive());
        return product;
    }

    public Product convertToProduct(Product_DTO product_dto){
        Product product=new Product();
        ObjectId id=new ObjectId(product_dto.getId());
        product.setId(id);
        product.setPrice(product_dto.getPrice());
        product.setCategory(product_dto.getCategory());
        product.setImage(product_dto.getImage());
        product.setTitle(product_dto.getTitle());
        product.setDescription(product_dto.getDescription());
        product.setStock(product_dto.getStock());
        product.setDiscountPrice(product_dto.getDiscountPrice());
        product.setDiscount(product_dto.getDiscount());
        product.setIsActive(product_dto.getIsActive());
        return product;
    }

    @GetMapping("/")
    public  String welcome(){
        return "Hello";
    }
 @PostMapping("/saveCategory")

 public ResponseEntity<Boolean> saveCategory(@ModelAttribute Category categoryModel) throws IOException {

     // Ch-eck if the category name already exists

//     String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg";
//     categoryModel.setImageName(imageName);


     if ( categoryModel.getImageName()!=null && categoryService.existCategory(categoryModel.getName())) {
         return new ResponseEntity<>(false, HttpStatus.OK);  // Category exists
     } else {
         // Save category and handle response
         Category savedCategory = categoryService.saveCategory(categoryModel);
         if (!ObjectUtils.isEmpty(savedCategory)) {
             return new ResponseEntity<>(true, HttpStatus.OK);  // Saved successfully
         } else {
             return new ResponseEntity<>(false, HttpStatus.OK);  // Save failed
         }
     }
 }

@GetMapping("/getAllCategory")
public ResponseEntity<List<DatabaseObject>> getAllCategory() {
    List<DatabaseObject> allCategory = categoryService.getAllCategory();
    // Fetch all categories

    return new ResponseEntity<>(allCategory, HttpStatus.OK);  // Return the list with HTTP 200 OK status
}


@GetMapping("/getAllActiveCategory")
public ResponseEntity<List<DatabaseObject>> getAllActiveCategory() {
    List<DatabaseObject> allCategory = categoryService.getAllActiveCategory();
    // Fetch all categories
if(allCategory!=null)
    return new ResponseEntity<>(allCategory, HttpStatus.OK);  // Return the list with HTTP 200 OK status
    return new ResponseEntity<>(allCategory, HttpStatus.NOT_FOUND);  // Return the list with HTTP 200 OK status
}

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable String id){
    System.out.println("Entered the delete endopoint");


    Boolean isDeleted = categoryService.deleteCategory(id);
    if (isDeleted) {
        System.out.println("Sucessfully deleted");
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    return new ResponseEntity<Boolean>(false, HttpStatus.OK);
}


//corrections here also
    @GetMapping("/editCategory/{id}")
    public ResponseEntity<DatabaseObject> editCategory(@PathVariable String id){



        Category dto = categoryService.getCategoryByName(id);

          DatabaseObject category=new DatabaseObject();

        category.setId(dto.getId().toString());
        category.setName(dto.getName());
        category.setIsActive(dto.getIsActive());
        category.setImageName(dto.getImageName());
        if(dto!=null)
        {
            System.out.println("Succefully get the editing Category");
            return  new ResponseEntity<DatabaseObject>(category,HttpStatus.OK);

        }
        else
            return  new ResponseEntity<DatabaseObject>(category,HttpStatus.NOT_FOUND);
    }

    @PutMapping ("/updateCategory")
    public ResponseEntity<Boolean> updateCategory(@ModelAttribute DatabaseObject dto){

ObjectId id1=new ObjectId(dto.getId());
          Category category=new Category();
          category.setId(id1);
          category.setName(dto.getName());
          category.setIsActive(dto.getIsActive());
          category.setImageName(dto.getImageName());
//          Check if the name is already present in the database
        boolean isPresent=categoryService.existCategory(dto.getName());
        if (isPresent){
            System.out.println("Name already exists");
            return ResponseEntity.ok(false);
        }
        else {
            System.out.println("enterted the update endpoint ");
            Boolean isUpdated = categoryService.updateCategory(category);
            if (isUpdated) {
                System.out.println("Sucessfully updated");
                return ResponseEntity.ok(true); // Update was successful
            } else {
                System.out.println("Updation failed");
                return ResponseEntity.ok(false);
        }
        // No category found with the given id
        }

    }



//    For the products
    @PostMapping("/saveProduct")
    public ResponseEntity<Boolean> saveProduct(@ModelAttribute Product_DTO product_dto) {
        Product product = new Product();
        if (product_dto.getId() != null) {
            ObjectId id = new ObjectId(product_dto.getId());
            product.setId(id);
        }
            product.setPrice(product_dto.getPrice());
            product.setCategory(product_dto.getCategory());
            product.setImage(product_dto.getImage());
            product.setTitle(product_dto.getTitle());
            product.setDescription(product_dto.getDescription());
            product.setStock(product_dto.getStock());
            product.setDiscount(product_dto.getDiscount());
            product.setDiscountPrice(product_dto.getDiscountPrice());
            product.setIsActive(product_dto.getIsActive());

            Product savedProduct = productService.saveProduct(product);
            if (savedProduct != null)
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);

            return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);

        }
//"http://localhost:8081/api/admin/products";
    @GetMapping("/products")
    public ResponseEntity<List<Product_DTO>>getAllProducts(){


        List<Product> allProducts = productService.getAllProducts();
        List<Product_DTO> objectStream = allProducts.stream().map(product_dto -> {
            Product_DTO product=new Product_DTO();
            product.setId(product_dto.getId().toString());
            product.setPrice(product_dto.getPrice());
            product.setCategory(product_dto.getCategory());
            product.setImage(product_dto.getImage());
            product.setTitle(product_dto.getTitle());
            product.setDescription(product_dto.getDescription());
            product.setStock(product_dto.getStock());
            product.setDiscount(product_dto.getDiscount());
            product.setDiscountPrice(product_dto.getDiscountPrice());
            product.setIsActive(product_dto.getIsActive());
            return product;
        }).collect(Collectors.toList());

        if (allProducts!=null){
            return new ResponseEntity<List<Product_DTO>>(objectStream,HttpStatus.OK);
        }
        return new ResponseEntity<List<Product_DTO>>(objectStream,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable String id) {
        Boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }

    @GetMapping("/editProduct/{id}")
    public ResponseEntity<Product_DTO> editProduct(@PathVariable String id) {
        System.out.println("Hello");
        Product product_dto = productService.getProductById(id);
        Product_DTO product=convertToDTO(product_dto);

        if (product_dto==null)
            return  new ResponseEntity<Product_DTO>(product,HttpStatus.NOT_FOUND);
        return  new ResponseEntity<Product_DTO>(product,HttpStatus.OK);
    }


    @PutMapping("/updateProduct")
    public ResponseEntity<Boolean> updateProduct(@ModelAttribute Product_DTO product_dto,@RequestParam("file") MultipartFile image) {
//        System.out.println(product_dto.getIsActive());
     ObjectId id1=new ObjectId(product_dto.getId());
     Product product=convertToProduct(product_dto);

//        System.out.println(product.getIsActive());

        Product updateProduct = productService.updateProduct(product,image);
//        System.out.println(updateProduct.getIsActive());

        if (updateProduct!=null){
            return new ResponseEntity<Boolean>(true,HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @GetMapping("/getAllActiveproducts1")
    public ResponseEntity<List<Product_DTO>>getAllActiveProducts1( @RequestParam(value = "categoryName",required = false) String categoryName){

        List<Product> allProducts=productService.getAllActiveProducts(categoryName);
        List<Product_DTO> objectStream = allProducts.stream().map(product_dto -> {
            Product_DTO product=new Product_DTO();
            AdminController adminController=new AdminController();
            return adminController.convertToDTO(product_dto);

        }).collect(Collectors.toList());
        if (allProducts!=null){
            return new ResponseEntity< List<Product_DTO>>(objectStream,HttpStatus.OK);
        }
        return new ResponseEntity< List<Product_DTO>>(objectStream,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getAllActiveproducts")
    public ResponseEntity<List<Product_DTO>>getAllActiveProducts( @RequestParam(value = "categoryName",required = false) String categoryName,@RequestParam(name = "pageNo",defaultValue="0") Integer pageNo,@RequestParam(name = "pageSize",defaultValue = "9")Integer pageSize){


        Page<Product> allActiveProductPagination = productService.getAllActiveProductPagination(pageNo, pageSize, categoryName);
        List<Product> allProducts = allActiveProductPagination.getContent();
//        System.out.println(allActiveProductPagination.getTotalElements());
//        System.out.println(allActiveProductPagination.getTotalPages());
//        System.out.println(allActiveProductPagination.getTotalElements());
        List<Product_DTO> objectStream = allProducts.stream().map(product_dto -> {
            Product_DTO product=new Product_DTO();
            AdminController adminController=new AdminController();
          return adminController.convertToDTO(product_dto);

        }).collect(Collectors.toList());

        List<Product> content = allActiveProductPagination.getContent();

        if (allProducts!=null){
            return new ResponseEntity< List<Product_DTO>>(objectStream,HttpStatus.OK);
        }
        return new ResponseEntity< List<Product_DTO>>(objectStream,HttpStatus.NOT_FOUND);

    }

    @GetMapping("/getPageDetails")
    public ResponseEntity<PageDetails> getPageDetails( @RequestParam(value = "categoryName",required = false) String categoryName,@RequestParam(name = "pageNo",defaultValue="0") Integer pageNo,@RequestParam(name = "pageSize",defaultValue = "2")Integer pageSize){
        Page<Product> allActiveProductPagination = productService.getAllActiveProductPagination(pageNo, pageSize, categoryName);
        int number = allActiveProductPagination.getNumber();
        long totalElements = allActiveProductPagination.getTotalElements();
        int totalPages = allActiveProductPagination.getTotalPages();
        boolean first = allActiveProductPagination.isFirst();
        boolean last = allActiveProductPagination.isLast();
        PageDetails pageDetails=new PageDetails();
        pageDetails.setNumber(number);
        pageDetails.setTotalPages(totalPages);
        pageDetails.setTotalElements(totalElements);
        pageDetails.setFirst(first);
        pageDetails.setLast(last);
        return ResponseEntity.ok(pageDetails);
    }

}



