package com.edu.laptrinhweb.nhom4.repository;

import com.edu.laptrinhweb.nhom4.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
