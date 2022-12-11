package com.edu.laptrinhweb.nhom4.service.impl;

import com.edu.laptrinhweb.nhom4.model.Category;
import com.edu.laptrinhweb.nhom4.repository.CategoryRepository;
import com.edu.laptrinhweb.nhom4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }//findAll

    public void updateCategory(Category category){
    	categoryRepository.save(category);
    }//add or update (tuy vao pri-key)

    public void removeCategoryById(int id){
        categoryRepository.deleteById(id);
    }//delete truyen vao pri-key

    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }//search theo id

}
