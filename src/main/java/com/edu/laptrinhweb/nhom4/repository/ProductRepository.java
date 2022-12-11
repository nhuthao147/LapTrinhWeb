package com.edu.laptrinhweb.nhom4.repository;

import com.edu.laptrinhweb.nhom4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(int id);
    Page<Product> findByProductNameContaining(String name, Pageable pageable);

    List<Product> findByProductNameContaining(String name);
}
