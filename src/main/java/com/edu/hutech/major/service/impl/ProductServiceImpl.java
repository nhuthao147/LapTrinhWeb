package com.edu.hutech.major.service.impl;

import com.edu.hutech.major.model.Product;
import com.edu.hutech.major.repository.CategoryRepository;
import com.edu.hutech.major.repository.ProductRepository;
import com.edu.hutech.major.service.ProductService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;
    private static Random rnd = new Random();

    @Override
	public List<Product> getAllProduct() {
        return productRepository.findAll();
    }//findAll

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

}
