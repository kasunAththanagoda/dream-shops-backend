package com.ecommerce.dreamshops.service.category;

import com.ecommerce.dreamshops.exceptions.AlreadyExistsException;
import com.ecommerce.dreamshops.exceptions.ResourceNotFoundException;
import com.ecommerce.dreamshops.model.Category;
import com.ecommerce.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return  Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists"));


//        if (categoryRepository.existsByName(category.getName())) {
//            throw new AlreadyExistsException(category.getName() + " already exists");
//        }
//        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long id) {
//        Category oldCategory = categoryRepository.findById(id).orElse(null);
//        if (oldCategory == null) {
//            throw new ResourceNotFoundException("Category not found!");
//        }
//        oldCategory.setName(category.getName());
//        return categoryRepository.save(oldCategory);

        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }) .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));

//        return categoryRepository.findById(id).map((existingCategory)->{
//            existingCategory.setName(category.getName());
//            return categoryRepository.save(existingCategory);
//        }).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository :: delete, () -> {
            throw new ResourceNotFoundException("Category Not Found");
        });
    }
}
