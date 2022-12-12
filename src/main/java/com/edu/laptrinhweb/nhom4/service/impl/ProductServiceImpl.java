package com.edu.laptrinhweb.nhom4.service.impl;

import com.edu.laptrinhweb.nhom4.model.Product;
import com.edu.laptrinhweb.nhom4.repository.ProductRepository;
import com.edu.laptrinhweb.nhom4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class ProductServiceImpl implements ProductService{
    private static Random rnd = new Random();
    @Autowired
    ProductRepository productRepository;

    @Override
	public List<Product> getAllProduct() {
        return productRepository.findAll();
    }//findAll

    @Override
	public void updateProduct(Product product) {

        productRepository.save(product);
    }//add or update (tuy vao pri-key)

    @Override
	public void removeProductById(long id) {
        productRepository.deleteById(id);
    }//delete dua vao pri-key

    @Override
	public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }//search theo id

    @Override
	public List<Product> getAllProductByCategoryId(int id) {
        return productRepository.findAllByCategory_Id(id);
    }
    //findList theo ProductDTO.categoryId

    @Override
    public Page<Product> findByProductNameContaining(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    @Override
    public List<Product> findByProductNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public List<Product> getRandomListProducts(int lenght) {
        List<Product> productList = productRepository.findAll();
        int lastIndex;
        lastIndex = productList.size() -1;
        while(lastIndex > 0 ){
            Product tempValue = productList.get(lastIndex);
            int randomIndex = rnd.nextInt(lastIndex);
            productList.set(lastIndex, productList.get(randomIndex));
            productList.set(randomIndex, tempValue);
            lastIndex--;
        }
        return productList.subList(0, lenght);
    }

}
