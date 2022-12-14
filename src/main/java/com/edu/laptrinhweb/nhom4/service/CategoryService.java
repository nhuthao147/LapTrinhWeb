package com.edu.laptrinhweb.nhom4.service;


import com.edu.laptrinhweb.nhom4.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    public List<Category> getAllCategory();

    public void updateCategory(Category category);

    public void removeCategoryById(int id);

    public Optional<Category> getCategoryById(int id);

    long count();

    Page<Category> findAll(Pageable pageable);
}
