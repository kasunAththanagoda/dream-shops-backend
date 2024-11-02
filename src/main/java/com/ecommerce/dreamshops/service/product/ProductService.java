package com.ecommerce.dreamshops.service.product;

import com.ecommerce.dreamshops.exceptions.ProductNotFoundException;
import com.ecommerce.dreamshops.model.Category;
import com.ecommerce.dreamshops.model.Product;
import com.ecommerce.dreamshops.repository.CategoryRepository;
import com.ecommerce.dreamshops.repository.ProductRepository;
import com.ecommerce.dreamshops.request.AddProductRequest;
import com.ecommerce.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    private final ProductRepository productRepository; // need to add final here .we are using constructor injection // constructor is generated by RequiredArgsConstructor annotation

    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())) //Optional.ofNullable: Wraps the result of categoryRepository.findByName(...) in an Optional. If the category is found, it’s wrapped in a non-empty Optional. If not, Optional.empty() is returned.
                .orElseGet(() -> { //orElseGet: If the category is not found, orElseGet executes the lambda function to create and save a new Category object.
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
//        Optional<Product> product = productRepository.findById(id);
//        if (product.isEmpty()) {
//            throw new ProductNotFoundException("Product with ID " + id + " not found.");
//        }

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(
                productRepository :: delete,
                ()-> {
                    throw new ProductNotFoundException("Product with ID " + id + " not found");
                });  // this is equivalent to the lambda expressions product -> productRepository.delete(product)
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))  //The map method on an Optional object is used to apply a function to the value inside the Optional if it is present, and then it returns a new Optional containing the result of that function. If the Optional is empty (i.e., Optional.empty()), map simply returns an empty Optional.
                .map(productRepository :: save)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
