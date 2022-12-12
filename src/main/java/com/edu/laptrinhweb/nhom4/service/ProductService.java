package com.edu.laptrinhweb.nhom4.service;

import com.edu.laptrinhweb.nhom4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

	List<Product> getAllProductByCategoryId(int id);

	Optional<Product> getProductById(long id);

	void removeProductById(long id);

	void updateProduct(Product product);

	List<Product> getAllProduct();

	Page<Product> findByProductNameContaining(String name, Pageable pageable);

	List<Product> findByProductNameContaining(String name);

	Page<Product> findAll(Pageable pageable);

	long count();
	List<Product> getRandomListProducts(int lenght);
}
